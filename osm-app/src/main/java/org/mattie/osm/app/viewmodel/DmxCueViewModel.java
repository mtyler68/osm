package org.mattie.osm.app.viewmodel;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.mattie.osm.app.viewcontrollers.DmxControlConsoleViewController;
import org.mattie.osm.app.viewcontrollers.DmxDimmerControlViewController;
import org.mattie.osm.model.DmxCue;
import org.mattie.osm.model.dmx.DmxKeyFrame;

/**
 *
 * @author Matt Tyler
 */
@Slf4j
public class DmxCueViewModel extends CueViewModel<DmxCue> {

    private DmxControlConsoleViewController console;

    @Setter
    @Getter
    private List<DmxKeyFrame> keyFrames;

    @Getter
    private List<IntegerProperty> values = new ArrayList<>(512);

    @Override
    public String getSimpleType() {
        return "DMX";
    }

    @Override
    public Class<DmxCue> getCueClass() {
        return DmxCue.class;
    }

    @Override
    protected CueViewModel newInstance() {
        return new DmxCueViewModel();
    }

    @Override
    public void copyFrom(DmxCue cue, CueViewModelContext context) {
        super.copyFrom(cue, context);
        console = context.getApplicationContext().getBean(DmxControlConsoleViewController.class);
        setKeyFrames(cue.getKeyFrames());
    }

    @Override
    public void buildAnimation() {
        for (int ndx = 0; ndx < 512; ndx++) {
            values.add(new SimpleIntegerProperty());
        }

        Timeline timeline = new Timeline();
        keyFrames.stream().forEach(kf -> {
            log.debug("read key frame={}", kf);
            List<KeyValue> keyValues = new ArrayList<>();
            for (int ndx = 0; ndx < kf.getValues().length; ndx++) {
                keyValues.add(new KeyValue(values.get(kf.getValues()[ndx].getAddress() - 1),
                        kf.getValues()[ndx].getValue(),
                        Interpolator.DISCRETE));
            }
            //KeyFrame keyFrame = new KeyFrame(kf.getTime(), keyValues.toArray(new KeyValue[keyValues.size()]));
            KeyFrame keyFrame = new KeyFrame(kf.getTime(), (t) -> {
                for (int ndx = 0; ndx < kf.getValues().length; ndx++) {
                    //getValues().get(kf.getValues()[ndx].getAddress() - 1).set(kf.getValues()[ndx].getValue());
                    DmxDimmerControlViewController controller = console.getDmxControllerForAddress(kf.getValues()[ndx].getAddress());
                    if (controller != null) {
                        controller.setValue(kf.getValues()[ndx].getValue());
                    }
                }
            });
            log.debug("created key frame={}", keyFrame);
            timeline.getKeyFrames().add(keyFrame);

            // Buffer
            timeline.getKeyFrames().add(new KeyFrame(kf.getTime().add(Duration.millis(10))));
        });

        log.debug("timeline={}", timeline);

        setAnimation(timeline);
//        timeline.statusProperty().addListener((ov, oldVal, newVal) -> {
//            for (IntegerProperty value : values) {
//                System.out.print("" + value.get() + ",");
//            }
//            System.out.println();
//        });
    }

}
