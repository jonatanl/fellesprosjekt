package client;

import Models.*;
import client.calendar.CalendarView;
import interfaces.PersistencyInterface;
import javafx.application.Application;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public abstract class CalendarLists extends Application{
    protected ArrayList<Event> events;
    protected ArrayList<User> users;
    protected ArrayList<Group> groups;
    protected ArrayList<Room> rooms;
    protected ArrayList<EventParticipant> eventParticipants;
    protected ArrayList<Alarm> alarms;

    protected ArrayList<User> selectedUsers;

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Alarm> getAlarms(){
        return alarms;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<EventParticipant> getEventParticipants() {
        return eventParticipants;
    }

    public User findUser(int userID){
        for (User u: users){
            if (u.getUserId() == userID){
                return u;
            }
        }
        return null;
    }

    public Event findEvent(int eventId){
        for (Event e: events){
            if (e.getEventId() == eventId){
                return e;
            }
        }
        return null;
    }

    public Group findGroup(int groupId){
        for (Group g: groups){
            if (g.getGroupId() == groupId){
                return g;
            }
        }
        return null;
    }

    public Room findRoom(int roomId){
        for (Room r: rooms){
            if (r.getId() == roomId)
                return r;

        }
        return null;
    }

    public EventParticipant findEventParticipant(int userId, int eventId){
        for (EventParticipant ep: eventParticipants){
            if (ep.getEventId() == eventId && ep.getUserId() == userId){
                return ep;

            }
        }
        return null;
    }

    public Alarm findAlarm(int eventId, int userId){
        EventParticipant ep = findEventParticipant(userId, eventId);
        if (ep != null){
            if (ep.getAlarmId() == -1){
                return null;
            }
            return findAlarm(ep.getAlarmId());
        }
        return null;
    }

    public Alarm findAlarm(int alarmId){
        for (Alarm a: alarms){
            if (a.getAlarmID() == alarmId){
                return a;
            }
        }
        return null;
    }

}
