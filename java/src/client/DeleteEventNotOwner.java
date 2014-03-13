/*
 * @author Bugge
 */

package client;

import Models.Event;
import Models.EventParticipant;
import interfaces.CalendarInterface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DeleteEventNotOwner {

    Button ok_btn = new Button("Ok");
    Button cancel_btn = new Button("Cancel");
    CalendarInterface participant;
    private int eventID;
    private int participantID;
    
	private Stage thisStage;
    private Stage parentStage;
    
    public DeleteEventNotOwner(Event event, EventParticipant user, Stage parentStage, CalendarInterface ci) {
    	this.participant = ci;
    	this.participantID = user.getUserId();
    	this.eventID = event.getEventId();
    	
    	try {
			createStage();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	this.parentStage = parentStage;
    }
    
    
    public void createStage() throws Exception {
    	
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("You're about to remove this event");
        scenetitle.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
      
        HBox hbBtn = new HBox(10);
        ok_btn.setMinWidth(50);
        cancel_btn.setMinWidth(50);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.getChildren().addAll(ok_btn, cancel_btn);
        grid.add(hbBtn, 1, 3);

		Scene scene = new Scene(grid, 350, 150);
		thisStage = new Stage();
		thisStage.setTitle("Remove event");
		thisStage.setScene(scene);
		
		thisStage.initModality(Modality.APPLICATION_MODAL);
		thisStage.initOwner(parentStage);
		
		thisStage.show();

        
        ok_btn.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent actionEvent) {
        		System.out.println("Event removed");
        		participant.changeEventParticipantResponse(eventID, participantID, "Deleted", true);
        		thisStage.close();
            }
		});
        
        cancel_btn.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent actionEvent) {
        		System.out.println("Operation canceled");
        		thisStage.close();
            }
		});
        
    }


}