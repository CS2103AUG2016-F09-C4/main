package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.TaskCardHandle;
import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.AddEventCommand;
import seedu.task.logic.commands.AddTaskCommand;
import seedu.task.testutil.TestEvent;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;
import seedu.task.testutil.TypicalTestEvents;
import seedu.task.testutil.TypicalTestTasks;

//@@author A0127570H
/*
 * GUI test for Add Command
 */
public class AddCommandTest extends TaskBookGuiTest {

    @Test
    public void addTask() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = TypicalTestTasks.arts;
        currentList = TestUtil.addTasksToListAtIndex(currentList, 0,taskToAdd);
        assertAddTaskSuccess(taskToAdd, currentList);

        //add another task
        taskToAdd = TypicalTestTasks.socSciences;
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertAddTaskSuccess(taskToAdd, currentList);

        //add duplicate task
        commandBox.runCommand(TypicalTestTasks.arts.getFullAddCommand());
        assertResultMessage(AddTaskCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    @Test
    public void addEvent() {
        //add one event
        TestEvent[] currentList = te.getTypicalNotCompletedEvents();
        TestEvent eventToAdd = TypicalTestEvents.addedEvent;
        currentList = TestUtil.addEventsToListAtIndex(currentList, 0,eventToAdd);

        assertAddEventSuccess(eventToAdd, currentList);        

        //add duplicate event
        commandBox.runCommand(TypicalTestEvents.addedEvent.getAddCommand());
        assertResultMessage(AddEventCommand.MESSAGE_DUPLICATE_EVENT);
        assertTrue(eventListPanel.isListMatching(currentList));

    }

    private void assertAddTaskSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getFullAddCommand());
        
        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getTask().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        assertTrue(taskListPanel.isListMatching(currentList));
    }
    
    private void assertAddEventSuccess(TestEvent eventToAdd, TestEvent... currentList) {

    	commandBox.runCommand(eventToAdd.getAddCommand());
        
        //confirm the new card contains the right data
        EventCardHandle addedCard = eventListPanel.navigateToEvent(eventToAdd.getEvent().fullName);
        assertMatching(eventToAdd, addedCard);

        //confirm the list now contains all previous events plus the new event
        assertTrue(eventListPanel.isListMatching(currentList));
    }

}
