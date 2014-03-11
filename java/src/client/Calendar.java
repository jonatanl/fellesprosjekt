package client;

import interfaces.CalendarInterface;
import interfaces.PersistencyInterface;

import java.util.ArrayList;
import java.util.Date;

import Models.Alarm;
import Models.Event;
import Models.EventParticipant;
import Models.Group;
import Models.Room;
import Models.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import client.calendar.Browser;

//Main class of the calendar system. 
public class Calendar extends Application{
	
	
	private Stage stage;
	
	private ArrayList<Event> events;
	private ArrayList<User> users;
	private ArrayList<Group> groups;
	private ArrayList<Room> rooms;
	private ArrayList<EventParticipant> eventParticipants;
	private ArrayList<Alarm> alarms;
	
	private User loggedInUser;
	
	private PersistencyInterface persistency; 
	
	// Visible elements
	private Text title;
	private Buttons buttons;
	private Browser browser;
	
	public ArrayList<User> getUsers() {
		return users;
	}

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public ArrayList<EventParticipant> getEventParticipants() {
		return eventParticipants;
	}

	public static void main(String[] args)  {
		launch(args);
	}
	
	public User getLoggedInUser() {
		return loggedInUser;
	}

	public PersistencyInterface getPersistency() {
		return persistency;
	}

	public Stage getStage(){
		return stage;
	}
	
	

	@Override
	public void start(Stage stage) throws Exception {
		persistency = new util.Persistency();
		this.stage = stage;
		
		Login login = new Login(this, persistency);
		login.createStage();
	}
	
	@Override
	public void stop(){
		try {
			super.stop();
			persistency.closeConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void startMainView(int loggedInUserId){
		System.out.println("logged in with id " + loggedInUserId);
		getAllFromDatabase();
		loggedInUser = findUser(loggedInUserId);

		title = new Text("Skalender");
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		buttons = new Buttons(this);
		browser = new Browser();

		GridPane root = new GridPane();
		root.setAlignment(Pos.CENTER);
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(25, 25, 25, 25));

		root.setAlignment(Pos.CENTER);
		root.add(title, 0, 0, 2, 1);
		root.add(buttons, 0, 1);
		root.add(browser, 1, 1);
		
		Scene scene = new Scene(root, 900, 700);
		
		stage.setScene(scene);
		stage.show();
	}
	
	private void getAllFromDatabase(){
		users =  persistency.getAllUsers();
		//events = persistency.getAllEvents();
		rooms = persistency.getAllRooms();
		eventParticipants = persistency.getAllEventParticipants();
		groups = persistency.getAllGroups();
		//alarms = persistency.getAllAlarms();
	}
	
	
	
	// Draws the webScene over again. 
	public void updateWebScene(){
		
	}
	
	
	public User findUser(int userID){
		for (User u: users){
			if (u.getUserId() == userID){
				return u;
			}
		}
		return null;
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
