package client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import util.Time;

import Models.Event;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
    	dateField.setText(ddmmyyyy);
    	
    	endTime.setText((Time.addTimes(mh, "01:00")));
    	
    }


    @Override
    public void handle(ActionEvent actionEvent) {
    	
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
    
    public boolean validInput() {
    	// Check that the title field has some text
    	if (titleField.getText().length() <= 1) {
    		if (titleField.getText().contains(" ") || titleField.getText().isEmpty()) {
    			titleField.setPromptText("Fill in , you bastard!");
    			return false;
    		}
    	}
    	if (dateField.getText().length() != 8 || dateField.getText().contains("[a-zA-Z]+")) {
    		dateField.setPromptText("Invalid dateformat: hh:ss");
    		return false;
    	}
    	return true;
    }



}
