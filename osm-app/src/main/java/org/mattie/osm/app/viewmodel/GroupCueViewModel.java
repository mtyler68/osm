package org.mattie.osm.app.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.mattie.osm.model.GroupCue;
import org.mattie.osm.model.TriggerType;

/**
 *
 * @author Matthew Tyler
 */
@Slf4j
@ToString(callSuper = true)
public abstract class GroupCueViewModel<C extends GroupCue> extends CueViewModel<C> {

    @Getter
    @Setter
    private List<CueViewModel> children = new ArrayList<>();

    @Override
    public void copyFrom(C cue, CueViewModelContext context) {
        super.copyFrom(cue, context);

        setChildren(cue.getCues().stream()
                .map(c -> CueViewModel.createCueViewModel(c, context))
                .map(c -> {
                    c.setTrigger(TriggerType.AUTO_START);
                    return c;
                })
                .collect(Collectors.toList()));
    }

    @Override
    public void resetCue() {
        super.resetCue();
        getChildren().stream()
                .forEach(cvm -> cvm.resetCue());
    }

//    @Override
//    public void pause() {
//        embeddedPause();
//        super.pause();
//    }
//
//    @Override
//    public void play() {
//        embeddedPlay();
//        super.play();
//    }
//
//    @Override
//    public void stop() {
//        embeddedStop();
//        super.stop();
//    }
    @Override
    public void embeddedPause() {
        log.debug("{}: embeddedPause(): {}", getName(), this);
        getChildren().stream()
                .forEach(c -> c.embeddedPause());
        super.embeddedPause();
    }

    @Override
    public void embeddedPlay() {
        log.debug("{}: embeddedPlay(): {}", getName(), this);
        getChildren().stream()
                .forEach(c -> c.embeddedPlay());
        super.embeddedPlay();
    }

    @Override
    public void embeddedStop() {
        log.debug("{}: embeddedStop(): {}", getName(), this);
        getChildren().stream()
                .forEach(c -> c.embeddedStop());
        super.embeddedStop();
    }
}
