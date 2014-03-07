package client;

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

public class EditAlarm implements EventHandler<ActionEvent> {

	//private TextField timeBeforeField;
	private CheckBox alarmCheckBox;
	private TextField timeBeforeField;
	private Button saveButton;
	private Button cancelButton;
	
    private Alarm model;
    private String eventTime;
    
    public EditAlarm(Alarm alarm, String eventTime){
    	this.eventTime = eventTime;
    	if (alarm == null){
    		this.model = new Alarm();
    		this.model.setTime(eventTime);
    	}
    	else{
    		this.model = alarm;
    	}
    }
    

    
    public void start(Stage stage) throws Exception {
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
        
        timeBeforeField = new TextField(util.Time.subtractTimes(this.eventTime, this.model.getTime()));
        grid.add(timeBeforeField, 1, 2);
        
        saveButton = new Button("Lagre");
        saveButton.setOnAction(this);
        grid.add(saveButton, 0, 3);
        
        cancelButton = new Button("Avbryt");
        cancelButton.setOnAction(this);
        grid.add(cancelButton, 1, 3);
        
        Scene scene = new Scene(grid, 300, 275);
        stage.setScene(scene);
        stage.show();
    }

    
    
    
    
    @Override
    public void handle(ActionEvent actionEvent) {
    	if (actionEvent.getSource() == saveButton) {
			Stage newStage = new Stage();
			VBox comp = new VBox();
			TextField nameField = new TextField("Name");
			TextField phoneNumber = new TextField("Phone Number");
			comp.getChildren().add(nameField);
			comp.getChildren().add(phoneNumber);

			Scene stageScene = new Scene(comp, 300, 300);
			newStage.setScene(stageScene);
			newStage.show();		
			
		}
    	else if (actionEvent.getSource() == cancelButton) {
    		
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
