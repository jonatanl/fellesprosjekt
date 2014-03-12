package Models;

import java.util.ArrayList;

// Models a group of users. 
public class Group {
	private int groupId;
	private ArrayList<Integer> members;
	private ArrayList<Integer> subGroups;

	private String name;
	
	public Group(){
		members = new ArrayList<Integer>();
	}
	
    public ArrayList<Integer> getSubGroups() {
		return subGroups;
	}

	public void setSubGroups(ArrayList<Integer> subGroups) {
		this.subGroups = subGroups;
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
    
    @Override
    public String toString(){
    	return getName() + " (" + members.size() + " members)";
    }
}
