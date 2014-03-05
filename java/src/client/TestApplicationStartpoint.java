package client;

import util.Time;
import Models.Alarm;
import javafx.stage.Stage;
import javafx.application.Application;

public class TestApplicationStartpoint extends Application{

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Alarm a = new Alarm();
		a.setTime(Time.toTimeString(12, 15));
		EditAlarm editAlarm = new EditAlarm(a, "13:00");
		editAlarm.start(stage);
	}
}
