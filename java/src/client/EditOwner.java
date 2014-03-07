package client;

import Models.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditOwner implements EventHandler<ActionEvent>{
	
	private Label l_title, l_date, l_start, l_stop, l_description, l_place, l_room, l_participation;
	private TextField t_title, t_date, t_start, t_stop, t_description, t_place, t_room;

	private ToggleGroup participationGroup;
	private Button confirm, cancel, addPerson, removePerson;
	private RadioButton going, notGoing;
	private ListView<String> allPersonList, chosenPersonList;
	private Event eventModel;
	private Stage thisStage;
	private Stage parentStage;

	private ObservableList<String> allPersons;
    private ObservableList<String> selectedPersons;
	
	public EditOwner(Event event, Stage parentStage) {
		try {
			createStage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		setModel(event);
		this.parentStage = parentStage;

	}


	
	public void setModel(Event model) {
		this.eventModel = model;
		
		t_title.setText(model.getEventName());
		t_date.setText(model.getDate());
		t_start.setText(model.getStartTime());
		t_stop.setText(model.getEndTime());
		t_description.setText(model.getDescription());
		t_place.setText(model.getLocation());
		//t_room.setText(model.getRoom().toString());
	}
	
	public void createStage() {
		GridPane grid = new GridPane();
		
		grid.add(getLabels(), 1, 1);
		grid.add(getFields(), 2, 1);
		grid.add(getListViewBox(), 3, 1);
		grid.add(getButtons(), 1, 2);
		
		grid.setHgap(10);
        grid.setVgap(10);
		
		Scene scene = new Scene(grid, 600, 300);
		thisStage = new Stage();
		thisStage.setTitle("Endre Hendelse");
		thisStage.setScene(scene);
		
		thisStage.initModality(Modality.APPLICATION_MODAL);
		thisStage.initOwner(parentStage);
		thisStage.show();

	}
	
	public VBox getLabels(){
		VBox box = new VBox();
		
		l_title = new Label("Title");
		l_date = new Label("Date");
		l_start = new Label("Start time");
		l_stop = new Label("End time");
		l_description = new Label("Description");
		l_place = new Label("Place");
		l_room = new Label("Room");
		
		box.getChildren().addAll(l_title,l_date,l_start,l_stop,l_description,l_place,l_room);
		box.setSpacing(10);
		box.setPadding(new Insets(10,10,10,10));
		
		return box;
	}
	
	public VBox getFields(){
		VBox box = new VBox();
		
		t_title = new TextField();
		t_date = new TextField();
		t_start = new TextField();
		t_stop = new TextField();
		t_description = new TextField();
		t_place = new TextField();
		t_room = new TextField();
		
		box.getChildren().addAll(t_title,t_date,t_start,t_stop,t_description,t_place,t_room);
		box.setPadding(new Insets(10,0,0,0));
		box.setSpacing(3);
		
		return box;
		
	}
	
	public HBox getButtons(){
		HBox box = new HBox();
		
		confirm = new Button("Save");
		confirm.setOnAction(this);
		cancel = new Button("Cancel");
		cancel.setOnAction(this);
		
		box.getChildren().addAll(confirm,cancel);
		
		return box;
	}
	
	public VBox getListViewBox(){
    	VBox box = new VBox(5);
    	HBox radioBox = new HBox(5);
        Label participants = new Label ("Participants");
        participationGroup = new ToggleGroup();
        
        allPersons = FXCollections.observableArrayList("Gunda-Ann","Krøll Alfa","Odd Morgan","Rune Linn","Johannes","Simen","Øivind","Jonatan");
        selectedPersons = FXCollections.observableArrayList();

    	allPersonList = new ListView<String>();
    	allPersonList.setPrefWidth(175);
    	allPersonList.setPrefHeight(130);
    	allPersonList.setItems(allPersons);
    	
    	addPerson = new Button("Add");
    	addPerson.setOnAction(this);

    	chosenPersonList = new ListView<String>();
    	chosenPersonList.setPrefWidth(175);
    	chosenPersonList.setPrefHeight(130);
    	chosenPersonList.setItems(selectedPersons);
    	
    	removePerson = new Button("Remove");
    	removePerson.setOnAction(this);
    	
    	going = new RadioButton("Going");
		going.setToggleGroup(participationGroup);
		notGoing = new RadioButton("Not going");
		notGoing.setToggleGroup(participationGroup);
    	
		radioBox.getChildren().addAll(going,notGoing);
    	box.getChildren().addAll(participants,allPersonList,addPerson,chosenPersonList,radioBox,removePerson);
    	
    	return box;
    }
	
	public boolean isValid(){
		boolean hasFailed = false;
    	// Check that the title field has some text
    	if (t_title.getText().length() <= 1) {
    		if (t_title.getText().contains(" ") || t_title.getText().isEmpty()) {
    			t_title.setPromptText("Need title");
    			hasFailed = true;;
    		}
    	}
    	// Checks that the date is in the correct format
    	if (t_date.getText().length() != 10 || !t_date.getText().substring(0, 2).matches("[0-9]+") || !t_date.getText().substring(3, 5).matches("[0-9]+") || !t_date.getText().substring(6).matches("[0-9]+")) {
    		t_date.setText("");
    		t_date.setPromptText("dd:mm:yyyy");
    		hasFailed = true;
    	}
    	// Check that the start and end time is in the correct format
    	if (t_start.getText().length() != 5 || !t_start.getText().substring(0, 2).matches("[0-9]+") || !t_start.getText().substring(3, 5).matches("[0-9]+")) {
    		t_start.setText("");
    		t_start.setPromptText("hh:ss");
    		hasFailed = true;
    	}
    	if (t_stop.getText().length() != 5 || !t_stop.getText().substring(0, 2).matches("[0-9]+") || !t_stop.getText().substring(3, 5).matches("[0-9]+")) {
    		t_stop.setText("");
    		t_stop.setPromptText("hh:ss");
    		hasFailed = true;
    	}
    	return !hasFailed;
	}
	

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource() == cancel){
			thisStage.close();			
		}
		else if(event.getSource() == confirm && isValid()){
			System.out.println("Send update");
			thisStage.close();
		}
		else if(event.getSource() == addPerson){
    		int id = allPersonList.getFocusModel().getFocusedIndex();
    		selectedPersons.add(allPersons.get(id));
    		allPersons.remove(id);        	
    	}
    	
    	else if(event.getSource() == removePerson){        	
    		int id = chosenPersonList.getFocusModel().getFocusedIndex();
    		allPersons.add(selectedPersons.get(id));
    		selectedPersons.remove(id);        	
    	}
		
	}

}
