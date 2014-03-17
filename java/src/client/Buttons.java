package client;

import interfaces.PersistencyInterface;

import java.util.ArrayList;
import java.util.Date;

import util.Persistency;
import Models.Alarm;
import Models.Event;
import Models.EventParticipant;
import Models.Notification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Buttons extends VBox implements EventHandler<ActionEvent> {
	private Button b_createEvent, b_editEvent, b_deleteEvent, b_showMore, b_alarm, b_test, b_logout;
	private Label extraSpaceText1, extraSpaceText2;
	private Calendar calendar;
	private boolean isOwner = false;
	private Event selectedEvent = null;
	private EventParticipant eventParticipant;
	
	public boolean isOwner() {
		return isOwner;
	}
	
	// Set whether or not the user is the owner of the currently selected event. 
	public void setIsOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}
	
	// Set which event is currently selected in the calendar. 
	public void setSelectedEvent(Event selectedEvent) {
		this.selectedEvent = selectedEvent;
		// Buttons should be enabled/disabled according to whether an event is selected or not. 
		b_editEvent.setDisable(selectedEvent == null);
		b_deleteEvent.setDisable(selectedEvent == null);
		b_showMore.setDisable(selectedEvent == null);
		b_alarm.setDisable(selectedEvent == null);
	}
	
	public void setParticipant(EventParticipant ep){
		eventParticipant = ep;
		b_alarm.setDisable(ep == null);
	}
	
	public Buttons(Calendar calendar){
		this.calendar = calendar;
		
		extraSpaceText1 = new Label();
		extraSpaceText1.setMinHeight(50);
		
		// Create the buttons.
		b_createEvent = new Button("Add event");
		b_createEvent.setMinWidth(80);
		b_createEvent.setMinHeight(30);
		b_createEvent.setOnAction(this);
		
		b_editEvent = new Button("Edit");
		b_editEvent.setMinWidth(80);
		b_editEvent.setMinHeight(30);
		b_editEvent.setOnAction(this);
		
		b_deleteEvent = new Button("Remove");
		b_deleteEvent.setOnAction(this);
		b_deleteEvent.setMinWidth(80);
		b_deleteEvent.setMinHeight(30);
		
		b_showMore = new Button("Details");
		b_showMore.setOnAction(this);
		b_showMore.setMinWidth(80);
		b_showMore.setMinHeight(30);
		
		b_alarm = new Button("Alarm");
		b_alarm.setOnAction(this);
		b_alarm.setMinWidth(80);
		b_alarm.setMinHeight(30);
		
		b_test = new Button("Test");
		b_test.setOnAction(this);
		b_test.setMinWidth(80);
		b_test.setMinHeight(30);
		
		extraSpaceText2 = new Label();
		extraSpaceText2.setMinHeight(50);
		
		b_logout = new Button("Log out");
		b_logout.setOnAction(this);
		b_logout.setMinWidth(80);
		b_logout.setMinHeight(30);
		
		this.getChildren().addAll(extraSpaceText1, b_createEvent, b_editEvent, b_deleteEvent, b_showMore, b_alarm, b_test, extraSpaceText2, b_logout);
		this.setSpacing(10);
		
		setSelectedEvent(null);
	}

	
	
	@Override
	public void handle(ActionEvent buttonEvent) {
		if (buttonEvent.getSource() == b_createEvent) {
			System.out.println("legg til event");
			new AddEvent(calendar, calendar.getStage(), calendar.getPersistency(), calendar.getLoggedInUser().getUserId(), calendar.getRooms(), calendar.getUsers(), calendar.getGroups());
		}
		
		else if (buttonEvent.getSource() == b_editEvent) {
			
			ArrayList<EventParticipant> currentParticipants = new ArrayList<EventParticipant>();
			for (EventParticipant ep: calendar.getEventParticipants()){
				if (ep.getEventId() == selectedEvent.getEventId()){
					currentParticipants.add(ep);
				}
			}
			
			if (isOwner){
				new EditOwner(selectedEvent, calendar.getStage(), calendar.getRooms(), calendar.getUsers(), calendar.getGroups(), currentParticipants, calendar.getPersistency(), calendar);
			}
			else{
				new EndreIkkeOwner(calendar, selectedEvent, calendar.getStage(), calendar.getPersistency());
			}
		}
		else if (buttonEvent.getSource() == b_deleteEvent){
			if (isOwner){
				new DeleteEventOwner(calendar, selectedEvent, calendar.getStage(), calendar.getPersistency());
			}
		}
		
		else if (buttonEvent.getSource() == b_showMore) {
			ArrayList<EventParticipant> currentParticipants = new ArrayList<EventParticipant>();
			for (EventParticipant ep: calendar.getEventParticipants()){
				if (ep.getEventId() == selectedEvent.getEventId()){
					currentParticipants.add(ep);
				}
			}
			new ShowMore(calendar, selectedEvent, calendar.getStage(), currentParticipants, calendar.getUsers(), calendar.getRooms());
		}
		else if (buttonEvent.getSource() == b_alarm) {
			new EditAlarm(calendar.getPersistency(), calendar, calendar.getStage(),calendar.findAlarm(selectedEvent.getEventId(), calendar.getLoggedInUser().getUserId()), 
					selectedEvent.getEventId(), calendar.getLoggedInUser().getUserId());
		}		
		else if (buttonEvent.getSource() == b_test){
			// Add code here for testing.
		}
		
		else if (buttonEvent.getSource() == b_logout){
			calendar.getStage().close();
			
			Login login = new Login(calendar, calendar.getPersistency());
			login.createStage();
		}
	}
}
