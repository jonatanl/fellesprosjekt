package Models;

import java.util.Date;

public class Alarm {
	private int alertID;
	private Date time;
	
	public int getAlertID() {
		return alertID;
	}
	public void setAlertID(int alertID) {
		this.alertID = alertID;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	public Alarm(){
		
	}
	
	public Alarm(Date alarmTime){
		this.time = alarmTime;
	}
	
}
