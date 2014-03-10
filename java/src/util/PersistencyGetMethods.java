package util;

import Models.*;
import interfaces.PersistencyInterface;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public abstract class PersistencyGetMethods implements PersistencyInterface{
    protected DBQuery query;

    @Override
    public ArrayList<Alarm> getAllAlarms() {
        ArrayList<Alarm> alarms = new ArrayList<Alarm>();
        try {
            alarms = query.getAllAlarms();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alarms;
    }

    @Override
    public ArrayList<Event> getAllEvents() {
        return null;
    }

    @Override
    public ArrayList<EventParticipant> getAllEventParticipants() {
        ArrayList<EventParticipant> participants = new ArrayList<EventParticipant>();

        try {
            participants = query.getAllEventParicipants();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return participants;
    }

    @Override
    public ArrayList<Group> getAllGroups() {
        ArrayList<Group> groups = new ArrayList<Group>();

        try {
            groups = query.getAllGroups();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return groups;
    }

    @Override
    public ArrayList<Room> getAllRooms() {
        ArrayList<Room> rooms = null;
        try {
            rooms = query.getAllRooms();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rooms;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = null;
        try {
            users = query.getAllUsers();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
}
