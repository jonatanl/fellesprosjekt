package client.calendar;

import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.net.URL;

/**
 * Created by ochamp on 2/26/14.
 */
public class Browser extends Region{

    final WebView browser;
    final WebEngine webEngine;

    public Browser() {
        browser = new WebView();
        webEngine = browser.getEngine();
        String url = "libs/fullcalendar/demos/test.html";
        webEngine.load(url);
        getChildren().add(browser);
    }
}
