package magus.task;

import magus.console.Console;
import magus.console.Parser;
import magus.exception.ArgumentNotFoundException;
import magus.task.variant.Deadline;
import magus.task.variant.Event;
import magus.task.variant.Todo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TaskFinder {
    private final List<Task> taskList;

    public TaskFinder(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<Task> filterTasks(Parser parser) {
        Parser subparser = new Parser(parser.getAdditionalInput());
        String taskTypeString = subparser.getCommandCandidate();
        TaskType taskType = TaskType.getEnum(taskTypeString);

        List<Task> filteredTaskList = null;
        switch (taskType) {
        case TODO:
            try {
                filteredTaskList = filterTodos(subparser);
            } catch (ArgumentNotFoundException ignored) {
                // break out and try filter by description
                break;
            }
            break;
        case DEADLINE:
            try {
                filteredTaskList = filterDeadlines(subparser);
            } catch (ArgumentNotFoundException ignored) {
                // break out and try filter by description
                break;
            } catch (DateTimeParseException e) {
                Console.printError("FIND", e);
                return filteredTaskList;
            }
            break;
        case EVENT:
            try {
                filteredTaskList = filterEvents(subparser);
            } catch (DateTimeParseException e) {
                Console.printError("FIND", e);
                return filteredTaskList;
            }
            break;
        default:
            try {
                filteredTaskList = filterTasksByDate(parser);
            } catch (ArgumentNotFoundException e) {
                // break out and try filter by description
                break;
            } catch (DateTimeParseException e) {
                Console.printError("FIND", e);
                return filteredTaskList;
            }
            break;
        }

        if (filteredTaskList == null) {
            filteredTaskList = filterTasksByDescription(parser, subparser, taskType);
        }

        return filteredTaskList;
    }

    private List<Task> filterTasksByDescription(Parser parser, Parser subparser, TaskType taskType) {
        String searchString, taskSpecificSearchString;
        try {
            searchString = parser.parseAdditionalInput(true).get(""); // non-keyword arg
        } catch (ArgumentNotFoundException e) {
            return new ArrayList<>();
        }
        taskSpecificSearchString = subparser.getAdditionalInput();

        Stream<Task> taskStream = taskList.stream();
        switch (taskType) {
        case TODO:
            taskStream = taskStream.filter(t -> t instanceof Todo);
            searchString = taskSpecificSearchString;
            break;
        case DEADLINE:
            taskStream = taskStream.filter(t -> t instanceof Deadline);
            searchString = taskSpecificSearchString;
            break;
        case EVENT:
            taskStream = taskStream.filter(t -> t instanceof Event);
            searchString = taskSpecificSearchString;
            break;
        }

        String finalSearchString = searchString;
        return taskStream.filter(t -> t.getDescription().contains(finalSearchString))
                .collect(Collectors.toList());
    }

    private List<Task> filterTasksByDate(Parser parser)
            throws ArgumentNotFoundException, DateTimeParseException {
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

    private List<Task> filterTodos(Parser parser) throws ArgumentNotFoundException {
        Map<String, String> parsedArgs = parser.parseAdditionalInput(true);
        String searchString = parsedArgs.get(""); // non-keyword arg
        return taskList.stream()
                .filter(t -> t instanceof Todo
                        && t.getDescription().contains(searchString))
                .collect(Collectors.toList());
    }

    private List<Task> filterDeadlines(Parser parser)
            throws ArgumentNotFoundException, DateTimeParseException {
        String byCommand = "/by";
        Map<String, String> parsedArgs = parser.parseAdditionalInput(false, byCommand);

        String searchEndDateString = parsedArgs.get(byCommand);
        LocalDate searchEndDate = LocalDate.parse(searchEndDateString);

        return taskList.stream()
                .filter(t -> t instanceof Deadline
                        && ((Deadline) t).getEnd().isEqual(searchEndDate))
                .collect(Collectors.toList());
    }

    private List<Task> filterEvents(Parser parser)
            throws DateTimeParseException {

        try {
            return filterEventsByStartAndEnd(parser);
        } catch (ArgumentNotFoundException e) {
            // continue the next filter type
        }
        try {
            return filterEventsByStart(parser);
        } catch (ArgumentNotFoundException e) {
            // continue the next filter type
        }
        try {
            return filterEventsByEnd(parser);
        } catch (ArgumentNotFoundException e) {
            // unknown combination of args
            return null;
        }
    }

    private List<Task> filterEventsByStartAndEnd(Parser parser)
            throws ArgumentNotFoundException, DateTimeParseException {
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

    private List<Task> filterEventsByStart(Parser parser)
            throws ArgumentNotFoundException, DateTimeParseException {
        String fromCommand = "/from";
        Map<String, String> parsedArgs = parser.parseAdditionalInput(false, fromCommand);
        String searchStartDateString = parsedArgs.get(fromCommand);
        LocalDate searchStartDate = LocalDate.parse(searchStartDateString);
        return taskList.stream()
                .filter(t -> t instanceof Event
                        && ((Event) t).getStart().isEqual(searchStartDate))
                .collect(Collectors.toList());
    }

    private List<Task> filterEventsByEnd(Parser parser)
            throws ArgumentNotFoundException, DateTimeParseException {
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
