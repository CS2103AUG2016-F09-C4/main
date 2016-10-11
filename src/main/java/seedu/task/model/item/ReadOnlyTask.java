package seedu.task.model.item;

import java.util.Optional;

/**
 * A read-only immutable interface for a task in the task book.
 * Implementations should guarantee: 
 *      Details are present and not null, with the exception of Deadline field. 
 *      Field values are validated.
 * @author kian ming
 */
public interface ReadOnlyTask {

    Name getTask();
    Description getDescription();
    Optional<Deadline> getDeadline();
    Boolean getTaskStatus();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTask().equals(this.getTask()) // state checks here onwards
                && other.getDeadline().equals(this.getDeadline())
                && other.getTaskStatus().equals(this.getTaskStatus())
                && other.getDescription().equals(this.getDescription()));
    }

    /**
     * Formats the task as text, showing all task details and status.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTask())
                .append(" Desc: ")
                .append(getDescription())
                .append(getDeadlineToString())
                .append(getTaskStatusToString());
        
        return builder.toString();
    }
    
    /**
     * Formats the deadline as text.
     * If null, empty string is returned
     */
    default String getDeadlineToString() {
        return getDeadline().isPresent()? " Deadline: " + getDeadline().get(): "";
    }
    
    
    /**
     * Formats the task status as text
     */
    default String getTaskStatusToString() {
        return getTaskStatus() ? " Status: Completed" : " Status: Not completed";
    }

}