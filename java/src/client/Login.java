package client;

import interfaces.PersistencyInterface;
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

public class Login implements EventHandler<ActionEvent> {
    private TextField username;
    private PasswordField password;
    private Text errorMessage;
    
    private PersistencyInterface persistency;
    private Calendar calendar;
    
    private Stage stage;
    
    public Login(Calendar c, PersistencyInterface p){
    	calendar = c;
    	persistency = p;
    }
    
    public void createStage()  {
    	stage = new Stage();
        stage.setTitle("Login");
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

        username = new TextField("johannes");
        username.setOnAction(this);
        grid.add(username, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        password = new PasswordField();
        password.setText("123456");
        password.setOnAction(this);
        grid.add(password, 1, 2);
        
        errorMessage = new Text();
        errorMessage.setText("* Invalid username/password");
        errorMessage.setFill(Color.FIREBRICK);
        errorMessage.setVisible(false);
        
        grid.add(errorMessage, 0, 4);
        
        Button btn = new Button("Sign in");
        btn.setOnAction(this);

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);

        grid.add(hbBtn, 1, 4);

        Scene scene = new Scene(grid, 300, 275);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void handle(ActionEvent actionEvent) {
    	int userId = persistency.requestLogin(username.getText(), password.getText());
    	if (userId >= 0){
    		calendar.startMainView(userId);
    		stage.close();
    	}
    	else{
    		errorMessage.setVisible(true);
    	}
    	
    }

}
