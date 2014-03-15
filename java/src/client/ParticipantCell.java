package client;

import Models.Event;
import Models.EventParticipant;
import Models.Group;
import Models.User;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ParticipantCell extends ListCell<Object> {
	private static Calendar calendar;
	private Event event;
	
	public ParticipantCell(Event event){
		this.event = event;
	}
	
	public static void setCalendar(Calendar c){
		calendar = c;
	}
	
	@Override
    public void updateItem(Object item, boolean empty) {
		super.updateItem(item, empty);
		if (item instanceof User){
			// The item should be colored according to response. 
			Label l = new Label();
			l.setText(((User) item).getUsername());
			EventParticipant ep = calendar.findEventParticipant(((User) item).getUserId(), event.getEventId());
			if (ep != null){
				if (ep.getResponse() != null){
					if (ep.getResponse().equals(EventParticipant.going)){
						l.setTextFill(Color.GREEN);
					}
					else if (ep.getResponse().equals(EventParticipant.notGoing)){
						l.setTextFill(Color.RED);
					}
				}
			}
			setGraphic(l);
		}
		else if (item instanceof Group){
			Label l = new Label();
			l.setText(((Group) item).getName());
			setGraphic(l);
		}
	}
}


/*
static class ColorRectCell extends ListCell<String> {
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        Rectangle rect = new Rectangle(100, 20);
        if (item != null) {
            rect.setFill(Color.web(item));
            setGraphic(rect);
        }
    }
}
*/