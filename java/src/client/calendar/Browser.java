package client.calendar;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.io.File;

/**
 * Created by ochamp on 2/26/14.
 */
public class Browser extends Region{

    private final WebView browser;
    private final WebEngine webEngine;

    public Browser() {
        browser = new WebView();
        webEngine = browser.getEngine();
        String basePath = "file:" + new File("").getAbsolutePath() + "/libs/fullcalendar/demos/agenda-views.html";
        System.out.println("Setting content for webView... @ " + basePath);
        webEngine.load(basePath);
        getChildren().add(browser);

        //Legg til callback for kalenderklikk
        callBackJavaScript();
    }

    public void callJavaScript(final String call){
        System.out.println("javaScript string: " + call);

        webEngine.getLoadWorker().stateProperty().addListener(
            new ChangeListener<State>() {
                public void changed(ObservableValue ov, State oldState, State newState) {
                    if (newState == State.SUCCEEDED) {
                        webEngine.executeScript(call);
                    }
                }
            });
        webEngine.reload();
    }

    public void callBackJavaScript() {
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
            @Override
            public void changed(ObservableValue<? extends State> observableValue, State oldState, State newState) {
                if (newState == State.SUCCEEDED) {
                    JSObject window = (JSObject) webEngine.executeScript("window");
                    window.setMember("app", new CallBack());
                }
            }
        });
    }

    public static class CallBack {
        public void onClickIDtoJavaFX(String id) {
            System.out.println(id);
        }
    }
}
