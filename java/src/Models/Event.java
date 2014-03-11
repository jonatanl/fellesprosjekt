package Models;

import sun.tools.jar.resources.jar;

import java.util.ArrayList;
import java.util.Date;

public class Event {
	
    private int eventId;
    private String eventName;
	private Date startTime;
    private Date endTime;
    private String description;
    private String location;
    private int roomId;
    private int ownerId;

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

    public void setStartTime(java.sql.Date startTime){
        this.startTime = new Date(startTime.getTime());
    }

    public void setEndTime(java.sql.Date endTime) {
        this.endTime = new Date(endTime.getTime());
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
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", roomId=" + roomId +
                ", ownerId=" + ownerId +
                '}';
    }
}
