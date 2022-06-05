package org.mattie.osm.app.viewmodel;

import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;
import org.mattie.osm.model.SequentialCue;

/**
 *
 * @author Matthew Tyler
 */
public class SequentialCueViewModel extends GroupCueViewModel<SequentialCue> {

    public void buildAnimation() {
        SequentialTransition animation = new SequentialTransition();

        getChildren().stream()
                .map(vm -> {
                    vm.buildAnimation();
                    Animation ani = (Animation) vm.getAnimation().get();

                    ani.statusProperty().addListener((ov, oldVal, newVal) -> {
                        switch (newVal) {
                            case PAUSED:
                                vm.setState(CueState.PAUSED);
                                break;
                            case RUNNING:
                                vm.setState(CueState.PLAYING);
                                break;
                            case STOPPED:
                                vm.setState(CueState.STOPPED);
                                break;
                        }
                    });

                    return ani;
                })
                .forEach(ani -> animation.getChildren().add(ani));

        setAnimation(animation);

    }

    @Override
    protected Duration calculateDuration() {
        return Duration.millis(getChildren().stream()
                .mapToDouble(c -> c.calculateDuration().toMillis())
                .sum()
        ).add(getDelay());
    }

    @Override
    public String getSimpleType() {
        return "Sequential";
    }

    @Override
    public Class<SequentialCue> getCueClass() {
        return SequentialCue.class;
    }

    @Override
    protected CueViewModel newInstance() {
        return new SequentialCueViewModel();
    }
}
