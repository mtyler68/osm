package org.mattie.osm.app.viewmodel;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.mattie.osm.model.RichTextCue;

/**
 *
 * @author Matthew Tyler
 */
@Slf4j
public class RichTextCueViewModel extends DisplayableCueViewModel<RichTextCue> {

    @Setter
    @Getter
    private List<String> pages = new ArrayList<>();

    @Getter
    private boolean clearPages;

    @Override
    public String getSimpleType() {
        return "Rich Text";
    }

    @Override
    public Class<RichTextCue> getCueClass() {
        return RichTextCue.class;
    }

    @Override
    public void buildAnimation() {
        Timeline timeline = new Timeline();

        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, (evt) -> {
            log.debug("{}: [display rich text]: {}", getName(), this);
            setDisplayed(true);
            setDisplayed(false);
        }));

        // Time Padding
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1), (evt) -> {
        }));

        setAnimation(timeline);
    }

    @Override
    protected CueViewModel newInstance() {
        return new RichTextCueViewModel();
    }

    @Override
    public void copyFrom(RichTextCue cue, CueViewModelContext context) {
        super.copyFrom(cue, context);
        getPages().addAll(cue.getPages());
        clearPages = cue.isClearPages();
    }
}
