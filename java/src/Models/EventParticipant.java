
package Models;

public class EventParticipant {
	private int id;
	private String response;
	private boolean pendingChange;
	private boolean isDeleted;
	private int alarmId;
    private int userId;
    private int eventId;
    
    public static final String going = "isGoing";
    public static final String notGoing = "isNotGoing";
    
    public EventParticipant() {
    
    }

    public EventParticipant(EventParticipant ep) {
		this.response = ep.getResponse();
		this.pendingChange = ep.isPendingChange();
		this.isDeleted = ep.isDeleted();
		this.alarmId = ep.getAlarmId();
		this.userId = ep.getUserId();
		this.eventId = ep.getEventId();
	}
	
	public int getId() {
		return id;
	}

	public int getEventId() {
        return eventId;
    }
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public boolean isPendingChange() {
		return pendingChange;
	}
	public void setPendingChange(boolean pendingChange) {
		this.pendingChange = pendingChange;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
    public int getAlarmId() {
        return alarmId;
    }
    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }
}
