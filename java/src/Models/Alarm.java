package Models;

import java.util.Date;

public class Alarm {
	private int alertID;
	private String time;

    public int getAlertID() {
        return alertID;
    }

    public void setAlertID(int alertID) {
        this.alertID = alertID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
