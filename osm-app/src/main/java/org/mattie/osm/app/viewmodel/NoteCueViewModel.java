package org.mattie.osm.app.viewmodel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.mattie.osm.model.NoteCue;

/**
 *
 * @author Matt
 */
@Slf4j
@ToString(callSuper = true, of = {"text"})
public class NoteCueViewModel extends DisplayableCueViewModel<NoteCue> {

    @Setter
    @Getter
    private String text;

    @Setter
    @Getter
    private boolean clearNotes;

    @Override
    protected CueViewModel newInstance() {
        return new NoteCueViewModel();
    }

    @Override
    public void copyFrom(NoteCue cue, CueViewModelContext context) {
        super.copyFrom(cue, context);
        setText(cue.getText());
        setClearNotes(cue.isClearNotes());
    }

    @Override
    public void buildAnimation() {
        Timeline timeline = new Timeline();
        //timeline.setDelay(getDelay());

        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, (evt) -> {
            log.debug("{}: [display note]: {}", getName(), this);
            setDisplayed(true);
        }));

        // Padding
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1), (evt) -> {
        }));

        //timeline.setOnFinished((evt) -> finished());
        setAnimation(timeline);
    }

    @Override
    public String getSimpleType() {
        return "Note";
    }

    @Override
    public Class<NoteCue> getCueClass() {
        return NoteCue.class;
    }

}
