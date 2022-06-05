package org.mattie.osm.app.viewcontrollers;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionProxy;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.mattie.osm.app.actions.ActionId;
import org.mattie.osm.app.application.StageReadyEvent;
import org.mattie.osm.app.events.ShowOpenedEvent;
import org.mattie.osm.app.viewmodel.MediaCueViewModel;
import org.mattie.osm.app.viewmodel.ShowViewModel;
import org.mattie.osm.model.MediaResource;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Matt
 */
@Slf4j
@Component
@FxmlView("ScreenView.fxml")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ScreenViewController extends AbstractViewController {

    private GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");

    private Stage primaryStage;

    private Stage screenStage;

    //@FXML
    private StackPane root;

    @PostConstruct
    public void init() {
        log.debug("init()");
        ActionMap.register(this);
    }

    /**
     * Initializes the controller class.
     */
//    @FXML
//    public void initialize() {
//        log.debug("initialize()");
//    }
    @EventListener
    public void handleStageReady(StageReadyEvent evt) {
        log.debug("handleStageReady()");

        if (this.primaryStage == null) {
            this.primaryStage = evt.stage;

            primaryStage.setOnCloseRequest((t) -> {
                if (screenStage != null) {
                    screenStage.close();
                }
            });

            ObservableList<Screen> screens = Screen.getScreens();
            if (screens.size() > 1) {
                Screen screen2 = screens.get(1);

                root = new StackPane();
                root.setStyle("-fx-background-color: black;");

                screenStage = new Stage(StageStyle.UNDECORATED);
                screenStage.setScene(new Scene(root));

                //screenStage.sizeToScene();
                screenStage.setWidth(screen2.getBounds().getWidth());
                screenStage.setHeight(screen2.getBounds().getHeight());
                screenStage.setX(screen2.getVisualBounds().getMinX());
                screenStage.setY(screen2.getVisualBounds().getMinY());
                screenStage.setAlwaysOnTop(true);
                screenStage.centerOnScreen();

            }
        }

    }

    @ActionProxy(text = "Show Video Screen", id = ActionId.TOGGLE_VIDEO, graphic = "font>FontAwesome|FILM")
    public void onToggleScreenAction() {
        log.debug("onToggleScreenAction()");

        if (screenStage != null) {
            Action action = ActionMap.action(ActionId.TOGGLE_VIDEO);
            if (screenStage.isShowing()) {
                screenStage.setFullScreen(false);
                screenStage.hide();
                action.setText("Show Video Screen");
            } else {
                screenStage.show();
                screenStage.setFullScreen(true);
                action.setText("Hide Video Screen");
            }
        }
    }

    @EventListener
    public void showOpenedHandler(ShowOpenedEvent evt) {
        clear();
        ShowViewModel show = evt.getShowViewModel();

        configureCues(show.getCueViewModels(),
                ((cvm) -> cvm instanceof MediaCueViewModel),
                ((cvm) -> {
                    final MediaCueViewModel mcvm = (MediaCueViewModel) cvm;

                    if (mcvm.getMediaType() != MediaResource.MediaType.AUDIO_ONLY) {
                        log.debug("showOpenedHandler(): mediaType={}", mcvm.getMediaType());

                        mcvm.stateProperty().addListener((ov, oldVal, newVal) -> {
                            switch (newVal) {
                                case PLAYING:
                                    clear();
                                    MediaView view = new MediaView(mcvm.getMediaPlayer());
                                    root.getChildren().add(view);
                                    break;
                                case PAUSED:
                                    break;
                                default:
                                    clear();
                            }
                        });
                    }
                }));
    }

    private void clear() {
        if (root != null) {
            root.getChildren().stream()
                    .forEach(c -> c.setVisible(false));
        }
    }
}
