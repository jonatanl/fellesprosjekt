package client.calendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Date;

/**
 * Created by ochamp on 2/26/14.
 */
public class CalendarTest extends Application{

    private Scene scene;
    private Calendar calendar;


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Calendar Frame");
        calendar = new Calendar();
        scene = new Scene(calendar.getContentForScene());
        stage.setScene(scene);
        stage.show();
        calendar.addEvent("666", "Kode Session", "2014-03-10 12:00:00" , "2014-03-10 13:00:00" ); //Example of adding event
    }

    public static void main(String[] args) {
        launch(args);
    }
}
