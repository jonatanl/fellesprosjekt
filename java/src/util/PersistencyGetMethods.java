package util;

import Models.*;
import interfaces.PersistencyInterface;

import java.sql.SQLException;
import java.util.ArrayList;

public abstract class PersistencyGetMethods implements PersistencyInterface{
    protected DBQuery query;

    @Override
    public ArrayList<Alarm> getAllAlarms() {
        return null;
    }

    @Override
    public ArrayList<Event> getAllEvents() {
        return null;
    }

    @Override
    public ArrayList<EventParticipant> getAllEventParticipants() {
        return null;
    }

    @Override
    public ArrayList<Group> getAllGroups() {
        ArrayList<Group> groups = new ArrayList<Group>();

        try {
            groups = query.getAllGroups();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groups;
    }

    @Override
    public ArrayList<Room> getAllRooms() {
        return null;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = null;
        try {
            users = query.getAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
