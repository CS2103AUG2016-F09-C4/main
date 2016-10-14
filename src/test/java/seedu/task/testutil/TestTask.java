package seedu.task.testutil;

import java.util.Optional;

import seedu.task.model.item.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Description description;
    private Optional<Deadline> deadline;
    private Boolean isTaskCompleted;

    public TestTask() {
    	//default fields
        this.isTaskCompleted = false;
        this.deadline = Optional.empty();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setDescription(Description description) {
        this.description = description;
    }
    
    public void setDeadline(Deadline deadline) {
        this.deadline = Optional.of(deadline);
    
    }
    
    public void setStatus(Boolean status) {
        this.isTaskCompleted = status;
    }

    @Override
    public Name getTask() {
        return name;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public Deadline getDeadlineRaw() {
        return this.deadline.get();
    }

    @Override
    public Boolean getTaskStatus() {
        return isTaskCompleted;
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTask().fullName + " ");
        sb.append("/desc " + this.getDescription().value + " ");
        return sb.toString();
    }

    @Override
    public Optional<Deadline> getDeadline() {
        return this.deadline;
    }
    
    

}
