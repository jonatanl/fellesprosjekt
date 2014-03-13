package client;

import client.calendar.CalendarView;
import interfaces.PersistencyInterface;

import java.util.ArrayList;

import util.DateHelper;
import Models.Alarm;
import Models.Event;
import Models.EventParticipant;
import Models.Group;
import Models.Room;
import Models.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
	private Event selectedEvent;

	private PersistencyInterface persistency;
	
	// Visible elements
	private Text title;
	private Buttons buttons;
	private CalendarView calendarView;

	public Event getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(int eventId) {
		this.selectedEvent = findEvent(eventId);
		buttons.setSelectedEvent(selectedEvent);
		buttons.setIsOwner(selectedEvent.getOwnerId() == loggedInUser.getUserId());
	}
	
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
        getAllFromDatabase();
        loggedInUser = findUser(loggedInUserId);

        title = new Text("Skalender");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        buttons = new Buttons(this);
        calendarView = new CalendarView(this);
        calendarView.setUserId(loggedInUser.getUserId());

        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));

        root.setAlignment(Pos.CENTER);
        root.add(title, 0, 0, 2, 1);
        root.add(buttons, 0, 1);
        root.add(calendarView.getContentForScene(), 1, 1);

		Scene scene = new Scene(root, 900, 700);
		
		stage.setScene(scene);
		stage.show();
        updateWebScene();
	}
	
	private void getAllFromDatabase(){
		users =  persistency.getAllUsers();
		events = persistency.getAllEvents();
        rooms = persistency.getAllRooms();
		eventParticipants = persistency.getAllEventParticipants();
		groups = persistency.getAllGroups();
		// Add members of subgroups to groups, so that groups contain own and subgroup's members. 
		for (Group g: groups)
		{
			addSubGroupMembers(g);
		}
		
		//alarms = persistency.getAllAlarms();
	}
	
	private Group addSubGroupMembers(Group g){
		for (int subGroupId: g.getSubGroups()){
			// Find subgroup.
			Group sg = findGroup(subGroupId);

			// Skip if subgroup does not exist. 
			if (sg == null)
				continue;
			
			// Recursively call subgroups of subgroups.
			sg = addSubGroupMembers(sg);

			// Add members of subgroup
			for (int member: sg.getMembers()){
				if (!g.getMembers().contains(member)){
					g.addMember(member);
				}
			}
		}
		
		return g;
	}
	
	public void changeEvent(int eventID, Event event) {
		Event original = findEvent(eventID);
		original = event;
		event.setEventId(eventID);
	}
	
	public void changeEventParticipantResponse(int eventId, int eventParticipantId,
			String newResponse, boolean newIsDeleted) {
		
	Event e = findEvent(eventId);
	
	if (e != null){
		//EventParticipant ep = e.findEventParticipant(eventParticipantId);
		//if (ep != null){
		//	ep.setResponse(newResponse);
		//	ep.setDeleted(newIsDeleted);
		}
	// TODO Inform other EventParticipants that someone changed their response, by setting their 
	// field 'pendingChange' to true. 
	}

	
	// Draws the webScene over again. 
	public void updateWebScene(){
        calendarView.removeAllEvents();

        for(Event event : events) {
            calendarView.addEvent(
                    "" + event.getEventId(),
                    event.getEventName(),
                    DateHelper.convertToString(event.getStartTime(), DateHelper.FORMAT_JAVASCRIPT),
                    DateHelper.convertToString(event.getEndTime(), DateHelper.FORMAT_JAVASCRIPT),
                    event.getOwnerId()
            );
        }
        System.out.println("-------------------------------------------------");
	}
	
	
	public User findUser(int userID){
		for (User u: users){
			if (u.getUserId() == userID){
				return u;
			}
		}
		return null;
	}
	
	public Event findEvent(int eventId){
		for (Event e: events){
			if (e.getEventId() == eventId){
				return e;
			}
		}
		return null;
	}
	
	public Group findGroup(int groupId){
		for (Group g: groups){
			if (g.getGroupId() == groupId){
				return g;
			}
		}
		return null;
	}

	
	public void addEvent(Event event) {
		events.add(event);
        updateWebScene();
	}
	
	
	public void removeEvent(Event event) {
		events.remove(event);
		updateWebScene();
	}
/*
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
