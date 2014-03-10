package util;

import Models.Group;
import Models.Room;
import Models.User;

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
}
