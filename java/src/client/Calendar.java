package client;

import Models.*;
import client.calendar.CalendarView;
import interfaces.PersistencyInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.DateHelper;

import java.util.ArrayList;
import java.util.HashMap;

//Main class of the calendar system. 
public class Calendar extends CalendarLists {

	private Stage stage;

	private User loggedInUser;
	private Event selectedEvent;

	private PersistencyInterface persistency;

	// Visible elements
	private Text title;
	private Buttons buttons;
	private CalendarView calendarView;
	private Notifications notifications;

    private ListView<User> userListView;
    private ObservableList<User> userObservableList;

	public Event getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(int eventId) {
		if (eventId == -1){
			// No event selected.
			this.selectedEvent = null;
			buttons.setSelectedEvent(null);
			buttons.setParticipant(null);
			buttons.setIsOwner(false);
		}
		else{
			this.selectedEvent = findEvent(eventId);
			buttons.setSelectedEvent(selectedEvent);
			buttons.setParticipant(findEventParticipant(loggedInUser.getUserId(), eventId));
			buttons.setIsOwner(selectedEvent.getOwnerId() == loggedInUser.getUserId());
			
			// Fjern eventuelle notifications.
			EventParticipant ep = findEventParticipant(loggedInUser.getUserId(), selectedEvent.getEventId());
			if (ep != null && ep.isPendingChange()){
				ep.setPendingChange(false);
				persistency.changeEventParticipantResponse(ep);
				updateNotifications();
				updateWebScene();
			}
			
			
		}
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
		ParticipantCell.setCalendar(this);
		
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
        selectedUsers = new ArrayList<>();

        userListView = new ListView<>();
        userObservableList = FXCollections.observableList(users);
        userListView.setItems(userObservableList);
        userListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        userListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observableValue, User user, User user2) {
                selectedUsers = new ArrayList<>();
                for (User u : userListView.getSelectionModel().getSelectedItems()) {
                    selectedUsers.add(u);
                    updateWebScene();
                }
            }
        });
        userListView.getSelectionModel().select(loggedInUser);
        
        //notifications = new Notifications();

        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));

        root.setAlignment(Pos.CENTER);
        root.add(title, 0, 0, 2, 1);
        root.add(buttons, 0, 1);
        root.add(calendarView.getContentForScene(), 1, 1);
        root.add(userListView, 2, 1);
        //root.add(notifications, 0,3);

		Scene scene = new Scene(root, 1150, 700);
		
		stage.setScene(scene);
		stage.setTitle("Skalender");
		stage.show();
		updateNotifications();
        updateWebScene();
	}
	
	public Notifications getNotifications(){
		return notifications;
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
		
		alarms = persistency.getAllAlarms();
	}
	
	public void setAlarm(Alarm a, int userId, int eventId){
		EventParticipant ep = findEventParticipant(userId, eventId);
		if (a == null){
			// Remove alarm.
			ep.setAlarmId(-1);
		}
		else if (ep.getAlarmId() == -1){
			// No alarm set yet. 
			alarms.add(a);
			ep.setAlarmId(a.getAlarmID());
		}
		else{
			// Update alarm. 
			Alarm original = findAlarm(eventId, userId);
			original.setTime(a.getTime());
		}
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
	
	public void changeEvent(int eventId, Event newEventInfo) {
		Event original = findEvent(eventId);
		original = newEventInfo;
		newEventInfo.setEventId(eventId);
		
		// Update isPendingChange on all the OTHER participants.
				for (EventParticipant epOther: eventParticipants){
					if (epOther.getEventId() == eventId && 
							(epOther.getUserId() != loggedInUser.getUserId())
							){
						epOther.setPendingChange(true);
						persistency.changeEventParticipantResponse(epOther);
					}
				}
	}
	
	public void changeEventParticipantResponse(int eventId, int userId,
			String newResponse, boolean newIsDeleted) {
		EventParticipant ep = findEventParticipant(userId, eventId);
		ep.setResponse(newResponse);
		ep.setDeleted(newIsDeleted);
		
		// Update isPendingChange on all the OTHER participants.
		for (EventParticipant epOther: eventParticipants){
			if (epOther.getEventId() == eventId && 
					((userId == loggedInUser.getUserId() && epOther != ep) ||
					(userId != loggedInUser.getUserId() && epOther.getUserId() != loggedInUser.getUserId()))
					){
				epOther.setPendingChange(true);
				persistency.changeEventParticipantResponse(epOther);
			}
		}
	}
	
	// Draws the webScene over again. 
	public void updateWebScene(){
        calendarView.removeAllEvents();

        HashMap visibleUsersHm = new HashMap();
        for (User u : selectedUsers) {
        	visibleUsersHm.put(u.getUserId(), u);
        }
        
        for(Event event : events) {
        	boolean drawEvent = false;
        	boolean changed = false;
        	boolean attending = false;
        	boolean myEvent = false;
            
            // One of the visible users owns the event
            if (visibleUsersHm.get(event.getOwnerId()) != null){
            	drawEvent = true;
            }
            
            // one of the visible users is a participant of the event.
            for (User u: selectedUsers){
            	EventParticipant ep = findEventParticipant(u.getUserId(), event.getEventId()); 
            	if (ep != null){
            		// Do not draw if the participant deleted the event. 
            		drawEvent = !ep.isDeleted();
            		if (drawEvent)
            			break;
            	}
            }
            
            // If drawEvent is not true at this point, skip the event. 
            if (!drawEvent){
            	continue;
            }
            
            // We got to this point --> Add the event!
            
            
            // myEvent, changed, attending (Merk at changed og attending bare er relevante dersom myEvent == true).
            EventParticipant epLoggedInUser = findEventParticipant(loggedInUser.getUserId(), event.getEventId());
            if (visibleUsersHm.get(loggedInUser.getUserId()) != null && (event.getOwnerId() == loggedInUser.getUserId() || epLoggedInUser != null)){
            	myEvent = true;
            	if (epLoggedInUser != null){
                    changed = epLoggedInUser.isPendingChange();
                    if (epLoggedInUser.getUserId() == event.getOwnerId()) {
                        attending = true;
                    } else {
                        attending = epLoggedInUser.getResponse().equals(EventParticipant.going);
                    }
            	}
            }

            String status = "lol"; //String status to be added for product requirement
        	calendarView.addEvent(
        			"" + event.getEventId(), 
        			event.getEventName(), 
        			DateHelper.convertToString(event.getStartTime(), DateHelper.FORMAT_JAVASCRIPT), 
        			DateHelper.convertToString(event.getEndTime(), DateHelper.FORMAT_JAVASCRIPT),
        			event.getOwnerId(), 
        			changed, 
        			attending, 
        			myEvent, status);
        }

        // No selected event after webscene update. 
        setSelectedEvent(-1);
	}

	public void addEvent(Event event) {
		events.add(event);
        updateWebScene();
	}

	public void removeEvent(Event event) {
		System.out.println("index of: " + events.indexOf(event));
		events.remove(events.indexOf(event));
		updateWebScene();
	}

	public void addEventParticipant(EventParticipant participant) {
		eventParticipants.add(participant);
		updateNotifications();
		updateWebScene();
	}
	
	public void addEventParticipants(ArrayList<EventParticipant> participants){
		for (EventParticipant ep: participants){
			if (ep.getUserId() == loggedInUser.getUserId()){
				ep.setPendingChange(false);
				persistency.changeEventParticipantResponse(ep);
			}
		}
		eventParticipants.addAll(participants);
		updateNotifications();
		updateWebScene();
	}
	
	private void updateNotifications(){
		return;
		// Remove all notifications and add again.
		/*
		notifications.removeAll();
		for (EventParticipant ep: eventParticipants){
			if (ep.isPendingChange() && ep.getUserId() == loggedInUser.getUserId()){
				notifications.addNotification(new Notification(ep.getUserId(),findEvent(ep.getEventId())));
			}
		}
		*/
	}
	
	public void removeEventParticipant(EventParticipant ep){
		EventParticipant epOriginal = findEventParticipant(ep.getUserId(), ep.getEventId());
		eventParticipants.remove(epOriginal);
		updateWebScene();
	}
	
	public void removeEventNotOwner(int eventID, int participantID, String status, boolean ans){
		changeEventParticipantResponse(eventID, participantID, status, ans);
		updateWebScene();
	}
	
}
