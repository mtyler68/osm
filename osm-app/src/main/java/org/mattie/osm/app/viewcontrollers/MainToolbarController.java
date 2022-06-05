package org.mattie.osm.app.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.ToggleButton;
import net.rgielen.fxweaver.core.FxmlView;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionUtils;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.mattie.osm.app.actions.ActionId;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Matt
 */
@Component
@FxmlView("MainToolbar.fxml")
public class MainToolbarController {

    @FXML
    public SplitMenuButton playButton;

    @FXML
    public ToggleButton dmxToggleButton;

    @FXML
    public Button fullscreenButton;

    @FXML
    public Button openButton;

    @FXML
    public Button stopButton;

    @FXML
    public Button pauseButton;

    @FXML
    public Button nextCueButton;

    @FXML
    public SplitMenuButton toggleScreenButton;

    private GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");

    @FXML
    public void initialize() {

        Action dmxToggleAction = ActionMap.action(ActionId.DMX_TOGGLE);
        ActionUtils.configureButton(dmxToggleAction, dmxToggleButton);

        fullscreenButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        Action fullscreenAction = ActionMap.action(ActionId.FULLSCREEN);
        ActionUtils.configureButton(fullscreenAction, fullscreenButton);

        Action openShowAction = ActionMap.action(ActionId.OPEN_SHOW);
        ActionUtils.configureButton(openShowAction, openButton);

        // PLAY
        Action playAction = ActionMap.action(ActionId.PLAY);
        ActionUtils.configureButton(playAction, playButton);

//        Glow playGlowEffect = new Glow();
//        playButton.setEffect(playGlowEffect);
//        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500),
//                new KeyValue(playGlowEffect.levelProperty(), 0.6, Interpolator.EASE_BOTH)),
//                new KeyFrame(Duration.millis(500),
//                        new KeyValue(playGlowEffect.levelProperty(), 0.0, Interpolator.EASE_BOTH)));
//        timeline.setCycleCount(Animation.INDEFINITE);
//        timeline.play();
        // STOP
        Action stopAction = ActionMap.action(ActionId.STOP);
        stopAction.setDisabled(true);
        ActionUtils.configureButton(stopAction, stopButton);

        // PAUSE
        Action pauseAction = ActionMap.action(ActionId.PAUSE);
//        pauseAction.setGraphic(fontAwesome.create(FontAwesome.Glyph.EXPAND));
        pauseAction.setDisabled(true);
        ActionUtils.configureButton(pauseAction, pauseButton);

        Action nextCueAction = ActionMap.action(ActionId.NEXT_CUE);
        ActionUtils.configureButton(nextCueAction, nextCueButton);

        // Screen Toggle
        toggleScreenButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        Action toggleScreenAction = ActionMap.action(ActionId.TOGGLE_VIDEO);
        ActionUtils.configureButton(toggleScreenAction, toggleScreenButton);
    }
}
