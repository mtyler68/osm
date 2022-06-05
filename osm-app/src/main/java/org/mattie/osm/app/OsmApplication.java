package org.mattie.osm.app;

import javafx.application.Application;
import org.mattie.osm.app.application.SpringbootJavaFxApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Matt
 */
@SpringBootApplication
public class OsmApplication {

    public static void main(String[] args) {
        Application.launch(SpringbootJavaFxApplication.class, args);
    }
}
