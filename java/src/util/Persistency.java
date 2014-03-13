package util;

import Models.Alarm;
import Models.Event;
import Models.EventParticipant;
import Models.Group;
import interfaces.PersistencyInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Persistency extends PersistencyGetMethods implements PersistencyInterface {

    public Persistency() {
        query = new DBQuery();
    }

    public void closeConnection(){
        if (query != null)
    	    query.close();
    	System.out.println("closed.");
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
    public boolean addEvent(Event event, ArrayList<Integer> participantIDs) {
        boolean success = false;
        try {
            success = query.addEvent(event, participantIDs);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return success;
    }

    @Override
    public boolean removeEvent(Event event) {
    	boolean success = false;
        try {
            query.removeEvent(event);
            success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return success;
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
    public boolean addEventParticipant(int eventID, int participantID) {
        boolean success = false;

        try {
            success = query.addEventParticipant(eventID, participantID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return success;
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
        try {
            query.changeEventParticipant(participant);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean addAlarm(EventParticipant participant, Alarm alarm) {
        boolean success = false;

        try {
            success = query.addAlarm(participant, alarm);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return success;
    }
    
    public void test(){
    	System.out.println("test");
    }

    @Override
    public boolean removeAlarm(Alarm alarm) {
    	boolean success = false;
        try {
            success = query.removeAlarm(alarm);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return success;
    }

    @Override
    public boolean changeAlarm(Alarm alarm) {
    	boolean success = false;
        try {
            success = query.changeAlarm(alarm);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return success;
    }
}
