package interfaces;

import java.util.ArrayList;

import Models.Alarm;
import Models.Event;
import Models.EventParticipant;

public interface CalendarInterface {
	
	// 'event' inneholer event samt referanser til EventParticipants, 
	// som igjen inneholder referanser til Users og Alarms. 
	public void addEvent(Event event);
		
	public void removeEvent(Event event);
	
	public void changeEvent(Event event);
	
	// Finn fram riktig Event og kall addEventParticipant p� denne Eventen. 
	public void addEventParticipant(int eventID, EventParticipant participant);
	
	public void removeEventParticipant(int eventID, EventParticipant participant);

	// Kalles n�r response eller isDeleted forandres hos en EventParticipant. 
	// Andre EventParticipants m� gis beskjed om at en forandring har skjedd. 
	public void changeEventParticipantResponse(EventParticipant participant);
	
	public void addAlarm(EventParticipant participant, Alarm alarm);
	
	public void removeAlarm(EventParticipant participant, Alarm alarm);
	
	public void changeAlarm(EventParticipant participant, Alarm alarm);
	
	// Kan vurdere � ogs� ha metoder for addUser, addRoom og addGroup, selv om disse ikke skal brukes n�r 
	// systemet er ferdig. Kan v�re nyttige mens vi tester kanskje. 
}
