/**
 * @author Bugge
 */
package buggetest;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BuggeTest extends Application {
	
	Button btn_ok;
        Button btn_avbryt;


	@Override
	public void start(Stage stage) throws Exception {
		btn_ok = new Button("Ok");
                btn_avbryt = new Button("Avbryt");
		
		btn_ok.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Event is deleted");				
			}
		});
                btn_avbryt.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Operation is canceled");				
			}
		});
		
		VBox root = new VBox();
		root.getChildren().addAll(btn_ok, btn_avbryt);
		
		Scene scene = new Scene(root, 500, 500);
		stage.setScene(scene);
		stage.show();
		
	}
        	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	
}
