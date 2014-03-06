package interfaces;

import java.util.ArrayList;
import java.util.Date;

import Models.Alarm;
import Models.Event;
import Models.EventParticipant;
import Models.Room;

public interface CalendarInterface {
	
	// 'event' inneholer event samt referanser til EventParticipants, 
	// som igjen inneholder referanser til Users og Alarms. 
	public void addEvent(Event event);
		
	public void removeEvent(Event event);
	
	// 'eventId' må brukes for å finne riktig Event-objekt som skal endres på. 
	public void changeEvent(int eventId, String newEventName, Date newStartTime, Date newEndTime,
			String newDescription, String newLocation, Room newRoom);
	
	// Finn fram riktig Event og kall addEventParticipant på denne Eventen. 
	public void addEventParticipant(int eventID, EventParticipant participant);
	
	// Finn fram riktig Event og kall removeEventParticipant på denne Eventen. 
	public void removeEventParticipant(int eventID, EventParticipant participant);

	// Kalles når response eller isDeleted forandres hos en EventParticipant. 
	// Andre EventParticipants må gis beskjed om at en forandring har skjedd. 
	public void changeEventParticipantResponse(int eventId, int eventParticipantId, String newResponse, boolean newIsDeleted);
	
	public void addAlarm(EventParticipant participant, Alarm alarm);
	
	public void removeAlarm(EventParticipant participant);
	
	public void changeAlarm(EventParticipant participant, Date newAlarmTime);
	
	// Kan vurdere å også ha metoder for addUser, addRoom og addGroup, selv om disse ikke skal brukes når 
	// systemet er ferdig. Kan være nyttige mens vi tester kanskje. 
}
