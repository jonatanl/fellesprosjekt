package client;

import java.util.ArrayList;
import java.util.Date;

import Models.Event;
import Models.EventParticipant;
import Models.Room;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

 
public class ShowMore implements EventHandler<ActionEvent> {
    
    private ObservableList<String> items;
    private String s_title,s_date,s_start,s_end,s_description,s_place,s_room,s_alert,s_timeBefore,s_myParticipation;
    
    private Stage parentStage;
    private Stage stage;
    private Event event;
    private ArrayList<EventParticipant> participants;
    private ArrayList<User> users;
    private ArrayList<Room> rooms;
    private ArrayList<String> finalParitcipants;
    private Calendar calendar;
    
    public ShowMore(Event event, Stage stage, ArrayList<EventParticipant> participants, ArrayList<User> users, ArrayList<Room> rooms){
    	try {
    		this.event = event;
    		this.parentStage = stage;
    		this.participants = participants;
    		this.users = users;
    		this.rooms = rooms;
    		setEvent(event.getEventName(), event.getStartTime(), event.getEndTime(), event.getDescription(), event.getLocation(), event.getRoomId());
    		createStage();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    
    public void setEvent(String title, Date start, Date end, String description, String place, int roomId){
    	this.s_title = title;
    	this.s_start = start.toString();
    	this.s_end = end.toString();
    	this.s_description = description;
    	this.s_place = place;
    	
    	finalParitcipants = new ArrayList<String>();
    	
    	for (int i = 0; i < users.size(); i++) {
    		for (int j = 0; j < participants.size()-1; j++) {
    			if(users.get(i).getUserId() == participants.get(j).getUserId()){    				
    				finalParitcipants.add(users.get(i).toString());
    			}
    		}
			
		}
    	
    	items =FXCollections.observableArrayList(finalParitcipants);
    	
    	for (int i = 0; i < rooms.size(); i++) {
    		if(rooms.get(i).getId()==roomId){
    			this.s_room = rooms.get(i).toString();
    		}
			
		}
    }

    
    public void createStage() {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        grid.add(getLeftBox(), 0, 0);
        grid.add(getDataBox(), 1, 0);
        grid.add(getListViewBox(), 2, 0);
        grid.add(getRadioBox(), 0, 1);
        grid.add(getOkButton(), 0, 2);
        
        Scene scene = new Scene(grid, 500, 300);
        
        stage = new Stage();
        stage.setTitle("Show more");
        stage.setScene(scene);
        stage.show();
    }
    
    
    public VBox getListViewBox(){
    	VBox rightBox = new VBox();
        Label participants = new Label ("Participants");

    	ListView<String> list = new ListView<String>();
    	list.setItems(items);
    	list.setPrefWidth(175);
    	list.setPrefHeight(130);
    	
    	rightBox.getChildren().addAll(participants,list);
    	
    	return rightBox;
    }
    
    public Button getOkButton(){
    	Button btnOk = new Button();
        btnOk.setText("Close");
        btnOk.setOnAction(this);
        return btnOk;
    }
    
    public VBox getLeftBox(){
    	Label title = new Label ("Title");
    	Label date = new Label ("Date");
        Label start = new Label ("Start");
        Label end = new Label ("End");
        Label description = new Label ("Description");
        Label place = new Label ("Place");
        Label room = new Label ("Room");
        Label alert = new Label ("Alert");
        Label timeBefore = new Label ("Time befor");
        Label myParticipation = new Label ("My participance");
        
        
        VBox leftBox = new VBox();
        leftBox.getChildren().addAll(title,date,start,end,description,place,room,alert,timeBefore,myParticipation);
        
        return leftBox;
    }
    
    public VBox getDataBox(){
    	Label eventTitle = new Label (s_title);
    	Label eventDate = new Label (s_date);
        Label eventStart = new Label (s_start);
        Label eventEnd = new Label (s_end);
        Label eventDescription = new Label (s_description);
        Label eventPlace = new Label (s_place);
        Label eventRoom = new Label (s_room);
        Label eventAlert = new Label ();
        Label eventTimeBefore = new Label ();
        Label eventMyParticipation = new Label ();
        
        VBox middleBox = new VBox();
        middleBox.getChildren().addAll(eventTitle,eventDate,eventStart,eventEnd,eventDescription,eventPlace,eventRoom,eventAlert,eventTimeBefore,eventMyParticipation);
        
        return middleBox;
    }
        
    public HBox getRadioBox(){
    	HBox radioBox = new HBox();
        radioBox.setPadding(new Insets(5, 5, 5, 5));
        
        ToggleGroup group = new ToggleGroup();
    	
    	RadioButton rb1 = new RadioButton("Going");
    	rb1.setToggleGroup(group);
    	rb1.setSelected(true);
    	rb1.setDisable(true);

    	RadioButton rb2 = new RadioButton("Not going");
    	rb2.setToggleGroup(group);
    	rb2.setDisable(true);
    	radioBox.getChildren().addAll(rb1,rb2);
    	
    	return radioBox;
    }

	@Override
	public void handle(ActionEvent event) {
		stage.close();
		
	}
    
}