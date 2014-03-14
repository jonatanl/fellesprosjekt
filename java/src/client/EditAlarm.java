package client;

import interfaces.PersistencyInterface;

import java.util.Date;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.glass.ui.Pixels.Format;

import util.DateHelper;
import util.Persistency;
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
import javafx.scene.paint.Color;
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
	private TextField alarmTimeField;
	private Button saveButton;
	private Button cancelButton;
	private Text errorMessage;
	
	private Stage thisStage;
    private Stage parentStage;
    private Alarm alarm;
    private Calendar calendar;
    private int userId, eventId;
    private boolean alarmExistedBefore;
    private PersistencyInterface persistency;
    
    public EditAlarm(PersistencyInterface p, Calendar calendar, Stage stage, Alarm alarm, int eventId, int userId) {
    	try {
    		this.persistency = p;
    		this.userId = userId;
        	alarmExistedBefore = (alarm != null);
        	this.alarm = alarm;
        	this.eventId = eventId;
        	this.parentStage = stage;
        	this.calendar = calendar;
        	createStage();
		} catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isValid(){
    	if (alarmTimeField.getText().length() == 17 
    			&& alarmTimeField.getText().substring(0, 2).matches("[0-9]+") 
    			&& alarmTimeField.getText().substring(3, 5).matches("[0-9]+") 
    			&& alarmTimeField.getText().substring(6, 10).matches("[0-9]+")
    			&& alarmTimeField.getText().substring(12, 14).matches("[0-9]+")
    			&& alarmTimeField.getText().substring(15, 17).matches("[0-9]+")) {
    		return true;
    	}
    	alarmTimeField.setPromptText("dd-MM-yyyy, HH:mm");
    	return false;
    }
    
    public void handle(ActionEvent actionEvent) {
    	if (actionEvent.getSource() == saveButton) {
    		if(isValid()){
    			System.out.println("Send info to database");
    			if (alarmExistedBefore){
    				if (!alarmCheckBox.isSelected()){
    					// Remove the alarm
    					if (persistency.removeAlarm(alarm)){
    						calendar.setAlarm(null, userId, eventId);
    					}
    				}
    				else{
    					// Update the alarm
    					alarm.setTime(DateHelper.convertToDate(alarmTimeField.getText(), DateHelper.FORMAT_GUI));
    					if (persistency.changeAlarm(alarm)){
    						calendar.setAlarm(alarm, userId, eventId);
    					}
    				}
    			}
    			else{
    				if (alarmCheckBox.isSelected()){
    					// Create new alarm.
    					alarm = new Alarm();
    					alarm.setTime(DateHelper.convertToDate(alarmTimeField.getText(), DateHelper.FORMAT_GUI));
    					
    					EventParticipant ep = calendar.findEventParticipant(userId, eventId);
    					
    					if (persistency.addAlarm(calendar.findEventParticipant(userId, eventId), alarm)){
    						calendar.setAlarm(alarm, userId, eventId);
    					}
    				}
    			}
    			
    			thisStage.close();
    		}
    		else{
    			System.out.println("Invalid input");
    			errorMessage.setText("Feil. Bruk formatet dd:MM:yyyy, HH:mm");
    		}
			
		}
    	else if (actionEvent.getSource() == cancelButton) {
    		thisStage.close();
    	}
    	
    	if(alarmCheckBox.isSelected()){
			alarmTimeField.setEditable(true);
		}
		else{
			alarmTimeField.setEditable(false);
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
       
        alarmTimeField = new TextField();
        alarmTimeField.setEditable(false);
        alarmTimeField.setPromptText("dd:MM:yyyy, HH:mm");
        alarmTimeField.setText(DateHelper.convertToString(calendar.getSelectedEvent().getStartTime(), DateHelper.FORMAT_GUI));
        grid.add(alarmTimeField, 1, 2);
        
        errorMessage = new Text();
        errorMessage.setFill(Color.FIREBRICK);
        grid.add(errorMessage, 0, 3);
        
        saveButton = new Button("Save");
        saveButton.setOnAction(this);
        grid.add(saveButton, 0, 4);
        
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(this);
        grid.add(cancelButton, 1, 4);
        
        Scene scene = new Scene(grid, 400, 200);
        thisStage = new Stage();
        thisStage.setScene(scene);
        thisStage.show();
		
	}
}
