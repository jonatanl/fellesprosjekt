// Made by Johannes
package client;





import util.DateHelper;
import Models.Event;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EndreIkkeOwner implements EventHandler<ActionEvent>{
	
	private Label l_title, l_date, l_start, l_stop, l_description, l_place, l_room, l_participation;
	private TextField t_title, t_date, t_start, t_stop, t_description, t_place, t_room;

	private ToggleGroup participationGroup;
	private Button confirm;
	private Event eventModel;
	private Stage thisStage;
	private Stage parentStage;
	private Calendar calendar;
	private RadioButton going;
	private RadioButton notGoing;	


	
	public EndreIkkeOwner(Calendar calendar, Event event, Stage parentStage) {
		try {
			createStage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.parentStage = parentStage;
		this.calendar = calendar;
		setModel(event);

	}


	
	public void setModel(Event model) {
		this.eventModel = model;
		String[] startDate = DateHelper.convertToString(model.getStartTime(),DateHelper.FORMAT_GUI).split(", ");
		String[] endDate = DateHelper.convertToString(model.getEndTime(),DateHelper.FORMAT_GUI).split(", ");
		String date = startDate[0];
		String startTime = startDate[1];
		String endTime = endDate[1];		
		
		t_title.setText(model.getEventName());
		t_date.setText(date);
		t_start.setText(startTime);
		t_stop.setText(endTime);
		t_description.setText(model.getDescription());
		t_place.setText(model.getLocation());
		t_room.setText(calendar.findRoom(model.getRoomId()).toString());
	}
	
	public void createStage() {
		
		participationGroup = new ToggleGroup();
		
		l_title = new Label("Tittel");
		l_date = new Label("Dato");
		l_start = new Label("Start");
		l_stop = new Label("Slutt");
		l_description = new Label("Beskrivelse");
		l_place = new Label("Sted");
		l_room = new Label("Rom");
		l_participation = new Label("Min Deltagelse:");
		
		t_title = new TextField();
		t_date = new TextField();
		t_start = new TextField();
		t_stop = new TextField();
		t_description = new TextField();
		t_place = new TextField();
		t_room = new TextField();
		
		confirm = new Button("Ok");
		confirm.setOnAction(this);
		
		going = new RadioButton("Skal");
		going.setToggleGroup(participationGroup);
		notGoing = new RadioButton("Skal ikke");
		notGoing.setToggleGroup(participationGroup);
		
		
		VBox leftBox = new VBox();
		leftBox.getChildren().addAll(l_title,l_date,l_start,l_stop,l_description,l_place,l_room);
		leftBox.setSpacing(10);
		leftBox.setPadding(new Insets(10,10,10,10));
		
		VBox rightBox = new VBox();
		rightBox.getChildren().addAll(t_title, t_date, t_start, t_stop, t_description, t_place, t_room);
		rightBox.setPadding(new Insets(10,0,0,0));
		rightBox.setSpacing(3);
		
		for (Node text : rightBox.getChildren()) {
			TextField textf = (TextField) text;
			textf.setDisable(true);
		}
		
		HBox participation = new HBox();
		participation.getChildren().addAll(l_participation, going, notGoing);
		participation.setPadding(new Insets(10,10,10,10));
		participation.setSpacing(10);
		
		
		HBox container = new HBox();
		container.getChildren().addAll(leftBox,rightBox);
		
		VBox confirmation = new VBox();
		confirmation.getChildren().add(confirm);
		confirmation.setPadding(new Insets(10,10,10,100));
		
		VBox root = new VBox();
		root.getChildren().addAll(container, participation, confirmation);
		
		Scene scene = new Scene(root, 300, 300);
		thisStage = new Stage();
		thisStage.setTitle("Endre Hendelse");
		thisStage.setScene(scene);
		
		thisStage.initModality(Modality.APPLICATION_MODAL);
		thisStage.initOwner(parentStage);
		
		thisStage.show();

	}



	@Override
	public void handle(ActionEvent actionEvent) {
		if(actionEvent.getSource() == confirm){
			thisStage.close();
		}
	}

}
