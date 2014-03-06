package client;

import java.util.ArrayList;

import Models.Event;
import Models.Room;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
 
public class ShowMore implements EventHandler<ActionEvent> {
    
    private ObservableList<String> items;
    private String s_title,s_start,s_end,s_description,s_place,s_room,s_alert,s_timeBefore,s_myParticipation;
    private Label l_title,l_start,l_end,l_description,l_place,l_room,l_alert,l_timeBefore,l_myParticipation;
    
    private Stage parentStage;
    private Stage stage;
    private Event event;
    
    public void createStage() {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        grid.add(getLeftBox(), 0, 0);
        grid.add(getDataBox(), 1, 0);
        grid.add(getListViewBox(), 2, 0);
        grid.add(getRadioBox(), 0, 1);
        grid.add(getOkButton(), 0, 2);
        
        Scene scene = new Scene(grid, 500, 300);
        
        stage = new Stage();
        stage.setTitle("Vis mer");
        stage.setScene(scene);
        stage.show();
    }
    
    public ShowMore(Event event, Stage stage){
    	try {
			createStage();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	this.event = event;
    	this.parentStage = stage;
    	setEvent(event.getEventName(), event.getDescription(), event.getLocation());
    	
    }
    
    public VBox getListViewBox(){
    	VBox rightBox = new VBox();
        Label participants = new Label ("Deltagere");

    	ListView<String> list = new ListView<String>();
    	ObservableList<String> items = getList();
    	list.setItems(items);
    	list.setPrefWidth(175);
    	list.setPrefHeight(130);
    	
    	rightBox.getChildren().addAll(participants,list);
    	
    	return rightBox;
    }
    
    public Button getOkButton(){
    	Button btnOk = new Button();
        btnOk.setText("Ok");
        btnOk.setOnAction(this);
        return btnOk;
    }
    
    public VBox getLeftBox(){
    	Label title = new Label ("Tittel");
    	Label date = new Label ("Dato");
        Label start = new Label ("Start");
        Label end = new Label ("Slutt");
        Label description = new Label ("Beskrivelse");
        Label place = new Label ("Sted");
        Label room = new Label ("Rom");
        Label alert = new Label ("Alarm");
        Label timeBefore = new Label ("Tid før");
        Label myParticipation = new Label ("Min deltakelse");
        
        
        VBox leftBox = new VBox();
        leftBox.getChildren().addAll(title,start,end,description,place,room,alert,timeBefore,myParticipation);
        
        return leftBox;
    }
    
    public VBox getDataBox(){
    	Label eventTitle = new Label (s_title);
        Label eventStart = new Label (s_start);
        Label eventEnd = new Label (s_end);
        Label eventDescription = new Label (s_description);
        Label eventPlace = new Label (s_place);
        Label eventRoom = new Label (s_room);
        Label eventAlert = new Label ();
        Label eventTimeBefore = new Label ();
        Label eventMyParticipation = new Label ();
        
        VBox middleBox = new VBox();
        middleBox.getChildren().addAll(eventTitle,eventStart,eventEnd,eventDescription,eventPlace,eventRoom,eventAlert,eventTimeBefore,eventMyParticipation);
        
        return middleBox;
    }
    
    public void setEvent(String title, String description, String place){
    	this.s_title = title;
    	this.s_description = description;
    	this.s_place = place;
    }
    
    public HBox getRadioBox(){
    	HBox radioBox = new HBox();
        radioBox.setPadding(new Insets(5, 5, 5, 5));
        
        ToggleGroup group = new ToggleGroup();
    	
    	RadioButton rb1 = new RadioButton("Skal");
    	rb1.setToggleGroup(group);
    	rb1.setSelected(true);
    	rb1.setDisable(true);

    	RadioButton rb2 = new RadioButton("Skal ikke");
    	rb2.setToggleGroup(group);
    	rb2.setDisable(true);
    	radioBox.getChildren().addAll(rb1,rb2);
    	
    	return radioBox;
    }
    
    public void addPersonsToList(ArrayList<String> people){
    	ObservableList<String> items =FXCollections.observableArrayList();
    	for (int i = 0; i < people.size()-1; i++) {
			items.add(people.get(i));
		}
    }
    public ObservableList<String> getList(){
    	return items;
    }

	@Override
	public void handle(ActionEvent event) {
		stage.close();
		
	}
    
}