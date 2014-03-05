package client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends Application implements EventHandler<ActionEvent> {
    private Text messageText;
    private TextField username;
    private PasswordField password;
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("YO!");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Username:");
        grid.add(userName, 0, 1);

        username = new TextField();
        grid.add(username, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        password = new PasswordField();
        grid.add(password, 1, 2);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        messageText = new Text("Sign in button pressed");
        messageText.setFill(Color.FIREBRICK);
        messageText.setVisible(false);
        grid.add(messageText, 1, 6);

        Scene scene = new Scene(grid, 300, 275);
        stage.setScene(scene);
        stage.show();

        btn.setOnAction(this);
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (!messageText.isVisible())
            messageText.setVisible(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
