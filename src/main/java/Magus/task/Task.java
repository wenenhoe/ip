package Magus.task;

public abstract class Task {
    protected final String description;
    private boolean isDone;

    protected Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public abstract char getBadge();

    @Override
    public String toString() {
        return String.format("[%s] %s", getStatusIcon(), description);
    }

    public String toStoredString() {
        return String.format("%s\t|\t%s", isDone, description);
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
