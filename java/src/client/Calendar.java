package client;

import java.util.ArrayList;

import Models.Event;
import Models.User;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//Main class of the calendar system. 
public class Calendar extends Application{
	
	private ArrayList<Event> events;
	private ArrayList<User> users;
	
	public static void main(String[] args)  {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        
        Text title = new Text("Calendar system, main window.");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(title, 0, 0, 2, 1);
		
		Scene scene = new Scene(grid, 300, 275);
        stage.setScene(scene);
        stage.show();
	}
}
