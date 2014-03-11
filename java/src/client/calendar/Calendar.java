package client.calendar;


/**
 * Created by ochamp on 3/10/14.
 */


public class Calendar {

    private Browser calendarView;

    public Calendar() {
        calendarView = new Browser();
    }

    public void addEvent(String id, String title, String startDate, String endDate) {
        calendarView.callJavaScript("addEvent(" + id + ", \'" + title + "\', \'" + startDate + "\', \'" + endDate + "\', false)");
    }

    public void removeAllEvents() {
        calendarView.callJavaScript("removeAllEvents()");
    }


    public Browser getContentForScene() {
        return calendarView;
    }
}
