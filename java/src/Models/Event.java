package Models;

import java.util.ArrayList;
import java.util.Date;

public class Event {
    private int eventId;
    private String eventName;
    private Date startTime;
    private Date endTime;
    private String description;
    private String location;
    private Room room;
    private User user;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                ", room=" + room +
                ", user=" + user +
                '}';
    }
}
