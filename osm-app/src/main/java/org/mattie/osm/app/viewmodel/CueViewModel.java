package org.mattie.osm.app.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Consumer;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.mattie.osm.model.Cue;
import org.mattie.osm.model.TriggerType;
import org.springframework.context.ApplicationEventPublisher;

/**
 *
 * @author Matt
 */
@Slf4j
@ToString(of = {"name", "state", "trigger", "delay"})
public abstract class CueViewModel<C extends Cue> {

    private static final ServiceLoader<CueViewModel> loaders = ServiceLoader.load(CueViewModel.class);

    @Setter
    @Getter(AccessLevel.PROTECTED)
    private ApplicationEventPublisher eventPublisher;

    @Getter
    @Setter
    private TriggerType trigger = TriggerType.MANUAL;

    @Getter
    private Duration delay;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String desc;

    @Setter
    @Getter
    private String id;

    @Setter
    @Getter
    private String hotKey;

    private ObjectProperty<Runnable> onFinished = new SimpleObjectProperty<>();

    private ObjectProperty<CueState> state = new SimpleObjectProperty<>(CueState.READY);

    private ObjectProperty<Animation> animation = new SimpleObjectProperty<>();

    private StringProperty currentTime = new SimpleStringProperty("00:00:00.0");

    private StringProperty duration = new SimpleStringProperty("00:00:00.0");

    private List<Consumer> viewTargets = new ArrayList<>();

    public abstract String getSimpleType();

    public abstract Class<C> getCueClass();

    protected abstract CueViewModel newInstance();

    /**
     * Implementations use calls to this method to build the internal animation
     * as well as other asynchronous and animated instances.
     */
    public abstract void buildAnimation();

    public void addViewTarget(Consumer target) {
        viewTargets.add(target);
    }

    public void notifyViewTargets() {
        viewTargets.stream()
                .forEach(vt -> vt.accept(this));
    }

    public void setDelay(Duration delay) {
        this.delay = new Duration(delay.toMillis()) {
            @Override
            public String toString() {
                double seconds = toSeconds();
                String format = "%,1.0f s";
                if (seconds - (double) ((int) seconds) > 0) {
                    format = "%,1.1f s";
                }
                return String.format(format, seconds);
            }

        };
    }

    public ReadOnlyObjectProperty<CueState> stateProperty() {
        return state;
    }

    public void setState(CueState newState) {
        state.set(newState);
    }

    public CueState getState() {
        return state.get();
    }

    public ReadOnlyObjectProperty<Runnable> onFinishedProperty() {
        return onFinished;
    }

    public void setOnFinished(Runnable onFinished) {
        this.onFinished.set(onFinished);
    }

    public Runnable getOnFinished() {
        return onFinished.get();
    }

    public ReadOnlyObjectProperty<Animation> animationProperty() {
        return animation;
    }

    public void setAnimation(Animation newAnimation) {

        Animation ani = newAnimation;

        if (getDelay().greaterThan(Duration.ZERO)) {
            ani = new SequentialTransition(
                    new PauseTransition(getDelay()),
                    ani);
        }

        ani.currentTimeProperty().addListener((ov, oldVal, newVal) -> {
            setCurrentTime(formatDuration(newVal));
        });
        ani.setOnFinished((evt) -> finished());

        ani.statusProperty().addListener((ov, oldVal, newVal) -> {
            switch (newVal) {
                case RUNNING:
                    setState(CueState.PLAYING);
                    break;
            }
        });

        animation.set(ani);

        setDuration(formatDuration(calculateDuration()));

    }

    public Optional<Animation> getAnimation() {
        return Optional.ofNullable(animation.get());
    }

    protected Duration calculateDuration() {
        if (getAnimation().isEmpty()) {
            return Duration.ZERO;
        }

        return getAnimation().get().getTotalDuration();
    }

    public ReadOnlyStringProperty durationProperty() {
        return duration;
    }

    public void setDuration(String newDuration) {
        duration.set(newDuration);
    }

    public String getDuration() {
        return duration.get();
    }

    public ReadOnlyStringProperty currentTimeProperty() {
        return currentTime;
    }

    public void setCurrentTime(String newCurrentTime) {
        this.currentTime.set(newCurrentTime);
    }

    public String getCurrentTime() {
        return currentTime.get();
    }

    protected void publishEvent(Object evt) {
        eventPublisher.publishEvent(evt);
    }

    /**
     * Cues should call the finished method when done with animation or
     * execution.
     */
    protected void finished() {
        log.info("{}: finished(): {}", getName(), this);
        setState(CueState.FINISHED);

        Runnable _onFinished = getOnFinished();
        if (_onFinished != null) {
            log.info("{}: (running onFinished): {}", getName(), this);
            _onFinished.run();
        }
    }

    protected Duration fromTimeDuration(java.time.Duration source) {
        return Duration.millis(source.toMillis());
    }

    protected java.time.Duration toTimeDuration(Duration source) {
        return java.time.Duration.ofMillis((long) source.toMillis());
    }

    protected String formatDuration(Duration source) {
        java.time.Duration d = java.time.Duration.ofMillis((int) source.toMillis());
        return String.format("%02d:%02d:%02d.%d", d.toHoursPart(), d.toMinutesPart(), d.toSecondsPart(), d.toMillisPart() / 100);
    }

    public void copyFrom(C cue, CueViewModelContext context) {
        setEventPublisher(context.getEventPublisher());
        setDesc(cue.getDesc());
        setId(cue.getId());
        setName(cue.getName());
        setDelay(fromTimeDuration(cue.getDelay()));
        setTrigger(cue.getTrigger());

        if (cue.getTrigger() == TriggerType.HOT_KEY) {
            setHotKey((String) cue.getTriggerProps().get("hotKey"));
        }
    }

    public static CueViewModel createCueViewModel(Cue cue, CueViewModelContext context) {
        log.info("createCueViewModel(): cue={}", cue);
        return loaders.stream()
                .filter(p -> {
                    log.info("createCueViewModel(): p.getCueClass={}, cue.class={}", p.get().getCueClass(), cue.getClass());
                    return p.get().getCueClass().equals(cue.getClass());
                })
                .map(p -> {
                    CueViewModel instance = p.get().newInstance();
                    log.info("createCueViewModel(): p.newInstance={}", instance);
                    return instance;
                })
                .map(vm -> {
                    vm.copyFrom(cue, context);
                    log.info("createCueViewModel(): vm={}, cue={}", vm, cue);
                    return vm;
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No CueViewModel registered for cue class: " + cue.getClass()));
    }

    public void resetCue() {
        setState(CueState.READY);
    }

    public void play() {
        log.debug("{}: play(): {}", getName(), this);

        getAnimation().ifPresent(ani -> {
            switch (ani.getStatus()) {
                case STOPPED:
                    setState(CueState.PLAYING);
                    ani.playFromStart();
                    break;

                case PAUSED:
                    setState(CueState.PLAYING);
                    ani.play();
                    break;
            }
        });

    }

    public void stop() {
        log.debug("{}: stop(): {}", getName(), this);

        getAnimation().ifPresent(ani -> {
            switch (ani.getStatus()) {
                case PAUSED:
                case RUNNING:
                    setState(CueState.STOPPED);
                    ani.stop();
                    break;
            }
        });

    }

    public void pause() {
        log.debug("{}: pause(): {}", getName(), this);

        getAnimation().ifPresent(ani -> {
            switch (ani.getStatus()) {
                case PAUSED:
                    // No need to pause again
                    break;
                case STOPPED:
                    // Why pause when stopped?
                    break;
                case RUNNING:
                    setState(CueState.PAUSED);
                    ani.pause();
                    break;
            }
        });

    }

    public void jumpTo(Duration time) {
        log.debug("{}: jumpTo(): {}", getName(), this);

        getAnimation().ifPresent(ani -> {
            switch (ani.getStatus()) {
//                case STOPPED:
//                    ani.jumpTo(time);
//                    setState(CueState.PAUSED);
//                    play();
//                    break;

                case PAUSED:
                    ani.jumpTo(time);
                    break;

                case RUNNING:
                    ani.jumpTo(time);
                    break;
            }
        });
    }

    /**
     * Called by GroupCueViewModel on children. Embedded animation controls are
     * important since the very outside group will control the Animation
     * instance which controls all child animations. However, some cues that
     * control media or other cue actions outside the animation need to be
     * controlled. That's where the embedded commands come in handy.
     */
    public void embeddedPause() {
        log.debug("{}: embeddedPause(): {}", getName(), this);

        switch (getState()) {
            case PLAYING:
                setState(CueState.PAUSED);
                break;
        }
    }

    public void embeddedPlay() {
        if (getState() == CueState.PAUSED) {
            setState(CueState.PLAYING);
        }
    }

    public void embeddedStop() {
        if (getState() == CueState.PLAYING || getState() == CueState.PAUSED) {
            setState(CueState.STOPPED);
        }
    }

    public void embeddedJumpTo(Duration time) {

    }

    public boolean isFadeableOut() {
        return false;
    }

    public void fadeOut(Duration dur) {

    }
}
