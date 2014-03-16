package client;

import interfaces.PersistencyInterface;

import java.util.ArrayList;
import java.util.Date;

import util.Persistency;
import Models.Alarm;
import Models.Event;
import Models.EventParticipant;
import Models.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class Notifications extends Region  {
	private ListView<Notification> notificationsListView;
	private ObservableList<Notification> notifications;
	
	
	public Notifications(){
		notifications = FXCollections.observableArrayList();
		notificationsListView = new ListView<Notification>(notifications);
		
		this.getChildren().add(notificationsListView);
	}

	public void addNotification(Notification n){
		notifications.add(n);
	}
	
	
	public void removeAll(){
		notifications.removeAll(notifications);
	}
	
}
