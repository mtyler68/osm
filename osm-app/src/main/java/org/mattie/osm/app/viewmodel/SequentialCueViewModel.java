package org.mattie.osm.app.viewmodel;

import java.util.concurrent.atomic.AtomicInteger;
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

        AtomicInteger cuePoint = new AtomicInteger(0);

        getChildren().stream()
                .forEach(vm -> {
                    final int cueNdx = cuePoint.getAndIncrement();
                    vm.buildAnimation();

                    vm.animationProperty().addListener((ov, oldVal, newVal) -> {
                        Animation ani = (Animation) vm.getAnimation().get();

                        ani.statusProperty().addListener((aov, aoldVal, anewVal) -> {
                            switch (anewVal) {
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
                        animation.getChildren().add(ani);
                    });

                });

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
