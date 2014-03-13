package client;

import interfaces.PersistencyInterface;

import java.util.Date;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.glass.ui.Pixels.Format;

import util.DateHelper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Models.Alarm;
import Models.Event;
import Models.EventParticipant;
import util.DateHelper;

public class EditAlarm implements EventHandler<ActionEvent> {

	private CheckBox alarmCheckBox;
	private TextField timeBeforeField;
	private Button saveButton;
	private Button cancelButton;
	
	private Stage thisStage;
    private Stage parentStage;
    private Alarm alarm;
    private int userId, eventId;
    private PersistencyInterface persistency;
    
    public EditAlarm(Stage stage, Alarm alarm, int eventId, int userId) {
    	try {
    		this.userId = userId;
        	this.alarm = alarm;
        	this.eventId = eventId;
        	this.parentStage = stage;
        	createStage();
		} catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isValid(){
    	if (timeBeforeField.getText().length() != 17 
    			&& timeBeforeField.getText().substring(0, 2).matches("[0-9]+") 
    			&& timeBeforeField.getText().substring(3, 5).matches("[0-9]+") 
    			&& timeBeforeField.getText().substring(6, 10).matches("[0-9]+")
    			&& timeBeforeField.getText().substring(12, 14).matches("[0-9]+")
    			&& timeBeforeField.getText().substring(15, 17).matches("[0-9]+")) {
    		return true;
    	}
    	timeBeforeField.setPromptText("dd-MM-yyyy, HH:mm");
    	return false;
    }
    
    public void handle(ActionEvent actionEvent) {
    	if (actionEvent.getSource() == saveButton) {
    		if(isValid()){
    			System.out.println("Send info to database");
    			
    			alarm.setAlarmID(eventId);
    			alarm.setTime(DateHelper.convertToDate(timeBeforeField.getText(), DateHelper.FORMAT_GUI));
    			
    			//persistency.addAlarm(participant, alarm);;
    			
    			thisStage.close();
    		}
    		else{
    			System.out.println("Invalid input");
    			timeBeforeField.clear();
    			timeBeforeField.setPromptText("Set an integer");
    		}
			
		}
    	else if (actionEvent.getSource() == cancelButton) {
    		thisStage.close();
    	}
    	
    	if(alarmCheckBox.isSelected()){
			timeBeforeField.setEditable(true);
		}
		else{
			timeBeforeField.setEditable(false);
		}
    }
	
    public void createStage() throws Exception {
		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text title = new Text("Set alarm");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(title, 0, 0, 2, 1);
        
        alarmCheckBox = new CheckBox("Alarm");
        alarmCheckBox.setOnAction(this);
        grid.add(alarmCheckBox, 0, 1);
        
        grid.add(new Text("Alarm time:"), 0, 2);
       
        timeBeforeField = new TextField();
        timeBeforeField.setEditable(false);
        timeBeforeField.setPromptText("dd:MM:yyyy, HH:mm");
        grid.add(timeBeforeField, 1, 2);
        
        saveButton = new Button("Save");
        saveButton.setOnAction(this);
        grid.add(saveButton, 0, 3);
        
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(this);
        grid.add(cancelButton, 1, 3);
        
        Scene scene = new Scene(grid, 400, 200);
        thisStage = new Stage();
        thisStage.setScene(scene);
        thisStage.show();
		
	}
}
