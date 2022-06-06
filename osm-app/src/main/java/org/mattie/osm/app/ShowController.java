package org.mattie.osm.app;

import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionProxy;
import org.mattie.osm.app.actions.ActionId;
import org.mattie.osm.app.application.StageReadyEvent;
import org.mattie.osm.app.events.ShowOpenedEvent;
import org.mattie.osm.app.viewmodel.CueState;
import org.mattie.osm.app.viewmodel.CueViewModel;
import org.mattie.osm.app.viewmodel.ShowViewModel;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 *
 */
@Slf4j
@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ShowController {

    private ShowViewModel showViewModel;

    private static Map<KeyCode, String> keyCodeToActionIdMap = new HashMap<>();

    @PostConstruct
    public void init() {
        ActionMap.register(this);

        // Configure KeyCode to actions
        keyCodeToActionIdMap.put(KeyCode.SPACE, ActionId.PLAY_PAUSE);
        keyCodeToActionIdMap.put(KeyCode.END, ActionId.NEXT_CUE);
    }

    @ActionProxy(id = ActionId.PLAY, text = "Play", graphic = "font>FontAwesome|PLAY")
    public void playAction() {
        log.debug("playAction()");
        play();
    }

    @ActionProxy(id = ActionId.STOP, text = "Stop", graphic = "font>FontAwesome|STOP")
    public void stopAction() {
        log.debug("stopAction()");
        stop();
    }

    @ActionProxy(id = ActionId.PAUSE, text = "Pause", graphic = "font>FontAwesome|PAUSE")
    public void pauseAction() {
        log.debug("pauseAction()");
        pause();
    }

    @ActionProxy(id = ActionId.PLAY_PAUSE, text = "Play/Pause")
    public void playPauseAction() {
        log.debug("playPauseAction()");
        playPause();
    }

    @ActionProxy(id = ActionId.NEXT_CUE, text = "Next Cue", graphic = "font>FontAwesome|STEP_FORWARD")
    public void nextCueAction() {
        log.debug("nextCueAction()");
        nextCue();
    }

    public void setShowState(CueState state) {
        ActionMap.action(ActionId.PLAY).setDisabled(state == CueState.PLAYING);
        ActionMap.action(ActionId.STOP).setDisabled(state == CueState.STOPPED);
        ActionMap.action(ActionId.PAUSE).setDisabled(
                state == CueState.PAUSED || state == CueState.STOPPED || state == CueState.WAITING);
    }

    @EventListener
    public void stageReadyHandler(StageReadyEvent event) {
        Stage stage = event.stage;

        stage.addEventFilter(KeyEvent.KEY_PRESSED, (evt) -> {
            log.debug("key: {},{},{}", evt.getText(), evt.getCode().name(), evt.getCharacter());

            Action act = null;
            if (keyCodeToActionIdMap.containsKey(evt.getCode())) {
                act = ActionMap.action(keyCodeToActionIdMap.get(evt.getCode()));
            } else if (ActionMap.action(evt.getCode().name()) != null) {
                act = ActionMap.action(evt.getCode().name());
            }

            if (act != null) {
                act.handle(new ActionEvent());
            } else {
                //getShowManager().playHotKey(evt.getText().toLowerCase());
            }

        });
    }

    @EventListener
    public void showOpenedHandler(ShowOpenedEvent evt) {
        this.showViewModel = evt.getShowViewModel();
        log.debug("showOpenedHandler(): showViewModel={}", showViewModel);

        showViewModel.stateProperty().addListener((ov, oldVal, newVal) -> {
            setShowState(newVal);
        });
    }

    public void play() {
        if (showViewModel != null) {
            showViewModel.play();
        }
    }

    public void stop() {
        if (showViewModel != null) {
            showViewModel.stop();
        }
    }

    public void pause() {
        if (showViewModel != null) {
            showViewModel.pause();
        }
    }

    public void playPause() {
        if (showViewModel != null) {
            switch (showViewModel.getState()) {
                case PLAYING:
                    ActionMap.action(ActionId.PAUSE).handle(new ActionEvent());
                    break;
                case WAITING:
                case PAUSED:
                case STOPPED:
                    ActionMap.action(ActionId.PLAY).handle(new ActionEvent());
                    break;
            }
        }
    }

    private void nextCue() {
        if (showViewModel != null) {
            showViewModel.playNext();
        }
    }

    public void playFrom(CueViewModel cueViewModel) {
        log.debug("playFrom(): {}", cueViewModel);
        if (showViewModel != null) {
            showViewModel.playFrom(cueViewModel);
        }
    }
}
