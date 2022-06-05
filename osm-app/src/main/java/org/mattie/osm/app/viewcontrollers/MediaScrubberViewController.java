package org.mattie.osm.app.viewcontrollers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.mattie.osm.app.viewmodel.CueState;
import org.mattie.osm.app.viewmodel.MediaCueViewModel;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Matt
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@FxmlView("MediaScrubberView.fxml")
public class MediaScrubberViewController {

    @FXML
    public ProgressBar progressBar;

    @FXML
    public Label label;

    @FXML
    public Button finishButton;

    @FXML
    public SplitMenuButton fadeSplitButton;

    @Getter
    private MediaCueViewModel model;

    private double getTotalDuration() {
        return model.getMediaPlayer().getTotalDuration().toSeconds();
    }

    void configure(MediaCueViewModel cvm) {
        this.model = cvm;

        cvm.getMediaPlayer().currentTimeProperty().addListener((ov, oldVal, newVal) -> {
            progressBar.setProgress(newVal.subtract(cvm.getStartAt()).toSeconds() / getTotalDuration());
            label.setText(String.format("%s (%s)", cvm.getDesc(), formatDuration(newVal.subtract(model.getStartAt()))));
        });

        progressBar.setOnMousePressed((evt) -> {
            double time = evt.getX() / progressBar.getWidth();
            cvm.jumpTo(Duration.seconds(time * getTotalDuration()));
        });

        finishButton.setOnAction((evt) -> cvm.end());

        // Fade Menu Button
        fadeSplitButton.setOnAction(new FadeEventHandler(5));

        cvm.getMediaPlayer().statusProperty().addListener((ov, oldVal, newVal) -> {
            fadeSplitButton.setDisable(cvm.getMediaPlayer().getStatus() != MediaPlayer.Status.PLAYING);
        });

        for (int ndx = 1; ndx < 5; ndx++) {
            MenuItem menuItem = new MenuItem(String.format("Fade (%ss)", ndx));
            menuItem.setOnAction(new FadeEventHandler(ndx));
            fadeSplitButton.getItems().add(menuItem);
        }
    }

    protected String formatDuration(Duration source) {
        java.time.Duration d = java.time.Duration.ofMillis((int) source.toMillis());
        return String.format("%02d:%02d:%02d.%d", d.toHoursPart(), d.toMinutesPart(), d.toSecondsPart(), d.toMillisPart() / 100);
    }

    private void fade(double dur) {
        if (model.getState() == CueState.PLAYING) {
            model.fadeOut(Duration.seconds(dur));
        }
    }

    @RequiredArgsConstructor
    public class FadeEventHandler implements EventHandler<ActionEvent> {

        private final double duration;

        @Override
        public void handle(ActionEvent t) {
            fade(duration);
        }

    }
}
