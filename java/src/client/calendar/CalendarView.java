package client.calendar;

import client.Calendar;


/**
 * Created by ochamp on 3/10/14.
 */


public class CalendarView {

    private Browser calendar;

    public CalendarView(Calendar c) {
    	Browser.calendar = c;
        calendar = new Browser();
    }

    public void addEvent(String id, String title, String startDate, String endDate) {
        calendar.callJavaScript("addEvent(" + id + ", \'" + title + "\', \'" + startDate + "\', \'" + endDate + "\', false)");
    }

    public void removeAllEvents() {
        calendar.callJavaScript("removeAllEvents()");
    }

    public void setUserId(int id) {
        calendar.callJavaScript("setUserId(" + id + ")");
    }

    public Browser getContentForScene() {
        return calendar;
    }
}
