package org.mattie.osm.app;

import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionProxy;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
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

    private GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");

    private Glyph playGlyph;

    private Glyph pauseGlyph;

    @PostConstruct
    public void init() {
        ActionMap.register(this);

        // Configure KeyCode to actions
        keyCodeToActionIdMap.put(KeyCode.SPACE, ActionId.PLAY_PAUSE);
        keyCodeToActionIdMap.put(KeyCode.END, ActionId.NEXT_CUE);

        playGlyph = fontAwesome.create(FontAwesome.Glyph.PLAY).color(Color.GREEN);
        pauseGlyph = fontAwesome.create(FontAwesome.Glyph.PAUSE);

        //ActionMap.action(ActionId.PLAY).setGraphic(playGlyph);
    }

    @ActionProxy(id = ActionId.STOP, text = "Stop", graphic = "font>FontAwesome|STOP")
    public void stopAction() {
        log.debug("stopAction()");
        stop();
    }

    @ActionProxy(id = ActionId.PLAY_PAUSE, text = "Play/Pause", graphic = "font>FontAwesome|PLAY")
    public void playPauseAction() {
        log.debug("playPauseAction()");
        playPause();
    }

    @ActionProxy(id = ActionId.NEXT_CUE, text = "Next Cue", graphic = "font>FontAwesome|STEP_FORWARD")
    public void nextCueAction() {
        log.debug("nextCueAction()");
        nextCue();
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

            ActionMap.action(ActionId.STOP).setDisabled(newVal == CueState.STOPPED);

            switch (newVal) {
                case PLAYING:
                    ActionMap.action(ActionId.PLAY_PAUSE).setGraphic(pauseGlyph);
                    ActionMap.action(ActionId.PLAY_PAUSE).setText("Pause");
                    break;

                case WAITING:
                case STOPPED:
                case PAUSED:
                    ActionMap.action(ActionId.PLAY_PAUSE).setGraphic(playGlyph);
                    ActionMap.action(ActionId.PLAY_PAUSE).setText("Play");
                    break;
            }
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
                    pause();
                    break;
                case WAITING:
                case PAUSED:
                case STOPPED:
                    play();
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
