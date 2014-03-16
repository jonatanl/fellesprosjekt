package client;

import interfaces.PersistencyInterface;

import java.util.ArrayList;
import java.util.Date;

import util.Persistency;
import Models.Alarm;
import Models.Event;
import Models.EventParticipant;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class Notifications extends Region  {
	private Label extraSpaceText1, extraSpaceText2;
	
	
	public Notifications(){
		
		this.getChildren().addAll();
		
	}
}
