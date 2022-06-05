package org.mattie.osm.app.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.mattie.osm.app.application.ResetEvent;
import org.mattie.osm.app.events.ShowOpenedEvent;
import org.mattie.osm.app.viewmodel.MediaCueViewModel;
import org.mattie.osm.app.viewmodel.ShowViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Matt Tyler
 */
@Slf4j
@Component
@FxmlView("MainView.fxml")
public class MediaScrubberListViewController extends AbstractViewController {

    @Autowired
    private FxWeaver fxWeaver;

    @FXML
    public VBox scrubberList;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        clear();
        // TODO
    }

    public void clear() {
        scrubberList.getChildren().clear();
    }

    @EventListener
    public void handleReset(ResetEvent evt) {
        clear();
    }

    @EventListener
    public void showOpenedHandler(ShowOpenedEvent evt) {
        clear();
        ShowViewModel show = evt.getShowViewModel();

        configureCues(show.getCueViewModels(),
                ((cvm) -> cvm instanceof MediaCueViewModel),
                ((cvm) -> {
                    final MediaCueViewModel mcvm = (MediaCueViewModel) cvm;

                    final Node scrubber = createScrubber(mcvm);

                    mcvm.stateProperty().addListener((ov, oldVal, newVal) -> {
                        switch (newVal) {
                            case PAUSED:
                            case PLAYING:
                                log.debug("{}: (playing or paused): {}", mcvm.getName(), mcvm);
                                if (!scrubberList.getChildren().contains(scrubber)) {
                                    scrubberList.getChildren().add(scrubber);
                                }
                                break;

                            case FINISHED:
                            case STOPPED:
                                log.debug("{}: (stopped): {}", mcvm.getName(), mcvm);
                                if (scrubberList.getChildren().contains(scrubber)) {
                                    scrubberList.getChildren().remove(scrubber);
                                }
                                break;
                        }
                    });

//                    mcvm.getMediaPlayer().statusProperty().addListener((ov, oldVal, newVal) -> {
//
//                        switch (newVal) {
//                            case PLAYING:
//                            case PAUSED:
//                                log.debug("{}: (playing or paused): {}", mcvm.getName(), mcvm);
//                                if (!scrubberList.getChildren().contains(scrubber)) {
//                                    scrubberList.getChildren().add(scrubber);
//                                }
//                                break;
//                            case HALTED:
//                                log.debug("{}: (halted): {}", mcvm.getName(), mcvm);
//                                break;
//                            case STOPPED:
//                                log.debug("{}: (stopped): {}", mcvm.getName(), mcvm);
//                                if (scrubberList.getChildren().contains(scrubber)) {
//                                    scrubberList.getChildren().remove(scrubber);
//                                }
//                                break;
//                        }
//                    });
                }));
    }

    private Node createScrubber(MediaCueViewModel cvm) {

        FxControllerAndView<MediaScrubberViewController, Node> controllerAndView
                = fxWeaver.load(MediaScrubberViewController.class);
        controllerAndView.getController().configure(cvm);
        return controllerAndView.getView().get();
    }

}
