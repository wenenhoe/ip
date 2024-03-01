package magus.task;

import static magus.task.storage.Parser.DELIMITER;

/**
 * Abstract class for implementing variants of Task
 * @see magus.task.variant
 */
public abstract class Task {
    protected final String description;
    private boolean isDone;

    protected Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * @return A char representing the task's badge
     */
    public abstract char getBadge();

    public String getDescription() {
        return description;
    }

    /**
     * @return Formatted string of the task's status and description
     */
    @Override
    public String toString() {
        return String.format("[%s] %s", getStatusIcon(), description);
    }

    /**
     * Formats task's status and description for storing
     * @return Formatted string of task's information for storing
     */
    public String toStoredString() {
        String formatString = "%s" + DELIMITER + "%s";
        return String.format(formatString, isDone, description);
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
