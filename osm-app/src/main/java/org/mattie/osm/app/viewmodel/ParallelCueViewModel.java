package org.mattie.osm.app.viewmodel;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.util.Duration;
import org.mattie.osm.model.ParallelCue;

/**
 *
 * @author Matthew Tyler
 */
public class ParallelCueViewModel extends GroupCueViewModel<ParallelCue> {

    @Override
    public String getSimpleType() {
        return "Parallel";
    }

    public void buildAnimation() {
        ParallelTransition animation = new ParallelTransition();

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
        return getChildren().stream()
                .map(c -> c.calculateDuration())
                .max((o1, o2) -> o1.compareTo(o2))
                .orElse(Duration.ZERO)
                .add(getDelay());
    }

    @Override
    public Class<ParallelCue> getCueClass() {
        return ParallelCue.class;
    }

    @Override
    protected CueViewModel newInstance() {
        return new ParallelCueViewModel();
    }

}
