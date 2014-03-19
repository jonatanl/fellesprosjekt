package tests;

import Models.Event;
import interfaces.PersistencyInterface;
import junit.framework.TestCase;
import util.Persistency;

import java.util.ArrayList;
import java.util.Date;

public class EventTest extends TestCase{

    public void testAddEvent() throws Exception {
        PersistencyInterface persistency = new Persistency();

        Event event = addEvent();
        ArrayList<Integer> participantIDs = new ArrayList<>();
        participantIDs.add(1);
        boolean success = persistency.addEvent(event,participantIDs);
        assertTrue("Added event", success);
    }


    public void testRemoveEvent() throws Exception {
        PersistencyInterface persistency = new Persistency();
        Event event = addEvent();
        ArrayList<Integer> participantIDs = new ArrayList<>();
        participantIDs.add(1);
        persistency.addEvent(event, participantIDs);

        boolean success = persistency.removeEvent(event);
        assertTrue(success);
    }

    private Event addEvent() {
        Event event = new Event();
        event.setEventName("Test");
        event.setStartTime(new Date());
        event.setEndTime(new Date());
        event.setDescription("TestDescription");
        event.setOwnerId(1);
        event.setRoomId(2);
        event.setLocation("");

        return event;
    }
}
