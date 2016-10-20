package seedu.task.logic.commands;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.Deadline;
import seedu.task.model.item.Description;
import seedu.task.model.item.Name;
import seedu.task.model.item.ReadOnlyTask;
import seedu.task.model.item.Task;
import seedu.task.model.item.UniqueTaskList;
import seedu.task.model.item.UniqueTaskList.DuplicateTaskException;
import seedu.taskcommons.core.Messages;
import seedu.taskcommons.core.UnmodifiableObservableList;

/**
 * Executes editing of tasks according to the input argument.
 * @author kian ming
 */
public class EditTaskCommand extends EditCommand  {

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    private static final Boolean TASK_DEFAULT_STATUS = false;
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task book";
    
    private Name newName;
    private Description newDescription;
    private Deadline newDeadline;
    private boolean isNameToBeEdit;
    private boolean isDescriptionToBeEdit;
    private boolean isDeadlineToBeEdit;
    
    private Task editTask;
    private ReadOnlyTask targetTask;
    
    /**
     * Convenience constructor using raw values.
     * Only fields to be edited will have values parsed in.
     * TODO: 
     *  1. allow edit tasks with deadline
     *  
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public EditTaskCommand(Integer index,
                           boolean isNameToBeEdited, String name, 
                           boolean isDescriptionToBeEdited, String description,
                           boolean isDeadlineToBeEdited, String deadline
                           ) throws IllegalValueException {
        
        setTargetIndex(index);
        this.isNameToBeEdit = isNameToBeEdited;
        this.isDescriptionToBeEdit = isDescriptionToBeEdited;
        this.isDeadlineToBeEdit = isDeadlineToBeEdited;
        
        newName = null;
        newDescription = null;
        newDeadline = null;
        
        if (isNameToBeEdited) {
            newName = new Name(name);
        } 
        if (isDescriptionToBeEdited) {
            newDescription = new Description(description);
        }
        if (isDeadlineToBeEdited) {
            newDeadline = new Deadline(deadline);
        }
    }
    
    public EditTaskCommand(Task newTask, Task oldTask) {
		this.editTask = newTask;
		this.targetTask = oldTask;
	}

	/**
     * Gets the task to be edited based on the index.
     * Only fields to be edited will have values updated.
     * @throws DuplicateTaskException 
     */
    @Override
    public CommandResult execute() {

        try {
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();        
            targetTask = lastShownList.get(getTargetIndex());

            editTask = editTask(targetTask);
            model.editTask(editTask, targetTask);
            reverseCommand = prepareUndoCommand();
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editTask));

        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (IndexOutOfBoundsException ie) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } 
    }

    /**
     * Edits the necessary fields.
     * Assumes task completion status is reinstated to not completed.
     * @return task that has the fields according to edit requirements.
     */    
    private Task editTask(ReadOnlyTask targetTask) {
        
        if (!isNameToBeEdit) {
            newName = targetTask.getTask();
        }
        if (!isDescriptionToBeEdit) {
            newDescription = targetTask.getDescription();
        }
        if (!isDeadlineToBeEdit) {
            if (targetTask.getDeadline().isPresent()) {
                newDeadline = targetTask.getDeadline().get();
                return new Task (this.newName, this.newDescription, this.newDeadline, TASK_DEFAULT_STATUS);
            } else {
                return new Task (this.newName, this.newDescription, TASK_DEFAULT_STATUS);
            }
        } 
        return new Task (this.newName, this.newDescription, this.newDeadline, TASK_DEFAULT_STATUS);
        
    }

	@Override
	public CommandResult undo() {
        try {
        	
            model.editTask(editTask, targetTask);
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, editTask));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (IndexOutOfBoundsException ie) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } 
	}

	@Override
	public UndoableCommand prepareUndoCommand() {
		Task newTask = (Task) this.targetTask;
		Task oldTask = this.editTask;
		
		UndoableCommand command = new EditTaskCommand(newTask, oldTask);
		command.setData(model);
		
		return command;
	}
	
	@Override
	public String toString() {
		return COMMAND_WORD+ " from " + this.targetTask.getAsText()
		+ " to " + this.editTask.getAsText();
	}

}
