package client;

import interfaces.PersistencyInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import sun.font.LayoutPathImpl.EndType;
import util.Time;
import Models.Event;
import Models.Group;
import Models.Room;
import Models.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddEvent implements EventHandler<ActionEvent> {
    private TextField titleField;
    private TextField dateField;
    private TextField startTime;
    private TextField endTime;
    private TextField description;
    private TextField location;
    
    private ComboBox<Room> roomList;
    
    private ListView<Object> allPersonListView, chosenPersonListView;
    private Button addPerson, removePerson, addEvent;

    private Event eventModel;
    
    // The Observable lists contain both users and groups. 
    private ObservableList<Object> allPersonsObservableList;
    private ObservableList<Object> selectedPersonsObservableList;
    private ArrayList<String> persons;
    private Stage thisStage;
    private Stage parentStage;
    private PersistencyInterface persistency;
    
    private ObservableList<Room> allRoomsObservableList;
    
    private ArrayList<Room> rooms;
    private ArrayList<User> users;
    private ArrayList<Group> groups;
    private int ownerId;
    	
    
    public AddEvent(Stage stage, PersistencyInterface persistency, int ownerId, ArrayList<Room> rooms, ArrayList<User> users, ArrayList<Group> groups) {
    	try {
			createStage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	this.parentStage = stage;
    	this.persistency = persistency;
    	this.ownerId = ownerId;
    	this.rooms = rooms;
    	this.users = users;
    	this.groups = groups;
    }

  
    public void createStage() throws Exception {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text title = new Text("Event");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(title, 0, 0, 2, 1);

        grid.add(createLabels(),0,1);
        grid.add(createFields(),1,1);
        grid.add(createListViewBox(),2,1);

        addEvent = new Button("Add event");
        grid.add(addEvent, 1,2);

        Scene scene = new Scene(grid, 500, 475);
        thisStage = new Stage();
        thisStage.setScene(scene);
        
        thisStage.initModality(Modality.APPLICATION_MODAL);
		thisStage.initOwner(parentStage);
		
        thisStage.show();

        addEvent.setOnAction(this);
        eventModel = new Event();
        
        setHints();
    }


    private VBox createLabels(){
        VBox box = new VBox();
        box.setSpacing(15);
        box.getChildren().addAll(new Label("Title"), new Label("Date"), new Label("Start Time"), new Label("End Time"), new Label("Description"), new Label("Location"), new Label("Room"));
        return box;
    }

    private VBox createFields() {
    	
    	VBox box = new VBox();
    	box.setSpacing(10);
    	
        titleField = new TextField();
        dateField = new TextField();
        startTime = new TextField();
        endTime = new TextField();
        description = new TextField();
        location = new TextField();
        roomList = new ComboBox<>();
        updateRoomComboBox();
        roomList.setMinWidth(200);
        
        box.getChildren().addAll(titleField,dateField,startTime,endTime,description,location, roomList);
        
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
    
    public void setHints() {
    	Date date = new Date();
    	
    	
    	
    	String mh = (date.getHours() + ":" + date.getMinutes());
    	if (date.getHours() < 10) {
    		mh = "0" + mh;
    	}
    	if (date.getMinutes() < 10) {
    		mh = mh.substring(0, 3) + "0" + mh.charAt(mh.length()-1);
    	}
    	startTime.setText(mh);
    	
    	String ddmmyyyy = date.getDate() + "-" + (date.getMonth()+1) + "-" + (date.getYear()+1900);
    	if (date.getDate() < 10) {
    		ddmmyyyy = "0" + ddmmyyyy;
    	}
    	if (date.getMonth()+1 < 10) {
    		ddmmyyyy = ddmmyyyy.substring(0, 3) + "0" + ddmmyyyy.substring(3) ;
    	}
    	dateField.setText(ddmmyyyy);
    	
    	endTime.setText((Time.addTimes(mh, "01:00")));
    	
    }


    @Override
    public void handle(ActionEvent actionEvent) {
    	if (actionEvent.getSource() == addEvent && validInput()) {
    		eventModel.setEventName(titleField.getText());
    		eventModel.setDate(dateField.getText());
    		eventModel.setStartTime(startTime.getText());
    		eventModel.setEndTime(endTime.getText());
    		eventModel.setDescription(description.getText());
    		eventModel.setLocation(location.getText());
    		eventModel.setRoomId(roomList.getValue().getId());
    		eventModel.setOwnerId(ownerId);
    		
    		
    		
    		//persistency.addEvent(eventModel, getSelectedParticipantIds());
    		
    		thisStage.close();    		

    	}
    	else if(actionEvent.getSource() == addPerson){
    		int id = allPersonListView.getFocusModel().getFocusedIndex();
    		selectedPersonsObservableList.add(allPersonsObservableList.get(id));
    		allPersonsObservableList.remove(id);
    		updateRoomComboBox();
    	}
    	
    	else if(actionEvent.getSource() == removePerson){        	
    		int id = chosenPersonListView.getFocusModel().getFocusedIndex();
    		allPersonsObservableList.add(selectedPersonsObservableList.get(id));
    		selectedPersonsObservableList.remove(id);
    		updateRoomComboBox();
    	}
    }
    
    private ArrayList<Integer> getSelectedParticipantIds(){
    	ArrayList<Integer> result = new ArrayList<Integer>();
    	for (Object o: selectedPersonsObservableList){
    		if (o instanceof User){
    			result.add(((User)o).getUserId());
    		}
    		else if (o instanceof Group){
    			for (User u:((Group)o).getMembers()){
    				if (!result.contains(u)){
    					result.add(u.getUserId());
    				}
    			}
    		}
    	}
    	return result;
    }
    
    public boolean validInput() {
    	boolean hasFailed = false;
    	// Check that the title field has some text
    	if (titleField.getText().length() <= 1) {
    		if (titleField.getText().contains(" ") || titleField.getText().isEmpty()) {
    			titleField.setPromptText("Need title");
    			hasFailed = true;;
    		}
    	}
    	// Checks that the date is in the correct format
    	if (dateField.getText().length() != 10 || !dateField.getText().substring(0, 2).matches("[0-9]+") || !dateField.getText().substring(3, 5).matches("[0-9]+") || !dateField.getText().substring(6).matches("[0-9]+")) {
    		dateField.setText("");
    		dateField.setPromptText("dd:mm:yyyy");
    		hasFailed = true;
    	}
    	// Check that the start and end time is in the correct format
    	if (startTime.getText().length() != 5 || !startTime.getText().substring(0, 2).matches("[0-9]+") || !startTime.getText().substring(3, 5).matches("[0-9]+")) {
    		startTime.setText("");
    		startTime.setPromptText("hh:ss");
    		hasFailed = true;
    	}
    	if (endTime.getText().length() != 5 || !endTime.getText().substring(0, 2).matches("[0-9]+") || !endTime.getText().substring(3, 5).matches("[0-9]+")) {
    		endTime.setText("");
    		endTime.setPromptText("hh:ss");
    		hasFailed = true;
    	}
    	return !hasFailed;
    }
    
    
        
    
    public VBox createListViewBox(){
    	VBox rightBox = new VBox(5);
        Label participants = new Label ("Participants");
        
        ArrayList<Object> usersAndGroups = new ArrayList<Object>(users);
        usersAndGroups.addAll(groups);
        
        allPersonsObservableList = FXCollections.observableArrayList(usersAndGroups);
        selectedPersonsObservableList= FXCollections.observableArrayList();

    	allPersonListView = new ListView<Object>();
    	allPersonListView.setPrefWidth(175);
    	allPersonListView.setPrefHeight(130);
    	allPersonListView.setItems(allPersonsObservableList);
    	
    	addPerson = new Button("Add");
    	addPerson.setOnAction(this);

    	chosenPersonListView = new ListView<Object>();
    	chosenPersonListView.setPrefWidth(175);
    	chosenPersonListView.setPrefHeight(130);
    	chosenPersonListView.setItems(selectedPersonsObservableList);
    	
    	removePerson = new Button("Remove");
    	removePerson.setOnAction(this);
    	
    	rightBox.getChildren().addAll(participants,allPersonListView,addPerson,chosenPersonListView,removePerson);
    	
    	return rightBox;
    }


}
