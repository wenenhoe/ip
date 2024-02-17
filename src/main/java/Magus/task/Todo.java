package Magus.task;

import Magus.exception.ArgumentNotFoundException;
import Magus.task.fileio.Parser;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    public static Todo parse(String taskInfo) throws ArgumentNotFoundException {
        boolean hasNoDescription = taskInfo.isEmpty();
        if (hasNoDescription) {
            throw new ArgumentNotFoundException(taskInfo);
        }
        return new Todo(taskInfo);
    }

    public static Todo parseStoredTaskInfo(Parser parser) {
        try {
            String description = parser.getTaskInfo();
            Todo todo = parse(description);

            boolean isDone = parser.isDone();
            if (isDone) {
                todo.markAsDone();
            } else {
                todo.unmarkAsDone();
            }

            return todo;
        } catch (ArgumentNotFoundException e) {
            return null;
        }
    }

    @Override
    public char getBadge() {
        return 'T';
    }

    @Override
    public String toString() {
        return String.format("[%c]%s", getBadge(), super.toString());
    }

    @Override
    public String toStoredString() {
        return String.format("%c\t|\t%s", getBadge(), super.toStoredString());
    }
}
