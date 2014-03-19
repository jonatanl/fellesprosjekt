package tests;

import Models.Event;
import Models.User;
import interfaces.PersistencyInterface;
import junit.framework.TestCase;
import org.junit.Test;
import util.Persistency;

public class ParticipantTest extends TestCase {

    @Test
    public void testAddParticipant() throws Exception{
        PersistencyInterface p = new Persistency();
        User user;
        user = p.getAllUsers().get(1);
        Event event;
        event = p.getAllEvents().get(1);

        boolean success = p.addEventParticipant(event.getEventId(),user.getUserId());
        assertTrue("Add participant", success);
    }

}
