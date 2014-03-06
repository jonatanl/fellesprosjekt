package Models;

import java.util.ArrayList;

// Models a group of users. 
public class Group {
	private int groupId;
	private ArrayList<User> members;
	
	public Group(){
		members = new ArrayList<User>();
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	public void addMember(User member){
		this.members.add(member);
	}
	
	public void removeMember(User member){
		this.members.remove(member);
	}
	
	public ArrayList<User> getMembers(){
		return this.members;
	}
}
