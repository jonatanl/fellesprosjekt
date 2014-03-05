package client;







import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

public class MainApp extends Application implements EventHandler<ActionEvent>{
	
	private Button editEvent;
	
	private TestEvent model;
	private Stage stage;
	
	public static void main(String[] args) {
		launch(args);
	}
	

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		editEvent = new Button("Endre Event");
		editEvent.setOnAction(this);
		
		VBox root = new VBox();
		root.getChildren().add(editEvent);
		
		Scene scene = new Scene(root, 500, 500);
		stage.setTitle("Main test");
		stage.setScene(scene);
		
		stage.show();
		
	
		
		
		model =  new TestEvent("Lunch", "05.03.2014", "13:15", "13:30", "Spise mat", "Månen", "");
	}

	@Override
	public void handle(ActionEvent arg0) {
		new EndreIkkeOwner(model, stage);
		
		
	}

	

}
