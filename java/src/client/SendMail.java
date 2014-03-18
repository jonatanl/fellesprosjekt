package client;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import Models.Event;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

 
public class SendMail implements EventHandler<ActionEvent> {
    
	private Event model;	
    private ObservableList<String> receivers;
    private Stage stage, parentstage;
    private TextField mailField;
    private ListView<String> addedPersonsView;
    private Button addButton, removeButton, sendButton, cancelButton;
    private Label infoText;
    private String title, startTime, endTime, description, location, mailText;
    
    public SendMail(Stage parentStage, Event eventModel){
    	try {
    		
    		this.model = eventModel;
    		this.parentstage = parentStage;
            createMailText();
    		createStage();

    	} catch (Exception e) {
    		e.printStackTrace();
    	}    	
    }
    
    public void createStage() {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        infoText = new Label("Enter the participants emails");

        grid.add(infoText, 0, 0);
        grid.add(top(), 0, 1);
        grid.add(middle(), 0, 2);
        grid.add(bottom(), 0, 3);

        Scene scene = new Scene(grid, 350, 400);

        stage = new Stage();
        stage.setTitle("Invite external people");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(parentstage);
        stage.show();
    }
    
    public HBox top(){
    	HBox topBox = new HBox();
    	addButton = new Button("Add");
    	removeButton = new Button("Remove");
    	mailField = new TextField();
    	addButton.setOnAction(this);
    	removeButton.setOnAction(this);
    	topBox.setSpacing(5);
    	topBox.getChildren().addAll(addButton, removeButton, mailField);
    	return topBox;
    }
    
    public VBox middle(){
    	VBox middleBox = new VBox();
    	addedPersonsView = new ListView<>();
		receivers = FXCollections.observableArrayList();
    	middleBox.getChildren().add(addedPersonsView);
    	return middleBox;
    }
    
    public HBox bottom(){
    	HBox bottomBox = new HBox();
    	sendButton = new Button("Send");
        cancelButton = new Button("Cancel");
        sendButton.setMinWidth(60);
        cancelButton.setMinWidth(60);
    	sendButton.setOnAction(this);
        cancelButton.setOnAction(this);
        sendButton.setDisable(true);
        bottomBox.setSpacing(10);
    	bottomBox.getChildren().addAll(sendButton, cancelButton);
    	return bottomBox;
    }
    
    public void createMailText(){

    	title = "" + model.getEventName();
    	startTime = "" + model.getStartTime();
    	endTime = "" + model.getEndTime();
    	description = "" + model.getDescription();
    	location = "" + model.getLocation();
    	
    	mailText = "You are invited to the event: " + title + ".\n\nStart time: " + startTime +
    			"\nEnd time: " + endTime +  "\nDescription: " + description + "\nLocation: " +
    			location + "\n\nThe Skalender Team";
    }
        
	@Override
	public void handle(ActionEvent actionEvent) {
		if (actionEvent.getSource() == addButton) {
            if(!mailField.getText().equals("")){
			    receivers.add(mailField.getText());
			    mailField.clear();
			    addedPersonsView.setItems(receivers);
                sendButton.setDisable(false);
            }
		}
		else if(actionEvent.getSource() == removeButton){
            if(!receivers.isEmpty())
            	if (addedPersonsView.getFocusModel().getFocusedIndex() != -1) {
            		receivers.remove(addedPersonsView.getFocusModel().getFocusedIndex());            		
            	}
            if(receivers.isEmpty())
                sendButton.setDisable(true);
        }
		else if(actionEvent.getSource() == sendButton){
			mailSender();
			stage.close();
		}
        else if(actionEvent.getSource() == cancelButton)
            stage.close();
	}
	
	public void mailSender(){
        final String username = "skalendermail@gmail.com";
        final String password = "skalender";
		
		for(String receiver:receivers){			
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
	 
			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });
	 
			try {
	 
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("skalendermail@gmail.com"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
				message.setSubject("Invite to " + model.getEventName());
				message.setText(mailText);
	 
				Transport.send(message);
	 
				System.out.println("Mail sendt");
	 
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}		
	}    
}