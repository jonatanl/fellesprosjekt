package client;

import interfaces.PersistencyInterface;

import java.util.ArrayList;
import java.util.Collections;

import util.DateHelper;
import util.Persistency;
import Models.Event;
import Models.EventParticipant;
import Models.Group;
import Models.Room;
import Models.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EditOwner implements EventHandler<ActionEvent>{
	
	private Label l_title, l_date, l_start, l_stop, l_description, l_place, l_room, l_participation;
	private TextField t_title, t_date, t_start, t_stop, t_description, t_place;
	private ComboBox<Room> roomList;
	
	private ToggleGroup participationGroup;
	private Button confirm, cancel, addPerson, removePerson, inviteParticipants;
	private RadioButton going, notGoing;
	private ListView<Object> allPersonListView, chosenPersonListView;
	private Event eventModel;
	private Stage thisStage;
	private Stage parentStage;
	
	private ObservableList<Object> allPersonsObservableList;
    private ObservableList<Object> selectedPersonsObservableList;
    
    private ObservableList<Room> allRoomsObservableList;
    
    private Room noRoom;
    
    private ArrayList<Room> rooms;
    private ArrayList<User> users;
    private ArrayList<Group> groups;
    private ArrayList<EventParticipant> currentParticipants;
    
    private ArrayList<EventParticipant> clonedParticipants = new ArrayList<EventParticipant>();
    
    ArrayList<EventParticipant> newParticipants = new ArrayList<EventParticipant>();
    ArrayList<EventParticipant> removedParticipants = new ArrayList<EventParticipant>();
    ArrayList<Group> newGroups = new ArrayList<Group>();

    private PersistencyInterface persistency;
    
    private Calendar calendar;
    
	
	public EditOwner(Event event, Stage parentStage, ArrayList<Room> rooms, ArrayList<User> users, ArrayList<Group> groups, ArrayList<EventParticipant> currentParticipants, PersistencyInterface persistency, Calendar calendar) {
		try {
			//noRoom = new Room();
			//noRoom.setAdress("not chosen");
			this.calendar = calendar;
			this.parentStage = parentStage;
			this.rooms = rooms;
			this.users = users;
			this.groups = groups;
			this.currentParticipants = currentParticipants;
			this.persistency = persistency;
			
			for (EventParticipant ep : currentParticipants) {
				clonedParticipants.add(new EventParticipant(ep));
			}
			this.eventModel = event;
			createStage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		setModel(event);
		this.parentStage = parentStage;

	}


	
	public void setModel(Event model) {
		this.eventModel = model;
		
		t_title.setText(eventModel.getEventName());
		t_date.setText(DateHelper.convertToString(eventModel.getStartTime(), DateHelper.FORMAT_GUI).substring(0,10));
		t_start.setText(DateHelper.convertToString(eventModel.getStartTime(), DateHelper.FORMAT_GUI).substring(12));
		t_stop.setText(DateHelper.convertToString(eventModel.getEndTime(), DateHelper.FORMAT_GUI).substring(12));
		t_description.setText(eventModel.getDescription());
		t_place.setText(eventModel.getLocation());
		updateRoomComboBox();
		roomList.getSelectionModel().select(calendar.findRoom(eventModel.getRoomId()));
	}
	
	public void createStage() {
		GridPane grid = new GridPane();
		
		grid.add(getLabels(), 1, 1);
		grid.add(getFields(), 2, 1);
		grid.add(getListViewBox(), 3, 1);
		//grid.add(getButtons(), 1, 2);
	
		//grid.add(vb, 2,1);
		
		grid.setHgap(15);
        grid.setVgap(15);
		
		Scene scene = new Scene(grid, 600, 400);
		thisStage = new Stage();
		thisStage.setResizable(false);
		thisStage.setTitle("Endre Hendelse");
		thisStage.setScene(scene);
		
		thisStage.initModality(Modality.APPLICATION_MODAL);
		thisStage.initOwner(parentStage);
		thisStage.show();

	}
	
	public VBox getLabels(){
		VBox box = new VBox();
		
		l_title = new Label("Title");
		l_date = new Label("Date");
		l_start = new Label("Start time");
		l_stop = new Label("End time");
		l_description = new Label("Description");
		l_place = new Label("Place");
		l_room = new Label("Room");
		
		HBox vb = getButtons();
		
		box.getChildren().addAll(l_title,l_date,l_start,l_stop,l_description,l_place,l_room, vb);
		box.setSpacing(10);
		box.setPadding(new Insets(10,10,10,10));
		
		return box;
	}
	
	public VBox getFields(){
		VBox box = new VBox();
		
		t_title = new TextField();
		t_title.setMinWidth(240);
		t_date = new TextField();
		t_start = new TextField();
		t_stop = new TextField();
		t_description = new TextField();
		t_place = new TextField();
		roomList = new ComboBox<>();
		roomList.setMinWidth(210);
		roomList.setVisibleRowCount(6);
		
		VBox vb = new VBox();
		inviteParticipants = new Button("Invite Externals");
		inviteParticipants.setOnAction(this);
		Label l = new Label(" ");
		l.setMinHeight(153);
		vb.getChildren().addAll(l, inviteParticipants);
		
		
		
	
		
		box.getChildren().addAll(t_title,t_date,t_start,t_stop,t_description,t_place,roomList, vb);
		box.setPadding(new Insets(10,0,0,0));
		box.setSpacing(3);
		
		return box;
		
	}
	
	private void updateRoomComboBox(){
		Room curRoom = roomList.getValue();
		int nParticipants = getSelectedParticipantIds().size();
        Collections.sort(rooms);

    	ArrayList<Room> sortedList = new ArrayList<Room>();
    	ArrayList<Room> goodRooms = new ArrayList<Room>();
    	ArrayList<Room> badRooms = new ArrayList<Room>();
    	
    	for (Room r:rooms){
    		if (r.getCapacity() >= nParticipants){
    			goodRooms.add(r);
    		}
    		else{
    			badRooms.add(r);
    		}
    	}
    	sortedList.addAll(goodRooms);
    	sortedList.addAll(badRooms);
    	//sortedList.add(noRoom);

    	ObservableList<Room> sortedObservableList = FXCollections.observableArrayList(sortedList);
    	
    	roomList.setItems(sortedObservableList);
        roomList.setValue(curRoom);
    }
	
	
	
	public HBox getButtons(){
		HBox box = new HBox(8);
		
		confirm = new Button("Save");
		confirm.setOnAction(this);
		cancel = new Button("Cancel");
		cancel.setOnAction(this);
		
		box.getChildren().addAll(confirm,cancel);
		box.setPadding(new Insets(145,0, 0,0));
		
		return box;
	}
	
	public VBox getListViewBox(){
		participationGroup = new ToggleGroup();
		
		VBox rightBox = new VBox(5);
        Label participants = new Label ("Participants");
    	
    	addPerson = new Button("Add");
    	addPerson.setOnAction(this);
    	
    	removePerson = new Button("Remove");
    	removePerson.setOnAction(this);
    	removePerson.setDisable(true);
    	
    	  	
    	going = new RadioButton("Going");
    	going.setDisable(true);
    	going.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				EventParticipant ep = getEventParticipantFromView();
				ep.setResponse(EventParticipant.going);
			}
		});
		going.setToggleGroup(participationGroup);
		
		notGoing = new RadioButton("Not going");
		notGoing.setDisable(true);
		notGoing.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {	
				EventParticipant ep = getEventParticipantFromView();
				ep.setResponse(EventParticipant.notGoing);
			}
		});
		notGoing.setToggleGroup(participationGroup);
	
    	
		populateLists();
		
    	rightBox.getChildren().addAll(participants,allPersonListView,addPerson,chosenPersonListView,removePerson);
    	HBox hb = new HBox(8);
    	hb.setPadding(new Insets(10,10,10,0));
    	hb.getChildren().addAll(going, notGoing);
    	rightBox.getChildren().add(hb);
    	
    	
    	
    	return rightBox;
    }
	
	private void populateLists(){
		ArrayList<Object> usersAndGroups = new ArrayList<Object>(users);
		usersAndGroups.addAll(groups);
		
		ArrayList<Object> participantUsers = new ArrayList<Object>();
		
		for (User u: users){
			for (EventParticipant ep: currentParticipants){
				if (u.getUserId() == ep.getUserId()){
					participantUsers.add(u);
					usersAndGroups.remove(u);
				}
			}
		}
		
		if (!participantUsers.isEmpty()) {
			removePerson.setDisable(false);
		}
			
        allPersonsObservableList = FXCollections.observableArrayList(usersAndGroups);
        selectedPersonsObservableList= FXCollections.observableArrayList(participantUsers);

    	allPersonListView = new ListView<Object>();
    	allPersonListView.setPrefWidth(190);
    	allPersonListView.setPrefHeight(150);
    	allPersonListView.setItems(allPersonsObservableList);
    	
    	chosenPersonListView = new ListView<Object>();
    	chosenPersonListView.setPrefWidth(190);
    	chosenPersonListView.setPrefHeight(150);
    	chosenPersonListView.setItems(selectedPersonsObservableList);
    	chosenPersonListView.focusedProperty().addListener(new ChangeListener<Boolean>() {
    		
    		@Override
    		public void changed(ObservableValue<? extends Boolean> arg0,
    				Boolean oldVal, Boolean newVal) {
    			//System.out.println(getEventParticipantFromView().getResponse());
    			if (newVal) {
    				setRadioButtons();
    			}
    		}
    	});
    	chosenPersonListView.setCellFactory(new Callback<ListView<Object>, ListCell<Object>>() {
			
			@Override
			public ListCell<Object> call(ListView<Object> arg0) {
				// TODO Auto-generated method stub
				return new ParticipantCell(eventModel);
			}
		});
	}
	
	public void setRadioButtons() {
		going.setDisable(false);
		notGoing.setDisable(false);	
		
		EventParticipant ep = getEventParticipantFromView();

		if (ep != null) {
			//System.out.println(ep.getResponse() + " == " +EventParticipant.going);
			//System.out.println(ep.getResponse().equals(EventParticipant.going));
			if (ep.getResponse() == null){
				going.setSelected(false);
				notGoing.setSelected(false);
			}
			else if (ep.getResponse().equals(EventParticipant.going)) {
				System.out.println("setter knapper");
				going.setSelected(true);
				notGoing.setSelected(false);
			}
			else if (ep.getResponse().equals(EventParticipant.notGoing)) {
				going.setSelected(false);
				notGoing.setSelected(true);
			}
		}
		else {
			going.setSelected(false);
			going.setDisable(true);
			notGoing.setDisable(true);
			notGoing.setSelected(false);
		}
		
	}
	
	public EventParticipant getEventParticipantFromView() {
		Object o = chosenPersonListView.getSelectionModel().getSelectedItem();
		if (o instanceof User) {
			User u = (User) o;
			ArrayList<EventParticipant> allParticipants = new ArrayList<EventParticipant>(clonedParticipants);
			allParticipants.addAll(newParticipants);
			for (EventParticipant ep : allParticipants) {
				if (ep.getUserId() == u.getUserId()) {
					return ep;
				}
			}
		}
		return null;
	}

	
	public void updateRadioButtons(boolean val) {
		going.setSelected(val);
		notGoing.setSelected(!val);
		
	}
	
	public boolean isValid(){
		boolean hasFailed = false;
    	// Check that the title field has some text
    	if (t_title.getText().length() <= 1) {
    		if (t_title.getText().contains(" ") || t_title.getText().isEmpty()) {
    			t_title.setPromptText("Need title");
    			hasFailed = true;;
    		}
    	}
    	// Checks that the date is in the correct format
    	if (t_date.getText().length() != 10 || !t_date.getText().substring(0, 2).matches("[0-9]+") || !t_date.getText().substring(3, 5).matches("[0-9]+") || !t_date.getText().substring(6).matches("[0-9]+")) {
    		t_date.setText("");
    		t_date.setPromptText("dd:mm:yyyy");
    		hasFailed = true;
    	}
    	// Check that the start and end time is in the correct format
    	if (t_start.getText().length() != 5 || !t_start.getText().substring(0, 2).matches("[0-9]+") || !t_start.getText().substring(3, 5).matches("[0-9]+")) {
    		t_start.setText("");
    		t_start.setPromptText("hh:ss");
    		hasFailed = true;
    	}
    	if (t_stop.getText().length() != 5 || !t_stop.getText().substring(0, 2).matches("[0-9]+") || !t_stop.getText().substring(3, 5).matches("[0-9]+")) {
    		t_stop.setText("");
    		t_stop.setPromptText("hh:ss");
    		hasFailed = true;
    	}
    	
    	if (!hasFailed) {
    		int startTimeMinutes = Integer.parseInt(t_start.getText().substring(3, 5));
    		int stopTimeMinutes = Integer.parseInt(t_stop.getText().substring(3, 5));
    		int startTimeHours = Integer.parseInt(t_start.getText().substring(0, 2));
    		int stopTimeHours = Integer.parseInt(t_stop.getText().substring(0, 2));
    		System.out.println("startH: " + startTimeHours + "\n" + "startM: " + startTimeMinutes);
    		if  ((startTimeHours > stopTimeHours) || (startTimeHours == stopTimeHours && startTimeMinutes >= stopTimeMinutes)) {
    			t_start.setText("");
    			t_start.setPromptText("Start time cannot be >= stop time. (hh:ss)");
    			hasFailed = true;
    		}    		
    	}
    	
    	return !hasFailed;
	}
	

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == inviteParticipants) {
			new SendMail(thisStage, eventModel);
		}
		
		else if(event.getSource() == cancel){
			thisStage.close();			
		}
		else if(event.getSource() == confirm && isValid()){
			System.out.println("Send update");
			if (isValid()) {
				updateEvent();				
				thisStage.close();
			}
			
		}
		
		else if(event.getSource() == addPerson){
			int id = allPersonListView.getFocusModel().getFocusedIndex();
    		if (id == -1) {
    			id = 0;
    		}
    		selectedPersonsObservableList.add(allPersonsObservableList.get(id));
    		
    		Object o = allPersonsObservableList.get(id);
    		
    		if (o instanceof User) {
    			System.out.println(((User)o).getUsername());
    			newParticipants.add(new EventParticipant(eventModel.getEventId(), ((User)o).getUserId()));
    		}
    		else if (o instanceof Group) {
    			System.out.println(((Group)o).getName());
    			newGroups.add((Group)o);
    		}

    		allPersonsObservableList.remove(id);
    		
    		int nParticipants = selectedPersonsObservableList.size();
            if(nParticipants > roomList.getValue().getCapacity())
                updateRoomComboBox();
    		
    		
    		if (allPersonsObservableList.size() == 0) {
    			addPerson.setDisable(true);
    		}
    		removePerson.setDisable(false);      	
    		allPersonListView.getSelectionModel().select(0);
    	}
		
		
    	
    	else if(event.getSource() == removePerson){        	
    		int id = chosenPersonListView.getFocusModel().getFocusedIndex();
    		
    		if (id == -1) {
    			id = 0;
    		}
    		allPersonsObservableList.add(selectedPersonsObservableList.get(id));
    		
    		//if the removed participant was originally in the event, add to removedParticipants, if not, just remove it from newParticipants
    		Object o = selectedPersonsObservableList.get(id);
    		
    		if (o instanceof User) {
    			User u = (User)o;
    			System.out.println(u.getUsername());
    			System.out.println("newlist: " + newParticipants.size());
    			System.out.println("cloned: " + clonedParticipants.size());
    			for (int i = 0; i < newParticipants.size(); i++) {
    				if (newParticipants.get(i).getUserId() == u.getUserId()) {
    					newParticipants.remove(i);
    					break;
    				}
    			}
    			for (int i = 0; i < clonedParticipants.size(); i++) {
    				if (clonedParticipants.get(i).getUserId() == u.getUserId()) {
    					removedParticipants.add(clonedParticipants.get(i));
    					clonedParticipants.remove(i);
    					break;
    				}
    			}
    			System.out.println("----------------");
    			System.out.println("newlist: " + newParticipants.size());
    			System.out.println("cloned: " + clonedParticipants.size());
    			
    			
    		}
    		else if (o instanceof Group) {
    			Group g = (Group)o;
    			System.out.println("Groups: " + newGroups.size());
    			if (newGroups.contains(g)) {
    				newGroups.remove(g);
    			}
    			System.out.println("-------------");
    			System.out.println("Groups: " + newGroups.size());
    		}
    		
    		selectedPersonsObservableList.remove(id);
    		updateRoomComboBox();
    		
    		if (selectedPersonsObservableList.size() == 0){
    			removePerson.setDisable(true);
    			going.setDisable(true);
    			notGoing.setDisable(true);
    		}
    		addPerson.setDisable(false);        	
    	}
		chosenPersonListView.getSelectionModel().select(0);
		
	}
	
	
	public void updateEvent() {

		for (EventParticipant cep : clonedParticipants) {
			if ((cep.getResponse() != null) &&
			 (!cep.getResponse().equals(calendar.findEventParticipant(cep.getUserId(), cep.getEventId()).getResponse()))){
				persistency.changeEventParticipantResponse(cep);
				calendar.changeEventParticipantResponse(eventModel.getEventId(), cep.getUserId(), cep.getResponse(), cep.isDeleted());
			}
		}
		
		// Update all new participants
		for (EventParticipant nep : newParticipants) {
			persistency.addEventParticipant(eventModel.getEventId(), nep.getUserId());
			calendar.addEventParticipant(nep);
		}
		
		// remove all original participants that has been removed
		for (EventParticipant rep: removedParticipants) {
			persistency.removeEventParticipant(eventModel.getEventId(), rep.getUserId());
			calendar.removeEventParticipant(rep);
		}
		
		
		for (Group g : newGroups) {
			ArrayList<Integer> members = g.getMembers();
			ArrayList<EventParticipant> allParticipants = new ArrayList<EventParticipant>(newParticipants);
			allParticipants.addAll(clonedParticipants);
			
			for (int i : members) {
				boolean contains = false;
				for (EventParticipant ep : allParticipants) {
					if (ep.getUserId() == i) {
						contains = true;
					}
				}
				if (!contains) {
					System.out.println("new participant: " + i);
					persistency.addEventParticipant(eventModel.getEventId(), i);
					calendar.addEventParticipant(new EventParticipant(eventModel.getEventId(), i));					
				}
				
			}
		}
		
		updateModels();
		
		
		
	}
	
	public void updateModels() {
		boolean eventIsChanged = false;
		if (!eventModel.getEventName().equals(t_title.getText())){
			eventModel.setEventName(t_title.getText());
			eventIsChanged = true;
		}
		String newStartTime = t_date.getText() + ", " + t_start.getText();
		if (!DateHelper.convertToString(eventModel.getStartTime(), DateHelper.FORMAT_GUI).equals(newStartTime)){
			eventModel.setStartTime(DateHelper.convertToDate(newStartTime, DateHelper.FORMAT_GUI));
			eventIsChanged = true;
		}
		String newEndTime = t_date.getText() + ", " + t_stop.getText();
		if (!DateHelper.convertToString(eventModel.getEndTime(), DateHelper.FORMAT_GUI).equals(newEndTime)){
			eventModel.setEndTime(DateHelper.convertToDate(newEndTime, DateHelper.FORMAT_GUI));
			eventIsChanged = true;
		}
		if (!eventModel.getDescription().equals(t_description.getText())){
			eventModel.setDescription(t_description.getText());
			eventIsChanged = true;
		}
		if (!eventModel.getLocation().equals(t_place.getText())){
			eventModel.setLocation(t_place.getText());
			eventIsChanged = true;
		}
		Room selectedRoom = roomList.getValue();
		if (eventModel.getRoomId() != selectedRoom.getId()){
			eventModel.setRoomId(roomList.getValue().getId());
			eventIsChanged = true;
		}
		
		if (eventIsChanged){
			persistency.changeEvent(eventModel);
			calendar.changeEvent(eventModel.getEventId(), eventModel);
		}
		
	}
	
	
	  private ArrayList<Integer> getSelectedParticipantIds(){
	    	ArrayList<Integer> result = new ArrayList<Integer>();
	    	for (Object o: selectedPersonsObservableList){
	    		if (o instanceof User){
	    			if (!result.contains(((User)o).getUserId())){
	    				result.add(((User)o).getUserId());
	    			}
	    		}
	    		else if (o instanceof Group){
	    			for (Integer u:((Group)o).getMembers()){
	    				if (!result.contains(u)){
	    					result.add(u);
	    				}
	    			}
	    		}
	    	}
	    	return result;
	    }


}
