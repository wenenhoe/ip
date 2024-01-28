public class Task {
    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String toString() {
        return String.format("[%s] %s", getStatusIcon(), description);
    }

    private String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }
}
