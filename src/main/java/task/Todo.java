package task;

public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    public static Todo parse(String taskInfo) {
        return new Todo(taskInfo);
    }

    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
