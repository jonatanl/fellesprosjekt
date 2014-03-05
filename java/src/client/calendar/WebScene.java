package client.calendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by ochamp on 2/26/14.
 */
public class WebScene extends Application{

    private Scene scene;
    private Browser browser;


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Calendar Frame");
        browser = new Browser();
        scene = new Scene(browser);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
