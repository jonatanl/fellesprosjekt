package tests;

import Models.Event;
import interfaces.PersistencyInterface;
import junit.framework.TestCase;
import util.Persistency;

import java.util.ArrayList;

public class EventTest extends TestCase{

    public void testAddEvent() throws Exception {
        PersistencyInterface persistency = new Persistency();
        Event event = new Event();
        ArrayList<Integer> participantIDs = new ArrayList<>();
        boolean success = persistency.addEvent(event,participantIDs);
        assertTrue("Added event", success);
    }
}
