package util;

import Models.*;
import interfaces.PersistencyInterface;

import java.sql.SQLException;
import java.util.ArrayList;

public class Persistency extends PersistencyGetMethods implements PersistencyInterface {

    public Persistency() {
        query = new DBQuery();

        System.out.println(requestLogin("johannes", "123456"));

        query.close();
    }

    public static void main(String[] args) {
        new Persistency();
    }

    @Override
    public int requestLogin(String username, String password) {
        int id = -1;
        try {
            id = query.requestLogin(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void addEvent(Event event, ArrayList<Integer> participantIDs) {
        try {
            query.addEvent(event, participantIDs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeEvent(Event event) {
        try {
            query.removeEvent(event);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void changeEvent(Event event) {
        try {
            query.updateEvent(event);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addEventParticipant(int eventID, int participantID) {
        try {
            query.addEventParticipant(eventID, participantID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeEventParticipant(int eventID, int participantID) {
        try {
            query.removeEventParticipant(eventID, participantID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void changeEventParticipantResponse(EventParticipant participant) {

    }
    @Override
    public void addAlarm(EventParticipant participant, Alarm alarm) {
        try {
            query.addAlarm(participant, alarm);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeAlarm(EventParticipant participant, Alarm alarm) {

    }

    @Override
    public void changeAlarm(EventParticipant participant, Alarm alarm) {

    }
}
