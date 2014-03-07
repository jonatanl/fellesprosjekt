
package Models;

public class EventParticipant {
	private int id;
	private String response;
	private boolean pendingChange;
	private boolean isDeleted;
	private int alarmId;
    private int userId;
    private int eventId;

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
