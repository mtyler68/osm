package org.mattie.osm.app.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.mattie.osm.app.application.ResetEvent;
import org.mattie.osm.model.Show;
import org.mattie.osm.model.TriggerType;
import org.springframework.context.ApplicationEventPublisher;

/**
 *
 * @author Matt
 */
@Slf4j
@ToString(of = {"name", "cueIndex", "state"})
public class ShowViewModel {

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private List<CueViewModel> cueViewModels = new ArrayList<>();

    private ObjectProperty<CueState> state = new SimpleObjectProperty<>(CueState.STOPPED);

    private ObjectProperty<CueViewModel> currentCueViewModel = new SimpleObjectProperty<>();

    private ApplicationEventPublisher eventPublisher;

    private AtomicInteger cueIndex = new AtomicInteger(0);

    private ObjectProperty<ObservableList<CueViewModel>> hotKeyCueViewModels
            = new SimpleObjectProperty<>(FXCollections.observableArrayList());

    private ObjectProperty<Duration> totalDuration = new SimpleObjectProperty<>();

    public ShowViewModel(Show show, CueViewModelContext context) {
        read(show, context);
    }

    public ReadOnlyObjectProperty<Duration> totalDurationProperty() {
        return totalDuration;
    }

    public void setTotalDuration(Duration totalDuration) {
        this.totalDuration.set(totalDuration);
    }

    public Duration getTotalDuration() {
        return totalDuration.get();
    }

    public ReadOnlyObjectProperty<CueViewModel> currentCueViewModel() {
        return currentCueViewModel;
    }

    public void setCurrentCueViewModel(CueViewModel currentCueViewModel) {
        this.currentCueViewModel.set(currentCueViewModel);

    }

    public CueViewModel getCurrentCueViewModel() {
        return currentCueViewModel.get();
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

    private void read(Show show, CueViewModelContext context) {
        this.eventPublisher = context.getEventPublisher();
        setCurrentCueViewModel(null);
        setName(show.getName());

        // Timeline cues
        setCueViewModels(show.getCues().stream()
                .filter(cue -> cue.getTrigger() == TriggerType.AUTO_START || cue.getTrigger() == TriggerType.MANUAL)
                .map(cue -> CueViewModel.createCueViewModel(cue, context))
                .collect(Collectors.toList()));

        // TODO: Put hot key cues in the main cue list and have show view model skip hot key and time type cues
        // Hot Key cues
        setHotKeyCueViewModels(FXCollections.observableArrayList(show.getCues().stream()
                .filter(cue -> cue.getTrigger() == TriggerType.HOT_KEY)
                .map(cue -> CueViewModel.createCueViewModel(cue, context))
                .collect(Collectors.toList())));

        // Prepare all animations
        buildAnimation();
        setTotalDuration(calculateDuration());
        setState(CueState.WAITING);
    }

    protected void buildAnimation() {
        getCueViewModels().stream()
                .forEach(vm -> vm.buildAnimation());
    }

    public ObjectProperty<ObservableList<CueViewModel>> hotKeyCueViewModelsProperty() {
        return hotKeyCueViewModels;
    }

    public ObservableList<CueViewModel> getHotKeyViewModels() {
        return hotKeyCueViewModels.get();
    }

    public void setHotKeyCueViewModels(ObservableList<CueViewModel> newHotKeyCueViewModels) {
        hotKeyCueViewModels.set(newHotKeyCueViewModels);
    }

    /**
     * Start play from the beginning of a show (because AUTO_START on the first
     * cue is ignored. Also, this will resume play from a paused position or
     * start play for a manually triggered cue.
     *
     * STOPPED -> PLAY: Plays from the beginning of the show PAUSED -> PLAY:
     * Resumes play from paused position and cue WAITING -> PLAY: Plays the
     * current cue
     *
     */
    public void play() {
        log.info("play(): {}", this);

        // TODO: Need to add listeners to cues with media that load in the background in order to update totalDuration property.
        setTotalDuration(calculateDuration());

        switch (getState()) {

            case STOPPED:
                cueIndex.set(0);
                resetCues();

            // TODO: No need to build animations after load unless cue is edited. Can remove in the future
            //buildAnimation();
            case PAUSED:
            case WAITING:
                startCurrent();
                break;
        }
    }

    public void playNext() {

        // Stop current cue first
        getCurrCueViewModel().ifPresent((cvm) -> {
            if (cvm.getState() != CueState.STOPPED || cvm.getState() != CueState.FINISHED) {
                cvm.stop();
            }
        });

        cueIndex.incrementAndGet();

        getCurrCueViewModel().ifPresentOrElse((cvm) -> {
            switch (cvm.getTrigger()) {
                case AUTO_START:
                    startCurrent();
                    break;
                case MANUAL:
                    setState(CueState.WAITING);
                    log.debug("{}: -- WAITING FOR MANUAL CUE START --: {}", cvm.getName(), cvm);
                    break;
            }
        }, () -> {
            setState(CueState.STOPPED);
            log.debug("playNext(): end of show: {}", ShowViewModel.this);
        });
    }

    public void stop() {
        log.debug("stop(): {}", this);

        switch (getState()) {
            case PAUSED:
            case PLAYING:
            case WAITING:
                getCurrCueViewModel().ifPresent(viewModel -> viewModel.stop());
                setState(CueState.STOPPED);
                break;
        }
    }

    public void pause() {
        log.debug("pause(): {}", this);
        if (cueIndex.get() < cueViewModels.size()) {
            CueViewModel currentCueViewModel = cueViewModels.get(cueIndex.get());
            setState(CueState.PAUSED);
            currentCueViewModel.pause();
        } else {
            log.debug("pause(): no cues left to pause");
        }
    }

    private Optional<CueViewModel> getCurrCueViewModel() {
        return (cueIndex.get() < cueViewModels.size() && cueIndex.get() >= 0)
                ? Optional.of(cueViewModels.get(cueIndex.get()))
                : Optional.empty();
    }

    private void resetCues() {
        getCueViewModels().stream()
                .forEach(cvm -> cvm.resetCue());
        eventPublisher.publishEvent(new ResetEvent());
    }

    private void startCurrent() {
        getCurrCueViewModel().ifPresentOrElse((cvm) -> {
            cvm.setOnFinished(this::playNext);
            setState(CueState.PLAYING); // TODO: Update the state of the action
            cvm.play();
            setCurrentCueViewModel(cvm);

        }, () -> {
            setState(CueState.STOPPED);
            setCurrentCueViewModel(null);
            log.info("startCurrent(): end of show: {}", ShowViewModel.this);
        });
    }

    public void playFrom(CueViewModel cueViewModel) {
        log.debug("playFrom(): {}", cueViewModel);
        getCueViewModels().stream()
                .filter(cvm -> cueViewModel.getId().equals(cvm.getId()))
                .forEach(cvm -> {
                    log.debug("found cue: {}", cvm);
                    stop();
                    resetCues();
                    for (int ndx = 0; ndx < getCueViewModels().size(); ndx++) {
                        if (getCueViewModels().get(ndx).getId().equals(cueViewModel.getId())) {
                            cueIndex.set(ndx);
                            startCurrent();
                            break;
                        }
                    }
                });
    }

    private Duration calculateDuration() {
        double total = getCueViewModels().stream()
                .mapToDouble(cvm -> cvm.calculateDuration().toSeconds())
                .sum();
        return Duration.seconds(total);
    }

}
