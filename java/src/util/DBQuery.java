package util;

import Models.Event;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBQuery extends DBConnection {

    public DBQuery() {
        connect();

        Event event = null;
        try {
            event = getEvent(1);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println(event);

        close();
    }

    public static void main(String[] args) {
        new DBQuery();
    }

    public void close(){
        super.close();
    }

    public Event getEvent(int eventId) throws SQLException {
        Event event = new Event();
        String query = "SELECT * FROM event WHERE event.eventID=" + eventId;
        ResultSet result = getResult(query);

        event.setEventId(eventId);
        event.setEventName(result.getString("eventName"));
        event.setStartTime(result.getDate("startTime"));
        event.setEndTime(result.getDate("endTime"));
        event.setDescription(result.getString("description"));
        event.setLocation(result.getString("location"));

        return event;
    }

    public void updateEvent(Event event) {

    }
}
