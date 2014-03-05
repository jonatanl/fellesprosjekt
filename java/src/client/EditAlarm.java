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
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Models.Alarm;

public class EditAlarm extends Application implements EventHandler<ActionEvent> {

	//private TextField timeBeforeField;
	private CheckBox alarmCheckBox;
	private ChoiceBox timeBeforeChoiceBox;
	private Button saveButton;
	private Button cancelButton;

    private Alarm alarm;

    @Override
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
        
        timeBeforeChoiceBox = new ChoiceBox<String>(FXCollections.observableArrayList("5 min", "10 min", "30 min", 
        		"1 t", "2 t", "6 t", "12 t", "24 t"));
        grid.add(timeBeforeChoiceBox, 1, 2);
        
        saveButton = new Button("Lagre");
        grid.add(saveButton, 0, 3);
        
        cancelButton = new Button("Avbryt");
        grid.add(cancelButton, 1, 3);
        
        //Button button = new Button("Add event");
        //grid.add(button, 1, 6);

        Scene scene = new Scene(grid, 300, 275);
        stage.setScene(scene);
        stage.show();

        //button.setOnAction(this);
        //model = new Event();
    }

    private void createLabels(GridPane grid){
        grid.add(new Label("Sett alarm"), 0, 1);
        grid.add(new Label("Tid før"), 0, 2);
    }

    private void createFields(GridPane grid) {
        /*
    	//titleField = new TextField();
        startTime = new TextField();
        endTime = new TextField();
        description = new TextField();
        location = new TextField();

        //grid.add(titleField, 1, 1);
        grid.add(startTime, 1, 2);
        grid.add(endTime, 1, 3);
        grid.add(description, 1, 4);
        grid.add(location, 1, 5);
        */
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(ActionEvent actionEvent) {
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
