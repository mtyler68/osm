package org.mattie.osm.app.viewcontrollers;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.mattie.osm.app.events.ShowOpenedEvent;
import org.mattie.osm.app.viewmodel.CueState;
import org.mattie.osm.app.viewmodel.ShowViewModel;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Matt
 */
@Slf4j
@Component
@FxmlView("MainView.fxml")
public class MainViewController {

    private ShowViewModel showViewModel;

    @FXML
    public SplitPane topHSplitPane;

    @FXML
    public Label statusLabel;

    private FadeTransition fadeTransition;

    @FXML
    public void initialize() {
        statusLabel.setTextFill(Paint.valueOf("red"));
    }

    @EventListener
    public void showOpenedHandler(ShowOpenedEvent evt) {
        this.showViewModel = evt.getShowViewModel();
        log.debug("showOpenedHandler(): showViewModel={}", showViewModel);

        showViewModel.stateProperty().addListener((ov, oldVal, newVal) -> {
            statusLabel.setText(newVal.name());

            if (newVal == CueState.WAITING) {
                statusLabel.setTextFill(Paint.valueOf("red"));
                fadeTransition = new FadeTransition(Duration.seconds(0.25), statusLabel);
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                fadeTransition.setCycleCount(Animation.INDEFINITE);
                fadeTransition.play();
            } else {
                if (fadeTransition != null) {
                    fadeTransition.stop();
                    fadeTransition = null;
                    statusLabel.setOpacity(1);
                }
                statusLabel.setTextFill(Paint.valueOf("black"));
            }
        });
    }
}
