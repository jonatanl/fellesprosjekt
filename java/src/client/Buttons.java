package client;

import java.util.ArrayList;
import java.util.Date;

import Models.Event;
import Models.EventParticipant;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Buttons extends VBox implements EventHandler<ActionEvent> {
	private Button b_createEvent, b_editEvent, b_deleteEvent, b_showMore, b_alarm, b_test;
	private Calendar calendar;
	private boolean isOwner = false;
	private Event selectedEvent = null;
	
	public boolean isOwner() {
		return isOwner;
	}
	
	// Set whether or not the user is the owner of the currently selected event. 
	public void setOwner(boolean isOwner) {
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
	
	public Buttons(Calendar calendar){
		this.calendar = calendar;
		
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
		
		this.getChildren().addAll(b_createEvent, b_editEvent, b_deleteEvent, b_showMore, b_alarm, b_test);
		this.setSpacing(10);
		
		//setSelectedEvent(null);
	}

	
	
	@Override
	public void handle(ActionEvent buttonEvent) {
		if (buttonEvent.getSource() == b_createEvent) {
			System.out.println("legg til event");
			new AddEvent(calendar, calendar.getStage(), calendar.getPersistency(), calendar.getLoggedInUser().getUserId(), calendar.getRooms(), calendar.getUsers(), calendar.getGroups());
		}
		
		else if (buttonEvent.getSource() == b_editEvent) {
			
			Event currentEvent = new Event();
			currentEvent.setEventId(10);
			ArrayList<EventParticipant> currentParticipants = new ArrayList<EventParticipant>();
			for (EventParticipant ep: calendar.getEventParticipants()){
				if (ep.getEventId() == currentEvent.getEventId()){
					currentParticipants.add(ep);
				}
			}
			
			
			new EditOwner(new Event(), calendar.getStage(), calendar.getRooms(), calendar.getUsers(), calendar.getGroups(), currentParticipants);			
		}
		else if (buttonEvent.getSource() == b_showMore) {
			//new ShowMore(model, stage);
		}
		
		else if (buttonEvent.getSource() == b_alarm) {
			
		}		
		else if (buttonEvent.getSource() == b_test){
			// Testing
						Event e = new Event();
						e.setEventId(20);
						e.setEventName("Skalender progging");
						Date d = new Date();
						e.setStartTime(new Date(2014, 3, 10, 12, 0, 0));
						e.setEndTime(new Date(2014, 3, 10, 13, 0, 0));
						calendar.addEvent(e);
		}
	}
}
