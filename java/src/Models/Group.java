package Models;

import java.util.ArrayList;

// Models a group of users. 
public class Group {
	private int groupId;
	private ArrayList<Integer> members;
    private String name;
	
	public Group(){
		members = new ArrayList<Integer>();
	}

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public ArrayList<Integer> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Integer> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
