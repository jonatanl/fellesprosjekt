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

public class EditAlarm extends Application implements EventHandler<ActionEvent> {

	private CheckBox alarmCheckBox;
	private Label minutesLabel;
	private TextField timeBeforeField;
	private Button saveButton;
	private Button cancelButton;
	
	private Stage thisStage;
    private Stage parentStage;
    private Alarm alarm;
    private int userId, eventId;
    private PersistencyInterface persistency;
    
    public EditAlarm(Stage stage, Alarm alarm, int eventId, int userId) {
    	this.userId = userId;
    	this.alarm = alarm;
    	this.eventId = eventId;
    	this.parentStage = stage;
    	
    }
    public static void main(String[] args)  {
		launch(args);
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

    
    @Override
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
    	
    	if(alarmCheckBox.isSelected() == true){
			timeBeforeField.setEditable(true);
		}
		else{
			timeBeforeField.setEditable(false);
		}
    }
	@Override
	public void start(Stage arg0) throws Exception {
		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text title = new Text("Set alert");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(title, 0, 0, 2, 1);
        
        alarmCheckBox = new CheckBox("Alert");
        alarmCheckBox.setOnAction(this);
        grid.add(alarmCheckBox, 0, 1);
        
        grid.add(new Text("Time before:"), 0, 2);
       
        timeBeforeField = new TextField();
        timeBeforeField.setEditable(false);
        timeBeforeField.setPromptText("dd:MM:yyyy, HH:mm");
        grid.add(timeBeforeField, 1, 2);
        
        minutesLabel = new Label();
        minutesLabel.setText("Set time for alert");
        grid.add(minutesLabel, 2, 2);
        
        saveButton = new Button("Save");
        saveButton.setOnAction(this);
        grid.add(saveButton, 0, 3);
        
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(this);
        grid.add(cancelButton, 1, 3);
        
        Scene scene = new Scene(grid, 300, 275);
        thisStage = new Stage();
        thisStage.setScene(scene);
        thisStage.show();
		
	}
	
	
    
}
