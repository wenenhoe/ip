package Magus.task;

import Magus.exception.ArgumentNotFoundException;

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
