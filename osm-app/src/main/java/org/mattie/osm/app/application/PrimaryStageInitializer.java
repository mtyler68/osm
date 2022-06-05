package org.mattie.osm.app.application;

import java.util.prefs.Preferences;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.mattie.osm.app.viewcontrollers.MainViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Matt
 */
@Component
public class PrimaryStageInitializer implements ApplicationListener<StageReadyEvent> {

    private final FxWeaver fxWeaver;

    @Autowired
    public PrimaryStageInitializer(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.stage;
        Preferences prefs = Preferences.userNodeForPackage(org.mattie.osm.app.OsmApplication.class);

        stage.maximizedProperty().addListener((o) -> {
            prefs.putBoolean("maximized", stage.isMaximized());
        });

        Scene scene = new Scene(fxWeaver.loadView(MainViewController.class), 800, 600);
        stage.setScene(scene);
        stage.show();

        if (prefs.getBoolean("maximized", false)) {
            stage.setMaximized(true);
        }
    }
}
