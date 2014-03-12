package Models;

import util.DateHelper;

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

    public void setStartTime(String startTime){
        this.startTime = DateHelper.convertToDate(startTime, DateHelper.FORMAT_DB);
    }

    public void setEndTime(String endTime) {
        this.endTime = DateHelper.convertToDate(endTime, DateHelper.FORMAT_DB);
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
