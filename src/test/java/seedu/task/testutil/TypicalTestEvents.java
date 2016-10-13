package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskBook;
import seedu.task.model.item.Event;
import seedu.task.model.item.UniqueEventList;

/**
 * Constructing events for GUI testing
 * @author xuchen
 *
 */
public class TypicalTestEvents {
	public static TestEvent  meeting1, meeting2, meeting3;
	
	public TypicalTestEvents() {
		try {
			meeting1 = new EventBuilder()
					.withName("ms v0")
					.withDescription("for CS2103 project")
					.withDuration("yesterday 1pm > yesterday 2pm")
					.build();
			
			meeting2 = new EventBuilder()
					.withName("ms v1")
					.withDescription("for CS2103 project")
					.withDuration("tomorrow 3pm > tomorrow 4pm")
					.build();
			
			meeting3 = new EventBuilder()
					.withName("ms v2")
					.withDescription("for CS2103 project")
					.withDuration("tomorrow 8pm > tomorrow 11pm")
					.build();
		} catch (IllegalValueException e) {
			e.printStackTrace();
			assert false : "not possible";
		}
	}
	
	public static void loadTestBookWithSampleData(TaskBook tb) {
		try {
			tb.addEvent(new Event(meeting1));
			tb.addEvent(new Event(meeting2));
			tb.addEvent(new Event(meeting3));
		} catch (UniqueEventList.DuplicateEventException e) {
			assert false : "not possible";
		}
	}
	
	public TestEvent[] getTypicalAllEvents() {
		return new TestEvent[]{meeting1, meeting2, meeting3};
	}
	
	public TestEvent[] getTypicalNotCompletedEvents() {
		return new TestEvent[]{meeting2, meeting3};
	}
	
	public TaskBook getTypicalTaskBook() {
		TaskBook tb = new TaskBook();
		loadTestBookWithSampleData(tb);
		return tb;
	}
}
