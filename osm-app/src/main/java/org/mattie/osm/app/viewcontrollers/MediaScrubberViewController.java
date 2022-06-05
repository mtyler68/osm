package org.mattie.osm.app.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
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

    @Getter
    private MediaCueViewModel model;

    private double getTotalDuration() {
        return model.getMediaPlayer().getTotalDuration().toSeconds();
    }

    void configure(MediaCueViewModel cvm) {
        this.model = cvm;

        cvm.getMediaPlayer().currentTimeProperty().addListener((ov, oldVal, newVal) -> {
            progressBar.setProgress(newVal.subtract(cvm.getStartAt()).toSeconds() / getTotalDuration());
            label.setText(String.format("%s (%s)", cvm.getDesc(), formatDuration(newVal)));
        });

        progressBar.setOnMousePressed((evt) -> {
            double time = evt.getX() / progressBar.getWidth();
            cvm.jumpTo(Duration.seconds(time * getTotalDuration()));
        });
    }

    protected String formatDuration(Duration source) {
        java.time.Duration d = java.time.Duration.ofMillis((int) source.toMillis());
        return String.format("%02d:%02d:%02d.%d", d.toHoursPart(), d.toMinutesPart(), d.toSecondsPart(), d.toMillisPart() / 100);
    }
}
