package magus.task;

import magus.console.Console;
import magus.console.Parser;
import magus.exception.ArgumentNotFoundException;
import magus.exception.UnknownArgumentException;
import magus.task.variant.Deadline;
import magus.task.variant.Event;
import magus.task.variant.Todo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Finds relevant tasks to search filter
 */
public class TaskFinder {
    private final List<Task> taskList;

    /**
     * Constructor for TaskFinder that initialises the taskList
     * @param taskList List of tasks to filter
     */
    public TaskFinder(List<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Filters tasks that matches the user search query.
     * Attempt to filter by task type first and if failed,
     * filter by description.
     *
     * @param parser Console parser that parsed user input
     * @return List of task filtered from search query or
     * <code>null</code> if error encountered
     * @see #filterTodos(Parser)
     * @see #filterEvents(Parser)
     * @see #filterDeadlines(Parser)
     * @see #filterTasksByDate(Parser)
     * @see #filterTasksByDescription(Parser, Parser, TaskType)
     */
    public List<Task> filterTasks(Parser parser) {
        Parser subparser = new Parser(parser.getAdditionalInput());
        String taskTypeString = subparser.getCommandCandidate();
        TaskType taskType = TaskType.getEnum(taskTypeString);

        List<Task> filteredTaskList;
        switch (taskType) {
        case TODO:
            filteredTaskList = filterTodos(subparser);
            break;
        case DEADLINE:
            try {
                filteredTaskList = filterDeadlines(subparser);
            } catch (DateTimeParseException e) {
                Console.printError("FIND", e);
                return null;
            }
            break;
        case EVENT:
            try {
                filteredTaskList = filterEvents(subparser);
            } catch (DateTimeParseException e) {
                Console.printError("FIND", e);
                return null;
            }
            break;
        default:
            try {
                filteredTaskList = filterTasksByDate(parser);
            } catch (ArgumentNotFoundException e) {
                filteredTaskList = filterTasksByDescription(parser, subparser, taskType);
                break;
            } catch (DateTimeParseException | UnknownArgumentException e) {
                Console.printError("FIND", e);
                return null;
            }
            break;
        }

        return filteredTaskList;
    }

    /**
     * Filters tasks by checking if their description contain the search terms
     *
     * @param parser Console parser that parsed user input without task type
     *               or null if using task type specific parser <code>subparser</code>
     * @param subparser Console parser that parsed user input with task type
     * @param taskType Task type to filter by
     * @return List of task filtered from search query
     */
    private List<Task> filterTasksByDescription(Parser parser, Parser subparser, TaskType taskType) {
        String searchString;
        Map<String, String> parsedArgs;

        try {
            if (taskType != TaskType.UNKNOWN) {
                // task specific search string
                parsedArgs = subparser.parseAdditionalInput(true);
            } else {
                // search string for all types of tasks
                parsedArgs = parser.parseAdditionalInput(true);
            }
            searchString = Parser.getParsedArgsValue(parsedArgs, "", "description"); // non-keyword arg
        } catch (UnknownArgumentException | ArgumentNotFoundException e) {
            Console.printError("FIND", e);
            return null;
        }

        Stream<Task> taskStream = taskList.stream();
        switch (taskType) {
        case TODO:
            taskStream = taskStream.filter(t -> t instanceof Todo);
            break;
        case DEADLINE:
            taskStream = taskStream.filter(t -> t instanceof Deadline);
            break;
        case EVENT:
            taskStream = taskStream.filter(t -> t instanceof Event);
            break;
        }

        return taskStream.filter(t -> t.getDescription().contains(searchString))
                .collect(Collectors.toList());
    }

    /**
     * Filters tasks by checking if their date matches the search date
     *
     * @param parser Console parser that parsed user input containing search date
     * @return List of task filtered from search query
     * @throws ArgumentNotFoundException Expected argument not found in search query
     * @throws DateTimeParseException Unable to parse date time specified in search query
     * @throws UnknownArgumentException Unknown argument specified in search query
     */
    private List<Task> filterTasksByDate(Parser parser)
            throws ArgumentNotFoundException, DateTimeParseException, UnknownArgumentException {
        String dateCommand = "/date";
        Map<String, String> parsedArgs = parser.parseAdditionalInput(false, dateCommand);

        String searchDateString = parsedArgs.get(dateCommand);
        LocalDate searchDate = LocalDate.parse(searchDateString);
        return taskList.stream()
                .filter(t ->
                        (t instanceof Event
                                && (((Event) t).getStart().isEqual(searchDate) ||
                                ((Event) t).getEnd().isEqual(searchDate)))
                        || (t instanceof Deadline
                                && ((Deadline) t).getEnd().isEqual(searchDate)))
                .collect(Collectors.toList());
    }

    /**
     * Filters Todo tasks by checking if their description contain the search terms.
     * Wrapper method on filterTasksByDescription
     *
     * @param parser Console parser that parsed task-specific user input containing description
     * @return List of task filtered from search query
     * @see #filterTasksByDescription(Parser, Parser, TaskType)
     */
    private List<Task> filterTodos(Parser parser) {
        return filterTasksByDescription(null, parser, TaskType.TODO);
    }

    /**
     * Filters Deadline tasks by checking if their date matches the search date
     *
     * @param parser Console parser that parsed user input containing search date
     * @return List of task filtered from search query
     * @throws DateTimeParseException Unable to parse date time specified in search query
     * @see #filterDeadlinesByEnd(Parser) 
     * @see #filterTasksByDescription(Parser, Parser, TaskType) 
     */
    private List<Task> filterDeadlines(Parser parser)
            throws DateTimeParseException {
        try {
            return filterDeadlinesByEnd(parser);
        } catch (ArgumentNotFoundException | UnknownArgumentException ignored) {
            return filterTasksByDescription(null, parser, TaskType.DEADLINE);
        }
    }

    /**
     * Filters Deadline tasks by checking if their date matches the search date
     *
     * @param parser Console parser that parsed user input containing search date
     * @return List of task filtered from search query
     * @throws UnknownArgumentException Unknown argument specified in search query
     * @throws ArgumentNotFoundException Expected argument not found in search query
     */
    private List<Task> filterDeadlinesByEnd(Parser parser)
            throws UnknownArgumentException, ArgumentNotFoundException {
        String byCommand = "/by";
        Map<String, String> parsedArgs = parser.parseAdditionalInput(false, byCommand);

        String searchEndDateString = parsedArgs.get(byCommand);
        LocalDate searchEndDate = LocalDate.parse(searchEndDateString);

        return taskList.stream()
                .filter(t -> t instanceof Deadline
                        && ((Deadline) t).getEnd().isEqual(searchEndDate))
                .collect(Collectors.toList());
    }

    /**
     * Filters Event tasks by checking if their date matches the search date
     *
     * @param parser Console parser that parsed task-specific user input
     * @return List of task filtered from search query
     * @throws DateTimeParseException Unable to parse date time specified in search query
     * @see #filterEventsByStartAndEnd(Parser)
     * @see #filterEventsByStart(Parser)
     * @see #filterEventsByEnd(Parser)
     * @see #filterTasksByDescription(Parser, Parser, TaskType) 
     */
    private List<Task> filterEvents(Parser parser)
            throws DateTimeParseException {
        try {
            return filterEventsByStartAndEnd(parser);
        } catch (ArgumentNotFoundException | UnknownArgumentException ignored) {
            // continue the next filter type
        }

        try {
            return filterEventsByStart(parser);
        } catch (ArgumentNotFoundException | UnknownArgumentException ignored) {
            // continue the next filter type
        }

        try {
            return filterEventsByEnd(parser);
        } catch (ArgumentNotFoundException | UnknownArgumentException ignored) {
            // continue the next filter type
        }

        return filterTasksByDescription(null, parser, TaskType.EVENT);
    }

    /**
     * Filters Event tasks by checking if their date matches the start and end search dates
     *
     * @param parser Console parser that parsed user input containing search date
     * @return List of task filtered from search query
     * @throws ArgumentNotFoundException Expected argument not found in search query
     * @throws DateTimeParseException Unable to parse date time specified in search query
     * @throws UnknownArgumentException Unknown argument specified in search query
     */
    private List<Task> filterEventsByStartAndEnd(Parser parser)
            throws ArgumentNotFoundException, DateTimeParseException, UnknownArgumentException {
        String fromCommand = "/from";
        String toCommand = "/to";

        Map<String, String> parsedArgs = parser.parseAdditionalInput(false, fromCommand, toCommand);
        String searchStartDateString = parsedArgs.get(fromCommand);
        LocalDate searchStartDate = LocalDate.parse(searchStartDateString);
        String searchEndDateString = parsedArgs.get(toCommand);
        LocalDate searchEndDate = LocalDate.parse(searchEndDateString);
        return taskList.stream()
                .filter(t -> t instanceof Event
                        && ((Event) t).getStart().isEqual(searchStartDate)
                        && ((Event) t).getEnd().isEqual(searchEndDate))
                .collect(Collectors.toList());
    }

    /**
     * Filters Event tasks by checking if their date matches the start search dates only
     *
     * @param parser Console parser that parsed user input containing search date
     * @return List of task filtered from search query
     * @throws ArgumentNotFoundException Expected argument not found in search query
     * @throws DateTimeParseException Unable to parse date time specified in search query
     * @throws UnknownArgumentException Unknown argument specified in search query
     */
    private List<Task> filterEventsByStart(Parser parser)
            throws ArgumentNotFoundException, DateTimeParseException, UnknownArgumentException {
        String fromCommand = "/from";
        Map<String, String> parsedArgs = parser.parseAdditionalInput(false, fromCommand);
        String searchStartDateString = parsedArgs.get(fromCommand);
        LocalDate searchStartDate = LocalDate.parse(searchStartDateString);
        return taskList.stream()
                .filter(t -> t instanceof Event
                        && ((Event) t).getStart().isEqual(searchStartDate))
                .collect(Collectors.toList());
    }

    /**
     * Filters Event tasks by checking if their date matches the end search dates only
     *
     * @param parser Console parser that parsed user input containing search date
     * @return List of task filtered from search query
     * @throws ArgumentNotFoundException Expected argument not found in search query
     * @throws DateTimeParseException Unable to parse date time specified in search query
     * @throws UnknownArgumentException Unknown argument specified in search query
     */
    private List<Task> filterEventsByEnd(Parser parser)
            throws ArgumentNotFoundException, DateTimeParseException, UnknownArgumentException {
        String toCommand = "/to";
        Map<String, String> parsedArgs = parser.parseAdditionalInput(false, toCommand);
        String searchEndDateString = parsedArgs.get(toCommand);
        LocalDate searchEndDate = LocalDate.parse(searchEndDateString);
        return taskList.stream()
                .filter(t -> t instanceof Event
                        && ((Event) t).getEnd().isEqual(searchEndDate))
                .collect(Collectors.toList());
    }
}
