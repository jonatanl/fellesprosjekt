package client;

import interfaces.CalendarInterface;

import java.util.ArrayList;
import java.util.Date;

import Models.Alarm;
import Models.Event;
import Models.EventParticipant;
import Models.Group;
import Models.Room;
import Models.User;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//Main class of the calendar system. 
public class Calendar extends Application{
	
	private ArrayList<Event> events;
	private ArrayList<User> users;
	private ArrayList<Group> groups;
	private ArrayList<Room> rooms;
	
	public static void main(String[] args)  {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        
        Text title = new Text("Calendar system, main window.");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(title, 0, 0, 2, 1);
		
		Scene scene = new Scene(grid, 300, 275);
        stage.setScene(scene);
        stage.show();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*
	@Override
	public void addEvent(Event event) {
		events.add(event);
		// TODO Update the webView. 
	}

	@Override
	public void removeEvent(Event event) {
		events.remove(event);
		// TODO Update the webView.
	}

	public void changeEvent(int eventId, String newEventName,
			Date newStartTime, Date newEndTime, String newDescription,
			String newLocation, Room newRoom) {
		Event e = findEvent(eventId);
		e.setEventName(newEventName);
		e.setStartTime(newStartTime);
		e.setEndTime(newEndTime);
		e.setDescription(newDescription);
		e.setLocation(newLocation);
		//e.setRoom(newRoom);
		
		// TODO Update webview. 
		
	}
	
	public Event findEvent(int eventID){
		for (Event e: events){
			if (e.getEventId() == eventID){
				return e;
			}
		}
		return null;
	}

	public User findUser(int userID){
		for (User u: users){
			if (u.getUserId() == userID){
				return u;
			}
		}
		return null;
	}
	
	@Override
	public void addEventParticipant(int eventID, EventParticipant participant) {
		Event e = findEvent(eventID);
		if (e != null){
			e.addEventParticipant(participant);
		}
	}

	@Override
	public void removeEventParticipant(int eventID, EventParticipant participant) {
		Event e = findEvent(eventID);
		if (e != null){
			e.removeEventParticipant(participant);
		}
	}
	
	public void changeEventParticipantResponse(int eventId, int eventParticipantId,
			String newResponse, boolean newIsDeleted) {
		
		Event e = findEvent(eventId);
		
		if (e != null){
			EventParticipant ep = e.findEventParticipant(eventParticipantId);
			if (ep != null){
				ep.setResponse(newResponse);
				ep.setDeleted(newIsDeleted);
			}
		}
		// TODO Inform other EventParticipants that someone changed their response, by setting their 
		// field 'pendingChange' to true. 
		// TODO Inform webView. 
		
	}
	
	@Override
	public void addAlarm(EventParticipant participant, Alarm alarm) {
		//participant.setAlarm(alarm);
		// TODO Inform webView
	}
	
	@Override
	public void removeAlarm(EventParticipant participant) {
		//participant.setAlarm(null);
		// TODO inform webView.
		
	}

	@Override
	public void changeAlarm(EventParticipant participant, Date newAlarmTime) {
		//participant.setAlarm(new Alarm(newAlarmTime));
		// TODO inform webView.
	}
	
	public void addRoom(Room room){
		rooms.add(room);
	}
	
	public void removeRoom(Room room){
		rooms.remove(room);
	}
*/
	


	
}
