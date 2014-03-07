package util;

import Models.Alarm;
import Models.Event;
import Models.EventParticipant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBQuery extends DBConnection {

    public DBQuery() {
        connect();
    }

    public Event getEvent(int eventId) throws SQLException {
        Event event = new Event();
        String query = "SELECT * FROM event WHERE event.eventID=" + eventId;
        ResultSet result = getResult(query);

        event.setEventId(eventId);
        event.setEventName(result.getString("eventName"));
        event.setStartTime(result.getDate("startTime").toString());
        event.setEndTime(result.getDate("endTime").toString());
        event.setDescription(result.getString("description"));
        event.setLocation(result.getString("location"));
        event.setRoomId(result.getInt("roomID"));
        event.setOwnerId(result.getInt("ownerID"));

        return event;
    }

    public void updateEvent(Event event) throws SQLException{
        String query = "UPDATE event set eventName=?, endTime=?, startTime=?, description=?, location=?, roomID=?, ownerID=?"
                + "WHERE eventID=" + event.getEventId();
        PreparedStatement statement = connection.prepareStatement(query);
        setEventFields(statement, event);
        statement.executeUpdate();
    }

    public void addEvent(Event event, ArrayList<Integer> participantIDs) throws SQLException{
        if (event.getEventId() != 0)
            return;
        String query = "INSERT INTO calendar.event VALUES(default , ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        setEventFields(statement, event);
        statement.executeUpdate();

        for (int participantID : participantIDs){
            addEventParticipant(event.getEventId(), participantID);
        }
    }

    private void setEventFields (PreparedStatement statement, Event event) throws SQLException{
        statement.setString(1, event.getEventName());
        statement.setString(2, event.getStartTime().toString());
        statement.setString(3, event.getEndTime().toString());
        statement.setString(4, event.getDescription());
        statement.setString(5, event.getLocation());
        statement.setString(6, event.getRoomId() + "");
        statement.setString(7, event.getOwnerId() + "");
    }

    public void removeEvent(Event event) throws SQLException {
        String query = "DELETE FROM calendar.event WHERE eventID=" + event.getEventId();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
    }

    public void addEventParticipant(int eventID, int participantID) throws SQLException {
        String query = "INSERT INTO eventParticipant(eventID, userID) VALUES(?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, eventID);
        statement.setInt(2, participantID);
        statement.executeUpdate();
    }

    public void removeEventParticipant (int eventID, int participantID) throws SQLException {
        String query = "DELETE FROM calendar.eventParticipant WHERE eventID=? AND userID=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, eventID);
        statement.setInt(2, participantID);
        statement.executeUpdate();
    }

    public void addAlarm(EventParticipant participant, Alarm alarm) throws SQLException {
        String query = "INSERT INTO alarm VALUES(default, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, alarm.getTime());
        alarm.setAlarmID(statement.executeUpdate());

        query = "UPDATE eventParticipant SET alarmID=? WHERE userID=? AND eventID=?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, alarm.getAlarmID());
        statement.setInt(2, participant.getUserId());
        statement.setInt(3, participant.getEventId());
        statement.executeUpdate();
    }
}
