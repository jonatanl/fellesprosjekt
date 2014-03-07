//Made by Johannes
package client;


import Models.Event;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

public class ButtonMenu extends Application implements EventHandler<ActionEvent>{
	
	private Button b_createEvent, b_editEvent, b_deleteEvent, b_showMore, b_alert;
	
	private Event model;
	private Stage stage;
	
	public static void main(String[] args) {
		launch(args);
	}
	

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		b_createEvent = new Button("Legg til");
		b_createEvent.setMinWidth(60);
		b_createEvent.setOnAction(this);
		
		b_editEvent = new Button("Endre");
		b_editEvent.setMinWidth(60);
		b_editEvent.setOnAction(this);
		
		b_deleteEvent = new Button("Slett");
		b_deleteEvent.setOnAction(this);
		b_deleteEvent.setMinWidth(60);
		
		b_showMore = new Button("Vis mer");
		b_showMore.setOnAction(this);
		b_showMore.setMinWidth(60);
		
		b_alert = new Button("Alarm");
		b_alert.setOnAction(this);
		b_alert.setMinWidth(60);
		
		VBox root = new VBox();
		root.getChildren().addAll(b_createEvent, b_editEvent, b_deleteEvent, b_showMore, b_alert);
		
		Scene scene = new Scene(root, 500, 500);
		stage.setTitle("Main test");
		stage.setScene(scene);
		
		stage.show();
		
		model = new Event();
	}

	@Override
	public void handle(ActionEvent buttonEvent) {
		
		if (buttonEvent.getSource() == b_createEvent) {
			System.out.println("legg til event");
			new AddEvent(stage);
		}
		
		else if (buttonEvent.getSource() == b_editEvent) {
			new EndreIkkeOwner(model, stage);			
		}
		else if (buttonEvent.getSource() == b_showMore) {
			new ShowMore(model, stage);
		}
		
		else if (buttonEvent.getSource() == b_alert) {
			new EditAlarm(model,stage);
			
		}
		
	}

	

}
