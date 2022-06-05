package org.mattie.osm.app.actions;

import javafx.stage.Stage;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionProxy;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.mattie.osm.app.application.StageReadyEvent;
import org.mattie.osm.app.events.ShowOpenedEvent;
import org.mattie.osm.app.viewmodel.ShowViewModel;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *
 * @author Matt
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WindowActions {

    private Stage primaryStage;

    private GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");

    @PostConstruct
    public void init() {
        ActionMap.register(this);
    }

    @EventListener
    public void handleStageReady(StageReadyEvent evt) {
        if (this.primaryStage == null) {
            this.primaryStage = evt.stage;
        }
    }

    @ActionProxy(id = ActionId.FULLSCREEN, text = "Fullscreen", graphic = "font>FontAwesome|EXPAND")
    public void toggleFullScreen() {
        primaryStage.setFullScreen(!primaryStage.isFullScreen());
        Action action = ActionMap.action(ActionId.FULLSCREEN);
        action.setGraphic(primaryStage.isFullScreen()
                ? fontAwesome.create(FontAwesome.Glyph.COMPRESS)
                : fontAwesome.create(FontAwesome.Glyph.EXPAND));
        action.setText(primaryStage.isFullScreen() ? "Exit Fullscreen" : "Fullscreen");
    }

    @EventListener
    public void showOpenedHandler(ShowOpenedEvent evt) {
        ShowViewModel showViewModel = evt.getShowViewModel();
        primaryStage.setTitle(showViewModel.getName());
    }
}
