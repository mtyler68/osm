package org.mattie.osm.app.viewcontrollers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.mattie.osm.app.dmx.DmxKeyFrame;
import org.mattie.osm.app.dmx.DmxKeyValue;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Matt Tyler
 */
@Slf4j
@Component
@FxmlView("DmxKeyFrameView.fxml")
public class DmxKeyFrameViewController {

    @FXML
    public TextField durationField;

    @FXML
    public Slider timeSlider;

    @FXML
    public Label positionLabel;

    @FXML
    public Label durationLabel;

    @FXML
    public ListView<DmxKeyFrame> keyFrameList;

    private Timeline timeline = new Timeline();

    private ObservableList<DmxKeyFrame> keyFrames = FXCollections.observableArrayList();

    public ObservableList<DmxKeyFrame> keyFramesProperty() {
        return keyFrames;
    }

    private ObjectProperty<Duration> playbackPosition = new SimpleObjectProperty<>();

    public ObjectProperty<Duration> playbackPositionProperty() {
        return playbackPosition;
    }

    public void setPlaybackPosition(Duration playbackPosition) {
        this.playbackPosition.set(playbackPosition);
    }

    public Duration getPlaybackPosition() {
        return playbackPosition.get();
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {

        durationField.textProperty().addListener((ov, oldVal, newVal) -> {
            try {
                java.time.Duration newDur = java.time.Duration.parse(newVal);
                durationLabel.setText(formatDuration(fromTimeDuration(newDur)));
                timeSlider.setMax((double) newDur.toMillis() / 1000d);
            } catch (Exception e) {
            }
        });

        timeSlider.valueProperty().addListener((ov, oldVal, newVal) -> {
            Duration pos = Duration.seconds(newVal.doubleValue());
            setPlaybackPosition(pos);

            timeline.playFrom(pos);

            if (timeline.getStatus() != Animation.Status.RUNNING) {
                timeline.pause();
            }

        });

        playbackPosition.addListener((ov, oldVal, newVal) -> {
            positionLabel.setText(formatDuration(newVal));
            timeSlider.setValue(newVal.toSeconds());
        });

        keyFrameList.setItems(keyFrames);

        timeline.currentTimeProperty().addListener((ov, oldVal, newVal) -> {
            setPlaybackPosition(newVal);
        });
    }

    protected String formatDuration(Duration source) {
        java.time.Duration d = java.time.Duration.ofMillis((int) source.toMillis());
        return String.format("%02d:%02d:%02d.%d", d.toHoursPart(), d.toMinutesPart(), d.toSecondsPart(), d.toMillisPart() / 100);
    }

    protected Duration fromTimeDuration(java.time.Duration source) {
        return Duration.millis(source.toMillis());
    }

    public void createKeyFrame(List<DmxDimmerControlViewController> dmxControls) {
        Duration time = Duration.seconds(timeSlider.getValue());
        List<DmxKeyValue> keyValues = dmxControls.stream()
                .filter(dc -> dc.keyFrameCheck.isSelected())
                .map(dc -> new DmxKeyValue(dc.getAddress(), dc.getValue()))
                .collect(Collectors.toList());
        if (!keyValues.isEmpty()) {
            DmxKeyFrame keyFrame = new DmxKeyFrame(time, keyValues.toArray(new DmxKeyValue[keyValues.size()]));
            keyFrames.add(keyFrame);

            Collections.sort(keyFrames, (o1, o2) -> {
                return o1.getTime().compareTo(o2.getTime());
            });

            timeline.getKeyFrames().clear();
            for (DmxKeyFrame keyFrame1 : keyFrames) {
                List<KeyValue> values = new ArrayList<>();
                for (DmxKeyValue value : keyFrame1.getValues()) {
                    for (DmxDimmerControlViewController dmxControl : dmxControls) {
                        if (value.getAddress() == dmxControl.getAddress()) {
                            KeyValue keyValue = new KeyValue(dmxControl.valueProperty(), value.getValue(), Interpolator.EASE_BOTH);
                            values.add(keyValue);
                            break;
                        }
                    }
                }
                timeline.getKeyFrames().add(new KeyFrame(keyFrame1.getTime(), values.toArray(new KeyValue[values.size()])));
            }
            log.debug("timeline.keyFrames={}", timeline.getKeyFrames());
            setPlaybackPosition(time);
        }
    }

    public void togglePlayPause() {
        if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.pause();
        } else if (getPlaybackPosition() != null) {
            timeline.playFrom(getPlaybackPosition());
        }
    }
}
