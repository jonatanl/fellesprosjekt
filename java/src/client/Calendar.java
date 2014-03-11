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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import client.calendar.CalendarView;
import util.DateHelper;

//Main class of the calendar system. 
public class Calendar extends Application implements EventHandler<ActionEvent>{
	
	private Button b_createEvent, b_editEvent, b_deleteEvent, b_showMore, b_alert;
	private Stage stage;
    private CalendarView calendarView;
	
	private ArrayList<Event> events;
	private ArrayList<User> users;
	private ArrayList<Group> groups;
	private ArrayList<Room> rooms;
	private ArrayList<EventParticipant> eventParticipants;
	private ArrayList<Alarm> alarms;
	
	private User loggedInUser;
	
	private PersistencyInterface persistency; 
	
	public static void main(String[] args)  {
		launch(args);
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
	
	public void startCalendar(int loggedInUserId){
		System.out.println("logged in with id " + loggedInUserId);
		getAllFromDatabase();
		loggedInUser = findUser(loggedInUserId);
		
		// Create the buttons.
		b_createEvent = new Button("Legg til");
		b_createEvent.setMinWidth(60);
		b_createEvent.setOnAction(this);
		
		b_editEvent = new Button("Endre");
		b_editEvent.setMinWidth(60);
		b_editEvent.setOnAction(this);
		
		b_deleteEvent = new Button("Slett");
		b_deleteEvent.setOnAction(this);
		b_deleteEvent.setMinWidth(60);
		
		b_showMore = new Button("Vis mer");
		b_showMore.setOnAction(this);
		b_showMore.setMinWidth(60);
		
		b_alert = new Button("Alarm");
		b_alert.setOnAction(this);
		b_alert.setMinWidth(60);
		
		VBox root = new VBox();
		root.getChildren().addAll(b_createEvent, b_editEvent, b_deleteEvent, b_showMore, b_alert);
		
		//Scene scene = new Scene(root, 500, 500);
		
		
		javafx.scene.Group g = new javafx.scene.Group();
		calendarView = new CalendarView();
		HBox h = new HBox();
		h.getChildren().add(root);
		h.getChildren().add(calendarView.getContentForScene());

        Scene scene = new Scene(h, 1000, 1000);
		
		stage.setScene(scene);
		stage.show();
	}
	
	private void getAllFromDatabase(){
		users =  persistency.getAllUsers();
		events = persistency.getAllEvents();
		rooms = persistency.getAllRooms();
		eventParticipants = persistency.getAllEventParticipants();
		groups = persistency.getAllGroups();
		//alarms = persistency.getAllAlarms();
	}
	
	
	
	// Draws the webScene over again. 
	public void updateWebScene(){
        calendarView.removeAllEvents();
        for(Event event : events) {
            calendarView.addEvent(
                    "" + event.getEventId(),
                    event.getEventName(),
                    DateHelper.convertToString(event.getStartTime(), DateHelper.FORMAT_JAVASCRIPT),
                    DateHelper.convertToString(event.getEndTime(), DateHelper.FORMAT_JAVASCRIPT)
            );
        }
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
	public void handle(ActionEvent buttonEvent) {
		if (buttonEvent.getSource() == b_createEvent) {
			System.out.println("legg til event");
			new AddEvent(stage, persistency, loggedInUser.getUserId(), rooms, users, groups);
		}
		
		else if (buttonEvent.getSource() == b_editEvent) {
			
			Event currentEvent = new Event();
			currentEvent.setEventId(10);
			ArrayList<EventParticipant> currentParticipants = new ArrayList<EventParticipant>();
			for (EventParticipant ep: eventParticipants){
				if (ep.getEventId() == currentEvent.getEventId()){
					currentParticipants.add(ep);
				}
			}
			
			
			new EditOwner(new Event(), stage, rooms, users, groups, currentParticipants);			
		}
		else if (buttonEvent.getSource() == b_showMore) {
			//new ShowMore(model, stage);
		}
		
		else if (buttonEvent.getSource() == b_alert) {
			
		}		
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
