package org.mattie.osm.app.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.paint.Color;
import net.rgielen.fxweaver.core.FxmlView;
import org.controlsfx.control.ToggleSwitch;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionUtils;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.mattie.osm.app.DmxController;
import org.mattie.osm.app.actions.ActionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Matt
 */
@Component
@FxmlView("MainToolbar.fxml")
public class MainToolbarController extends AbstractViewController {

    @Autowired
    private DmxController dmxController;

    @FXML
    public Button playPauseButton;

    @FXML
    public Button fullscreenButton;

    @FXML
    public Button openButton;

    @FXML
    public Button stopButton;

    @FXML
    public Button nextCueButton;

    @FXML
    public SplitMenuButton toggleScreenButton;

    @FXML
    public Button dmxConsoleButton;

    @FXML
    public ToggleSwitch dmxToggle;

    @FXML
    public Button allOnButton;

    @FXML
    public Button allOffButton;

    private GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");

    @FXML
    public void initialize() {

        Glyph playGlyph = fontAwesome.create(FontAwesome.Glyph.PLAY).color(Color.GREEN);

        // FULLSCREEN
        configureGraphicOnlyActionButton(ActionId.FULLSCREEN, fullscreenButton);

        // OPEN SHOW
        configureActionButton(ActionId.OPEN_SHOW, openButton);

        // PLAY/PAUSE
        configureGraphicOnlyActionButton(ActionId.PLAY_PAUSE, playPauseButton)
                .setGraphic(playGlyph);

        // STOP
        configureGraphicOnlyActionButton(ActionId.STOP, stopButton);

        // NEXT CUE
        Action nextCueAction = ActionMap.action(ActionId.NEXT_CUE);
        ActionUtils.configureButton(nextCueAction, nextCueButton);

        // Screen Toggle
        configureGraphicOnlyActionButton(ActionId.TOGGLE_SCREEN, toggleScreenButton);

        // DMX Console Toggle
        configureGraphicOnlyActionButton(ActionId.TOGGLE_DMX_CONSOLE, dmxConsoleButton);

        dmxToggle.selectedProperty().bindBidirectional(dmxController.dmxEnabledProperty());

        configureActionButton(ActionId.DMX_ALL_ON, allOnButton);
        configureActionButton(ActionId.DMX_ALL_OFF, allOffButton);
    }

}
