package client;

import interfaces.CalendarInterface;

import java.util.ArrayList;

import Models.Alarm;
import Models.Event;
import Models.EventParticipant;
import Models.Group;
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
public class Calendar extends Application implements CalendarInterface{
	
	private ArrayList<Event> events;
	private ArrayList<User> users;
	private ArrayList<Group> groups;
	
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

	@Override
	public void changeEvent(Event event) {
		
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
				
	}

	@Override
	public void changeEventParticipantResponse(EventParticipant participant) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAlarm(EventParticipant participant, Alarm alarm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAlarm(EventParticipant participant, Alarm alarm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeAlarm(EventParticipant participant, Alarm alarm) {
		// TODO Auto-generated method stub
		
	}
	
	
}
