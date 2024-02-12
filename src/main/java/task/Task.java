package task;

public class Task {
    private final String description;
    private boolean isDone;

    protected Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", getStatusIcon(), description);
    }

    public void markAsDone() {
        isDone = true;
    }

    public void unmarkAsDone() {
        isDone = false;
    }

    private String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }
}
