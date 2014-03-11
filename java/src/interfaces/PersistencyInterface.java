
package interfaces;

import java.util.ArrayList;
import java.util.Date;

import Models.Event;
import Models.EventParticipant;
import Models.Alarm;
import Models.Group;
import Models.Room;
import Models.User;

// Forslag til et interface til database kontrolleren, her kalt Persistency. 
// Gangen i det hele: 
// 1. Et popup-vindu, f.eks. AddEvent, kaller metoden addEvent() hos Persistency.
// 2. Persistency fikser databasen og kaller deretter riktig metode hos Calendar.
// 3. Calendar sier fra videre til webView om nødvendig.

public interface PersistencyInterface {
	
	// For testing.  
	public void closeConnection();
	
	// MEthods for retrieving all entries. 
	public ArrayList<User> getAllUsers();
	public ArrayList<Event> getAllEvents();
	public ArrayList<EventParticipant> getAllEventParticipants();
	public ArrayList<Group> getAllGroups();
	public ArrayList<Room> getAllRooms();
	public ArrayList<Alarm> getAllAlarms();
	
	// If success, returns the userId of the logged in user. Otherwise, returns -1. 
	public int requestLogin(String username, String password);
	
	public void addEvent(Event event, ArrayList<Integer> participantIDs);
	
	public void removeEvent(Event event);
	
	// Kalles når noe forandres i en event, bortsett fra EventParticipants.  
	public void changeEvent(Event event);

	public void addEventParticipant(int eventID, int participantID);
	
	public void removeEventParticipant(int eventID, int participantID);
	
	// Kalles når response eller isDeleted forandres hos en EventParticipant. 
	public void changeEventParticipantResponse(EventParticipant participant);
	
	public void addAlarm(EventParticipant participant, Alarm alarm);
	
	public void removeAlarm(EventParticipant participant, Alarm alarm);
	
	public void changeAlarm(EventParticipant participant, Alarm alarm);
	
	// Kan vurdere å også ha metoder for addUser, addRoom og addGroup, selv om disse ikke skal brukes når 
	// systemet er ferdig. Kan være nyttige mens vi tester kanskje. 
}
