package client;

import java.util.Date;

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

public class EditAlarm implements EventHandler<ActionEvent> {

	//private TextField timeBeforeField;
	private CheckBox alarmCheckBox;
	private TextField timeBeforeField;
	private Button saveButton;
	private Button cancelButton;
	
	private Stage thisStage;
    private Stage parentStage;
    
    public EditAlarm(Event event, Stage stage) {
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
        
        Text title = new Text("Sett alarm");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(title, 0, 0, 2, 1);
        
        alarmCheckBox = new CheckBox("Alarm");
        grid.add(alarmCheckBox, 0, 1);
        
        grid.add(new Text("Tid før:"), 0, 2);
       
        timeBeforeField = new TextField();
        grid.add(timeBeforeField, 1, 2);
        
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
    
    public boolean isValid(){
    	if(timeBeforeField.getText().matches("[0-9]+")){
    		return true;
    	}
    	return false;
    }

    
    
    
    
    @Override
    public void handle(ActionEvent actionEvent) {
    	if (actionEvent.getSource() == saveButton) {
    		if(isValid()){
    			System.out.println("Send info to database");
    			thisStage.close();
    		}
    		else{
    			System.out.println("Invalid input");
    		}
			
		}
    	else if (actionEvent.getSource() == cancelButton) {
    		thisStage.close();
    	}
    	
    	
        /*
    	model.setTitle(titleField.getText());
        model.setStartTime(startTime.getText());
        model.setEndTime(endTime.getText());
        model.setDescription(description.getText());
        model.setLocation(location.getText());
        System.out.println(model);
        */
    }
    
}
