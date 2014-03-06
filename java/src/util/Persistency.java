package util;

import Models.Alarm;
import Models.Event;
import Models.EventParticipant;
import interfaces.PersistencyInterface;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class Persistency implements PersistencyInterface {
    private DBQuery query;

    public Persistency() {
        query = new DBQuery();
        EventParticipant participant = new EventParticipant();
        participant.setEventId(1);
        participant.setUserId(2);
        Alarm alarm = new Alarm();
        alarm.setTime(new Date(1992,25,05).toString());
        addAlarm(participant, alarm);
        query.close();
    }

    public static void main(String[] args) {
        new Persistency();
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
