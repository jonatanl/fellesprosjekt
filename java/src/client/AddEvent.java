package client;

import Models.Event;
import javafx.application.Application;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;

public class AddEvent extends Application implements EventHandler<ActionEvent> {
    private TextField titleField;
    private TextField startTime;
    private TextField endTime;
    private TextField description;
    private TextField location;
    
    private ListView<String> allPersonList, chosenPersonList;
    private Button addPerson, removePerson;

    private Event model;

    @Override
    public void start(Stage stage) throws Exception {
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
        grid.add(button, 1, 6);

        Scene scene = new Scene(grid, 300, 275);
        stage.setScene(scene);
        stage.show();

        button.setOnAction(this);
        model = new Event();
    }

    private void createLabels(GridPane grid){
        grid.add(new Label("Title"), 0, 1);
        grid.add(new Label("Start Time"), 0, 2);
        grid.add(new Label("End Time"), 0, 3);
        grid.add(new Label("Description"), 0, 4);
        grid.add(new Label("Location"), 0, 5);
    }

    private void createFields(GridPane grid) {
        titleField = new TextField();
        startTime = new TextField();
        endTime = new TextField();
        description = new TextField();
        location = new TextField();

        grid.add(titleField, 1, 1);
        grid.add(startTime, 1, 2);
        grid.add(endTime, 1, 3);
        grid.add(description, 1, 4);
        grid.add(location, 1, 5);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        //model.setTitle(titleField.getText());
        //model.setStartTime(startTime.getText());
        //model.setEndTime(endTime.getText());
        model.setDescription(description.getText());
        model.setLocation(location.getText());
        System.out.println(model);
        
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
    
}
