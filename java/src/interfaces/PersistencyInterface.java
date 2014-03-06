
package interfaces;

import java.util.ArrayList;

import Models.Event;
import Models.EventParticipant;
import Models.Alarm;

// Forslag til et interface til database kontrolleren, her kalt Persistency. 
// Gangen i det hele: 
// 1. Et popup-vindu, f.eks. AddEvent, kaller metoden addEvent() hos Persistency.
// 2. Persistency legger til eventen i databasen, og oppretter entries i EventParticipants i databasen.
// 3. Persistency oppretter egne objekt for hver eventParticipants, og disse legges til i Event-objektet. 
//		Disse vil i utgangspunktet ikke ha noen alarm, og ingen response. 
//		Event-objektet får en id, hentet fra databasen.
// 4. Persistency kaller hovedklassen, her kalt Calendar, sine tilsvarende metoder, f.eks. addEvent(), 
//		og legger til eventen. Merk at dette objektet nå har referanser til de tilhørende eventParticipants. 
// 5.  Calendar gir beskjed til kalender-viewet/webscenen om at en ny event er opprettet. 

public interface PersistencyInterface {
	
	// 'event' inneholder alle relevante data, untatt id, som etter hvert hentes fra databasen.
	// 'participantIDs' er id-ene til alle participants. 
	public void addEvent(Event event, ArrayList<Integer> participantIDs);
	
	// 'event' slettes i databasen, og dersom databasen er kodet riktig, med "ON DELETE CASCADE" vil de
	// tilhørende EventParticipants også slettes i databasen, og forhåpentligvis også alarmene. 
	// Når Persistency kaller removeEvent hos Calender, fjernes objektet og med det også referansen til 
	// EventParticipantene. 
	public void removeEvent(Event event);
	
	// Kalles når noe forandres i en event, bortsett fra EventParticipants. 
	public void changeEvent(Event event);
	
	// Her trengs bare ID til event og participant for å fjerne riktige entries. 
	public void addEventParticipant(int eventID, int participantID);
	
	public void removeEventParticipant(int eventID, int participantID);

	// Kalles når response eller isDeleted forandres hos en EventParticipant. 
	// Andre EventParticipants må gis beskjed om at en forandring har skjedd. 
	public void changeEventParticipantResponse(EventParticipant participant);
	
	
	public void addAlarm(EventParticipant participant, Alarm alarm);
	
	public void removeAlarm(EventParticipant participant, Alarm alarm);
	
	public void changeAlarm(EventParticipant participant, Alarm alarm);
	
	// Kan vurdere å også ha metoder for addUser, addRoom og addGroup, selv om disse ikke skal brukes når 
	// systemet er ferdig. Kan være nyttige mens vi tester kanskje. 
}
