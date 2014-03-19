package tests;

import java.util.ArrayList;
import java.util.Date;

import Models.Alarm;
import Models.Event;
import Models.EventParticipant;
import interfaces.PersistencyInterface;
import util.Persistency;
import junit.framework.TestCase;

public class AlarmTest extends TestCase {
	
	
	Alarm a;
	EventParticipant ep;
	PersistencyInterface persistency;
	
	public void testAddAlarm() throws Exception {
		boolean success = addAlarm();
		
		assertTrue("Add alarm", success);
	}
	
	public boolean addAlarm(){
		persistency = new Persistency();
		ep = persistency.getAllEventParticipants().get(0);
		a = new Alarm();
		a.setTime(new Date());
		
		return persistency.addAlarm(ep, a);
	}
	
	public void testRemoveAlarm() throws Exception{
		
		addAlarm();
		
		boolean success = persistency.removeAlarm(a);
		
		assertTrue("Remove alarm", success);
	}
	
	public void testChangeAlarm() throws Exception{
		addAlarm();
		
		boolean success = persistency.changeAlarm(a);
		
		assertTrue("Change alarm", success);
	}
	
}
