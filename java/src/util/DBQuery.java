package util;

import Models.*;

import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class DBQuery extends DBQueryGetMethods {

    public DBQuery() {
        connect();
    }

    public void updateEvent(Event event) throws SQLException{
        String query = "UPDATE event set eventName=?, startTime=?, endTime=?, description=?, location=?, roomID=?, ownerID=?"
                + "WHERE eventID=" + event.getEventId();
        PreparedStatement statement = connection.prepareStatement(query);
        setEventFields(statement, event);
        statement.executeUpdate();
    }

    public boolean addEvent(Event event, ArrayList<Integer> participantIDs) throws SQLException{
        if (event.getEventId() != 0)
            return false;
        String query = "INSERT INTO calendar.event VALUES(default , ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);
        setEventFields(statement, event);
        statement.executeUpdate();

        // We need to get the row id from the new row, before we can add eventParticipants
        ResultSet result = statement.getGeneratedKeys();
        result.next();
        event.setEventId(result.getInt(1));

        if (event.getEventId()==0)
            return false;
        for (int participantID : participantIDs){
            addEventParticipant(event.getEventId(), participantID);
        }

        return true;
    }

    private void setEventFields (PreparedStatement statement, Event event) throws SQLException{
        statement.setString(1, event.getEventName());
        statement.setString(2, DateHelper.convertToString(event.getStartTime(), DateHelper.FORMAT_DB));
        statement.setString(3, DateHelper.convertToString(event.getEndTime(), DateHelper.FORMAT_DB));
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

    public int requestLogin(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username=? AND password=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet result = getResult(statement);
        int id = result.getInt("userID");

        return id > 0 ? id : -1;
    }

    public void changeEventParticipant(EventParticipant participant) throws SQLException {
        String query = "UPDATE eventParticipant set isDeleted=?, pendingChange=?, response=? WHERE eventID=? AND userID=?";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, participant.isDeleted()? 1 : 0);
        statement.setInt(2, participant.isPendingChange()? 1 : 0);
        statement.setString(3, participant.getResponse());
        statement.setInt(4, participant.getEventId());
        statement.setInt(5, participant.getUserId());

        statement.executeUpdate();
    }
}
