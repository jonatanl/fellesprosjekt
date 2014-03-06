package Models;

import java.util.ArrayList;
import java.util.Date;

public class Event {
    public static final String[] DATABASE_FIELDS = {"eventName", "startTime", "endTime", "description", "location", "roomID", "ownerID"};

    private int eventId;
    private String eventName;
    private String date;
	private String startTime;
    private String endTime;
    private String description;
    private String location;
    private int roomId;
    private int ownerId;
    
    private ArrayList<EventParticipant> eventParticipants;

    public Event(){
    	eventParticipants = new ArrayList<EventParticipant>();
    }
    
    public void addEventParticipant(EventParticipant eventParticipant){
    	this.eventParticipants.add(eventParticipant);
    }
    
    public void removeEventParticipant(EventParticipant eventParticipant){
    	this.eventParticipants.remove(eventParticipant);
    }
    
    public EventParticipant findEventParticipant(int eventParticipantId){
    	
    	for (EventParticipant ep: eventParticipants){
    		if (ep.getEventId() == eventParticipantId){
    			return ep;
    		}
    	}
    	
    	
    	return null;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    
    public String getDate() {
    	return date;
    }
    
    public void setDate(String date) {
    	this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }


    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", roomId=" + roomId +
                ", ownerId=" + ownerId +
                '}';
    }
}
