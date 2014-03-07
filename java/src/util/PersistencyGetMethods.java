package util;

import Models.*;
import interfaces.PersistencyInterface;

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
        return null;
    }

    @Override
    public ArrayList<Room> getAllRooms() {
        return null;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return null;
    }
}
