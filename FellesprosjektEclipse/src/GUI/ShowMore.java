package GUI;

import java.util.ArrayList;

import sun.reflect.generics.tree.BottomSignature;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
 
public class ShowMore extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    ObservableList<String> items;
    Label title,start,end,description,place,room,alert,timeBefore,myParticipation;
    
    @Override
    public void start(Stage primaryStage) {
    		
        primaryStage.setTitle("Vis mer");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        grid.add(getLeftBox(), 0, 0);
        grid.add(getMiddleBox(), 1, 0);
        grid.add(getListViewBox(), 2, 0);
        grid.add(getRadioBox(), 0, 1);
        grid.add(getOkButton(), 0, 2);
        
        Scene scene = new Scene(grid, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    public VBox getListViewBox(){
    	VBox rightBox = new VBox();
        Label participants = new Label ("Deltakere");

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
        btnOk.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("back");
            }
        });
        return btnOk;
    }
    
    public VBox getLeftBox(){
    	Label title = new Label ("Tittel");
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
    
    public VBox getMiddleBox(){
    	Label eventTitle = new Label ("Tittel");
        Label eventStart = new Label ("Start");
        Label eventEnd = new Label ("Slutt");
        Label eventDescription = new Label ("Beskrivelse");
        Label eventPlace = new Label ("Sted");
        Label eventRoom = new Label ("Rom");
        Label eventAlert = new Label ("Alarm");
        Label eventTimeBefore = new Label ("Tid før");
        Label eventMyParticipation = new Label ("Min deltakelse");
        
        VBox middleBox = new VBox();
        middleBox.getChildren().addAll(eventTitle,eventStart,eventEnd,eventDescription,eventPlace,eventRoom,eventAlert,eventTimeBefore,eventMyParticipation);
        
        return middleBox;
    }
    
    public HBox getRadioBox(){
    	HBox radioBox = new HBox();
        radioBox.setPadding(new Insets(5, 5, 5, 5));
        
        ToggleGroup group = new ToggleGroup();
    	
    	RadioButton rb1 = new RadioButton("Skal");
    	rb1.setToggleGroup(group);
    	rb1.setSelected(true);

    	RadioButton rb2 = new RadioButton("Skal ikke");
    	rb2.setToggleGroup(group);
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
    
}