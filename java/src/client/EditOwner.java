package client;

import java.util.ArrayList;
import java.util.Collections;

import Models.Event;
import Models.EventParticipant;
import Models.Group;
import Models.Room;
import Models.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditOwner implements EventHandler<ActionEvent>{
	
	private Label l_title, l_date, l_start, l_stop, l_description, l_place, l_room, l_participation;
	private TextField t_title, t_date, t_start, t_stop, t_description, t_place;
	private ComboBox<Room> roomList;
	
	private ToggleGroup participationGroup;
	private Button confirm, cancel, addPerson, removePerson;
	private RadioButton going, notGoing;
	private ListView<Object> allPersonListView, chosenPersonListView;
	private Event eventModel;
	private Stage thisStage;
	private Stage parentStage;
	
	private ObservableList<Object> allPersonsObservableList;
    private ObservableList<Object> selectedPersonsObservableList;
    
    private ObservableList<Room> allRoomsObservableList;
    
    private ArrayList<Room> rooms;
    private ArrayList<User> users;
    private ArrayList<Group> groups;
    private ArrayList<EventParticipant> currentParticipants;
	
	public EditOwner(Event event, Stage parentStage, ArrayList<Room> rooms, ArrayList<User> users, ArrayList<Group> groups, ArrayList<EventParticipant> currentParticipants) {
		try {
			this.parentStage = parentStage;
			this.rooms = rooms;
			this.users = users;
			this.groups = groups;
			this.currentParticipants = currentParticipants;
			createStage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		setModel(event);
		this.parentStage = parentStage;

	}


	
	public void setModel(Event model) {
		this.eventModel = model;
		
		t_title.setText(eventModel.getEventName());
	
		//t_start.setText(eventModel.getStartTime());
		//t_stop.setText(eventModel.getEndTime());
		t_description.setText(eventModel.getDescription());
		t_place.setText(eventModel.getLocation());
//		t_room.setText(eventModel.getRoom().toString());
	}
	
	public void createStage() {
		GridPane grid = new GridPane();
		
		grid.add(getLabels(), 1, 1);
		grid.add(getFields(), 2, 1);
		grid.add(getListViewBox(), 3, 1);
		grid.add(getButtons(), 1, 2);
		
		grid.setHgap(10);
        grid.setVgap(10);
		
		Scene scene = new Scene(grid, 600, 300);
		thisStage = new Stage();
		thisStage.setTitle("Endre Hendelse");
		thisStage.setScene(scene);
		
		thisStage.initModality(Modality.APPLICATION_MODAL);
		thisStage.initOwner(parentStage);
		thisStage.show();

	}
	
	public VBox getLabels(){
		VBox box = new VBox();
		
		l_title = new Label("Title");
		l_date = new Label("Date");
		l_start = new Label("Start time");
		l_stop = new Label("End time");
		l_description = new Label("Description");
		l_place = new Label("Place");
		l_room = new Label("Room");
		
		box.getChildren().addAll(l_title,l_date,l_start,l_stop,l_description,l_place,l_room);
		box.setSpacing(10);
		box.setPadding(new Insets(10,10,10,10));
		
		return box;
	}
	
	public VBox getFields(){
		VBox box = new VBox();
		
		t_title = new TextField();
		t_date = new TextField();
		t_start = new TextField();
		t_stop = new TextField();
		t_description = new TextField();
		t_place = new TextField();
		roomList = new ComboBox<>();
		
		box.getChildren().addAll(t_title,t_date,t_start,t_stop,t_description,t_place,roomList);
		box.setPadding(new Insets(10,0,0,0));
		box.setSpacing(3);
		
		return box;
		
	}
	
	private void updateRoomComboBox(){
    	int nParticipants = getSelectedParticipantIds().size();
    	
    	Collections.sort(rooms);
    	
    	ArrayList<Room> sortedList = new ArrayList<Room>();
    	ArrayList<Room> goodRooms = new ArrayList<Room>();
    	ArrayList<Room> badRooms = new ArrayList<Room>();
    	
    	for (Room r:rooms){
    		if (r.getCapacity() >= nParticipants){
    			goodRooms.add(r);
    		}
    		else{
    			badRooms.add(r);
    		}
    	}
    	sortedList.addAll(goodRooms);
    	sortedList.addAll(badRooms);
    	
    	
    	ObservableList<Room> sortedObservableList = FXCollections.observableArrayList(sortedList);
    	
    	roomList.setItems(sortedObservableList);
    }
	
	private ArrayList<Integer> getSelectedParticipantIds(){
    	ArrayList<Integer> result = new ArrayList<Integer>();
    	for (Object o: selectedPersonsObservableList){
    		if (o instanceof User){
    			result.add(((User)o).getUserId());
    		}
    		else if (o instanceof Group){
    			for (Integer u:((Group)o).getMembers()){
    				if (!result.contains(u)){
    					result.add(u);
    				}
    			}
    		}
    	}
    	return result;
    }
	
	public HBox getButtons(){
		HBox box = new HBox();
		
		confirm = new Button("Save");
		confirm.setOnAction(this);
		cancel = new Button("Cancel");
		cancel.setOnAction(this);
		
		box.getChildren().addAll(confirm,cancel);
		
		return box;
	}
	
	public VBox getListViewBox(){
		participationGroup = new ToggleGroup();
		VBox rightBox = new VBox(5);
        Label participants = new Label ("Participants");
        
       
    	
    	addPerson = new Button("Add");
    	addPerson.setOnAction(this);

    	
    	
    	removePerson = new Button("Remove");
    	removePerson.setOnAction(this);
    	removePerson.setDisable(true);
    	
    	  	
    	going = new RadioButton("Going");
		going.setToggleGroup(participationGroup);
		notGoing = new RadioButton("Not going");
		notGoing.setToggleGroup(participationGroup);
    	
		populateLists();
		
    	rightBox.getChildren().addAll(participants,allPersonListView,addPerson,chosenPersonListView,removePerson);
    	rightBox.getChildren().addAll(going,notGoing);
    	
    	
    	updateRoomComboBox();
    	return rightBox;
    }
	
	private void populateLists(){
		ArrayList<Object> usersAndGroups = new ArrayList<Object>(users);
		usersAndGroups.addAll(groups);
		
		ArrayList<Object> participantUsers = new ArrayList<Object>();
		
		for (User u: users){
			for (EventParticipant ep: currentParticipants){
				if (u.getUserId() == ep.getUserId()){
					participantUsers.add(u);
					usersAndGroups.remove(u);
				}
			}
		}
		
		for (Group g: groups){
			for (int groupUserId: g.getMembers()){
				boolean isParticipant = false;
				for (Object u: participantUsers){
					if (groupUserId == ((User)u).getUserId()){
						isParticipant = true;
					}
				}
				if (!isParticipant){
					break;
				}
			}
			usersAndGroups.remove(g);
			participantUsers.add(g);
		}
		
		
		
        allPersonsObservableList = FXCollections.observableArrayList(usersAndGroups);
        selectedPersonsObservableList= FXCollections.observableArrayList(participantUsers);

    	allPersonListView = new ListView<Object>();
    	allPersonListView.setPrefWidth(175);
    	allPersonListView.setPrefHeight(130);
    	allPersonListView.setItems(allPersonsObservableList);
    	
    	chosenPersonListView = new ListView<Object>();
    	chosenPersonListView.setPrefWidth(175);
    	chosenPersonListView.setPrefHeight(130);
    	chosenPersonListView.setItems(selectedPersonsObservableList);
    	chosenPersonListView.focusedProperty().addListener(new ChangeListener<Boolean>() {
    		
    		@Override
    		public void changed(ObservableValue<? extends Boolean> arg0,
    				Boolean arg1, Boolean arg2) {
    			// Noe ble trykket på i listen...
    			
    		}
    		
    	});
	}
	
	public boolean isValid(){
		boolean hasFailed = false;
    	// Check that the title field has some text
    	if (t_title.getText().length() <= 1) {
    		if (t_title.getText().contains(" ") || t_title.getText().isEmpty()) {
    			t_title.setPromptText("Need title");
    			hasFailed = true;;
    		}
    	}
    	// Checks that the date is in the correct format
    	if (t_date.getText().length() != 10 || !t_date.getText().substring(0, 2).matches("[0-9]+") || !t_date.getText().substring(3, 5).matches("[0-9]+") || !t_date.getText().substring(6).matches("[0-9]+")) {
    		t_date.setText("");
    		t_date.setPromptText("dd:mm:yyyy");
    		hasFailed = true;
    	}
    	// Check that the start and end time is in the correct format
    	if (t_start.getText().length() != 5 || !t_start.getText().substring(0, 2).matches("[0-9]+") || !t_start.getText().substring(3, 5).matches("[0-9]+")) {
    		t_start.setText("");
    		t_start.setPromptText("hh:ss");
    		hasFailed = true;
    	}
    	if (t_stop.getText().length() != 5 || !t_stop.getText().substring(0, 2).matches("[0-9]+") || !t_stop.getText().substring(3, 5).matches("[0-9]+")) {
    		t_stop.setText("");
    		t_stop.setPromptText("hh:ss");
    		hasFailed = true;
    	}
    	return !hasFailed;
	}
	

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource() == cancel){
			thisStage.close();			
		}
		else if(event.getSource() == confirm && isValid()){
			System.out.println("Send update");
			thisStage.close();
		}
		else if(event.getSource() == addPerson){
			int id = allPersonListView.getFocusModel().getFocusedIndex();
    		System.out.println(id);
    		if (id == -1) {
    			id = 0;
    		}
    		selectedPersonsObservableList.add(allPersonsObservableList.get(id));
    		allPersonsObservableList.remove(id);
    		allPersonListView.getSelectionModel().select(0);
    		updateRoomComboBox();
    		
    		if (allPersonsObservableList.size() == 0) {
    			addPerson.setDisable(true);
    		}
    		removePerson.setDisable(false);      	
    	}
    	
    	else if(event.getSource() == removePerson){        	
    		int id = chosenPersonListView.getFocusModel().getFocusedIndex();
    		
    		if (id == -1) {
    			id = 0;
    		}
    		allPersonsObservableList.add(selectedPersonsObservableList.get(id));
    		selectedPersonsObservableList.remove(id);
    		chosenPersonListView.getSelectionModel().select(0);
    		updateRoomComboBox();
    		
    		if (selectedPersonsObservableList.size() == 0){
    			removePerson.setDisable(true);
    		}
    		addPerson.setDisable(false);        	
    	}
		
	}

}
