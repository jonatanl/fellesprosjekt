package Models;

import util.DateHelper;

public class Notification {
	
	private String message;
	private int userId;
	private int eventId;
	
	
	public int getUserId(){
		return userId;
	}
	
	public int getEventId(){
		return eventId;
	}
	
	public Notification(int relevantUserId, Event event){
		this.userId = relevantUserId;
		this.eventId = event.getEventId();
		this.message = "The event '" + event.getEventName() + "' was changed.";
	}
	
	@Override
	public String toString(){
		return message;
	}
}
