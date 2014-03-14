
package Models;

public class EventParticipant {
	private String response = null;
	private boolean pendingChange = true;
	private boolean isDeleted = false;
	private int alarmId = -1;
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
    
    public EventParticipant(int eventId, int userId){
    	this.eventId  = eventId;
    	this.userId = userId;
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
    
    @Override
	public String toString() {
		return "EventParticipant [response=" + response
				+ ", pendingChange=" + pendingChange + ", isDeleted="
				+ isDeleted + ", alarmId=" + alarmId + ", userId=" + userId
				+ ", eventId=" + eventId + "]";
	}
}
