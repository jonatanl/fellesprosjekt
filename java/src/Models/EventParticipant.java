
package Models;

public class EventParticipant {
	private String response;
	private boolean pendingChange;
	private boolean isDeleted;
	private Alarm alarm;

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
	public Alarm getAlarm() {
		return alarm;
	}
	public void setAlarm(Alarm alarm) {
		this.alarm = alarm;
	}
}
