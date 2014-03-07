package client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import sun.font.LayoutPathImpl.EndType;
import util.Time;

import Models.Event;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    
    private ListView<String> allPersonList, chosenPersonList;
    private Button addPerson, removePerson;

    private Event eventModel;
    
    private Stage thisStage;
    private Stage parentStage;
    
    public AddEvent(Stage stage) {
    	try {
			createStage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	this.parentStage = stage;
    	
    	
    	
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

        createFields(grid);
        createLabels(grid);
        grid.add(getListViewBox(),2,0);

        Button button = new Button("Add event");
        grid.add(button, 1, 7);

        Scene scene = new Scene(grid, 300, 275);
        thisStage = new Stage();
        thisStage.setScene(scene);
        
        thisStage.initModality(Modality.APPLICATION_MODAL);
		thisStage.initOwner(parentStage);
		
        thisStage.show();

        button.setOnAction(this);
        eventModel = new Event();
        
        setHints();
    }

    private void createLabels(GridPane grid){
        grid.add(new Label("Title"), 0, 1);
        grid.add(new Label("Date"), 0, 2);
        grid.add(new Label("Start Time"), 0, 3);
        grid.add(new Label("End Time"), 0, 4);
        grid.add(new Label("Description"), 0, 5);
        grid.add(new Label("Location"), 0, 6);
    }

    private void createFields(GridPane grid) {
        titleField = new TextField();
        dateField = new TextField();
        startTime = new TextField();
        endTime = new TextField();
        description = new TextField();
        location = new TextField();


        grid.add(titleField, 1, 1);
        grid.add(dateField, 1, 2);
        grid.add(startTime, 1, 3);
        grid.add(endTime, 1, 4);
        grid.add(description, 1, 5);
        grid.add(location, 1, 6);
        
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
        //model.setTitle(titleField.getText());
        //model.setStartTime(startTime.getText());
        //model.setEndTime(endTime.getText());
        eventModel.setDescription(description.getText());
        eventModel.setLocation(location.getText());
        System.out.println(eventModel);
        if (validInput()) {
        	eventModel.setEventName(titleField.getText());
        	eventModel.setDate(dateField.getText());
        	eventModel.setStartTime(startTime.getText());
        	eventModel.setEndTime(endTime.getText());
        	eventModel.setDescription(description.getText());
        	eventModel.setLocation(location.getText());
        	System.out.println(eventModel);
        	
        	thisStage.close();    		
        }
        
    }
    
    public VBox getListViewBox(){
    	VBox rightBox = new VBox();
        Label participants = new Label ("Deltagere");

    	allPersonList = new ListView<String>();
    	allPersonList.setPrefWidth(175);
    	allPersonList.setPrefHeight(130);
    	
    	addPerson = new Button("Legg til");
    	addPerson.setOnAction(this);

    	chosenPersonList = new ListView<String>();
    	chosenPersonList.setPrefWidth(175);
    	chosenPersonList.setPrefHeight(130);
    	
    	removePerson = new Button("Fjern");
    	removePerson.setOnAction(this);
    	
    	rightBox.getChildren().addAll(participants,allPersonList,addPerson,chosenPersonList,removePerson);
    	
    	return rightBox;
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



}
