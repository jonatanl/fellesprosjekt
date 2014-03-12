package util;

import Models.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class DBQueryGetMethods extends DBConnection{
    public ArrayList<User> getAllUsers() throws SQLException {
        String query = "SELECT * FROM users";
        ResultSet result = getResults(query);
        ArrayList<User> users = new ArrayList<User>();

        User user;
        while (result.next()){
            user = new User();

            user.setUserId(result.getInt("userID"));
            user.setUsername(result.getString("username"));
            user.setPassword(result.getString("password"));

            users.add(user);
        }
        return users;
    }

    public ArrayList<Group> getAllGroups() throws SQLException {
        String query = "SELECT * FROM calendar.group";
        ResultSet result = getResults(query);

        ArrayList<Group> groups = new ArrayList<Group>();
        Group group;
        while (result.next()){
            group = new Group();

            group.setGroupId(result.getInt("groupID"));
            group.setName(result.getString("name"));
            groups.add(group);
        }
        for (Group g : groups){
            g.setMembers(getAllUsersInGroup(g.getGroupId()));
        }

        return groups;
    }

    private ArrayList<Integer> getAllUsersInGroup(int groupId) throws SQLException{
        String query = "SELECT userID FROM memberOf WHERE groupID=" + groupId;
        ResultSet result = getResults(query);

        ArrayList<Integer> users = new ArrayList<Integer>();
        int userId;
        while (result.next()){
            userId = result.getInt("userID");
            users.add(userId);
        }

        return users;
    }

    public ArrayList<Room> getAllRooms() throws SQLException{
        String query = "SELECT * FROM room";
        ResultSet result = getResults(query);

        ArrayList<Room> rooms = new ArrayList<Room>();
        Room room;
        while (result.next()){
            room = new Room();

            room.setId(result.getInt("roomID"));
            room.setAdress(result.getString("adress"));
            room.setCapacity(result.getInt("capacity"));

            rooms.add(room);
        }


        return rooms;
    }

    public ArrayList<Alarm> getAllAlarms() throws SQLException {
        String query = "SELECT * FROM alarm";
        ResultSet result = getResults(query);

        ArrayList<Alarm> alarms = new ArrayList<Alarm>();
        Alarm alarm;

        while (result.next()){
            alarm = new Alarm();

            alarm.setAlarmID(result.getInt("alarmID"));
            alarm.setTime(result.getDate("time").toString());

            alarms.add(alarm);
        }
        return alarms;
    }

    public ArrayList<EventParticipant> getAllEventParicipants() throws SQLException {
        String query = "SELECT * FROM eventParticipant";
        ResultSet result = getResults(query);

        ArrayList<EventParticipant> participants = new ArrayList<EventParticipant>();
        EventParticipant participant;

        while (result.next()){
            participant = new EventParticipant();

            participant.setAlarmId(result.getInt("alarmID"));
            participant.setEventId(result.getInt("eventID"));
            participant.setUserId(result.getInt("userID"));
            participant.setDeleted(result.getBoolean("isDeleted"));
            participant.setPendingChange(result.getBoolean("pendingChange"));
            participant.setResponse(result.getString("response"));

            participants.add(participant);
        }
        return participants;
    }

    public ArrayList<Event> getAllEvents() throws SQLException {
        String query = "SELECT * FROM event";
        ResultSet result = getResults(query);

        ArrayList<Event> events = new ArrayList<Event>();
        Event event;

        while (result.next()){
            event = new Event();

            event.setEventId(result.getInt("eventID"));
            event.setEventName(result.getString("eventName"));
            event.setStartTime(result.getString("startTime"));
            event.setEndTime(result.getString("endTime"));
            event.setDescription(result.getString("description"));
            event.setLocation(result.getString("location"));
            event.setRoomId(result.getInt("roomID"));
            event.setOwnerId(result.getInt("ownerID"));

            events.add(event);
        }

        return events;
    }
}
