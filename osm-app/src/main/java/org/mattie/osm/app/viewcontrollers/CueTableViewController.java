package org.mattie.osm.app.viewcontrollers;

import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Duration;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxmlView;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionProxy;
import org.controlsfx.control.action.ActionUtils;
import org.mattie.osm.app.ShowController;
import org.mattie.osm.app.actions.ActionId;
import org.mattie.osm.app.application.ResetEvent;
import org.mattie.osm.app.events.ShowOpenedEvent;
import org.mattie.osm.app.viewmodel.CueState;
import org.mattie.osm.app.viewmodel.CueViewModel;
import org.mattie.osm.app.viewmodel.GroupCueViewModel;
import org.mattie.osm.app.viewmodel.MediaCueViewModel;
import org.mattie.osm.app.viewmodel.ShowViewModel;
import org.springframework.beans.factory.annotation.Autowired;
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
@FxmlView("CueTableView.fxml")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CueTableViewController {

    @Autowired
    private ShowController showController;

    @FXML
    public TreeTableView tableView;

    @FXML
    public TreeTableColumn nameColumn;

    @FXML
    public TreeTableColumn stateColumn;

    @FXML
    public TreeTableColumn typeColumn;

    @FXML
    public TreeTableColumn triggerColumn;

    @FXML
    public TreeTableColumn descColumn;

    @FXML
    public TreeTableColumn delayColumn;

    @FXML
    public TreeTableColumn lengthColumn;

    @FXML
    public TreeTableColumn currentColumn;

    @FXML
    public MenuItem playFromSelectedMenuItem;

    @FXML
    public ProgressBar mediaProgressBar;

    @FXML
    public SplitMenuButton fadeButton;

    private MediaCueViewModel lastMediaCueViewModel;

    private ChangeListener<Duration> mediaChangeListener;

    @PostConstruct
    public void init() {
        ActionMap.register(this);
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory("name"));
        stateColumn.setCellValueFactory(new TreeItemPropertyValueFactory("state"));
        typeColumn.setCellValueFactory(new TreeItemPropertyValueFactory("simpleType"));
        triggerColumn.setCellValueFactory(new TreeItemPropertyValueFactory("trigger"));
        descColumn.setCellValueFactory(new TreeItemPropertyValueFactory("desc"));
        delayColumn.setCellValueFactory(new TreeItemPropertyValueFactory("delay"));
        lengthColumn.setCellValueFactory(new TreeItemPropertyValueFactory("duration"));
        currentColumn.setCellValueFactory(new TreeItemPropertyValueFactory("currentTime"));

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        final Action playFromSelectedAction = ActionMap.action(ActionId.PLAY_FROM_SELECTED);
        ActionUtils.configureMenuItem(playFromSelectedAction, playFromSelectedMenuItem);

        tableView.getSelectionModel().selectedItemProperty().addListener((ov, oldVal, newVal) -> {
            TreeItem node = (TreeItem) newVal;
            if (node != null && node.getParent().getParent() == null) {
                log.debug("cue is top-level");
                playFromSelectedAction.setDisabled(false);
            } else {
                playFromSelectedAction.setDisabled(true);
            }
        });

        mediaChangeListener = (ov, oldVal, newVal) -> {
            double length = newVal.toSeconds();
            if (lastMediaCueViewModel != null) {
                length = lastMediaCueViewModel.getAnimation().get().getTotalDuration().toSeconds();
            }
            mediaProgressBar.setProgress(newVal.toSeconds() / length);
        };

        mediaProgressBar.setOnMousePressed((evt) -> {
            log.debug("mediaProgressBar.mousePressed(): {}, mediaProgressBar.width={}", evt, mediaProgressBar.getWidth());
            if (lastMediaCueViewModel != null) {
                double totalDur = lastMediaCueViewModel.getAnimation().get().getTotalDuration().toSeconds();
                double time = evt.getX() / mediaProgressBar.getWidth();
                lastMediaCueViewModel.jumpTo(Duration.seconds(time * totalDur));
            }
        });

        fadeButton.setOnAction(new FadeEventHandler(5));
    }

    @ActionProxy(id = ActionId.PLAY_FROM_SELECTED, text = "Play from selected")
    public void playFromSelectedAction() {
        log.debug("playFromSelectedAction()");
        TreeItem node = (TreeItem) tableView.getSelectionModel().getSelectedItem();
        showController.playFrom((CueViewModel) node.getValue());
    }

    @EventListener
    public void showOpenedHandler(ShowOpenedEvent evt) {
        ShowViewModel showViewModel = evt.getShowViewModel();

        TreeItem root = new TreeItem("root");
        tableView.setRoot(root);

        loadCueTable(root, showViewModel.getCueViewModels());

        showViewModel.currentCueViewModel().addListener((ov, oldVal, newVal) -> {
            if (lastMediaCueViewModel != null) {
                lastMediaCueViewModel.getAnimation().get().currentTimeProperty().removeListener(mediaChangeListener);
            }
            lastMediaCueViewModel = null;

            if (newVal instanceof MediaCueViewModel) {
                MediaCueViewModel cvm = (MediaCueViewModel) newVal;
                cvm.getAnimation().get().currentTimeProperty().addListener(mediaChangeListener);
                lastMediaCueViewModel = cvm;
            }
        });
    }

    public void loadCueTable(TreeItem parent, List<CueViewModel> viewModels) {
        viewModels.stream()
                .forEach(cueViewModel -> {
                    TreeItem node = new TreeItem(cueViewModel);
                    node.setExpanded(true);
                    parent.getChildren().add(node);

                    if (cueViewModel instanceof GroupCueViewModel) {
                        loadCueTable(node, ((GroupCueViewModel) cueViewModel).getChildren());
                    }

                    cueViewModel.stateProperty().addListener((ov, oldVal, newVal) -> {
                        switch ((CueState) newVal) {
                            case PLAYING:
                                tableView.getSelectionModel().select(node);
                                break;
                        }
                    });
                });
    }

    @EventListener
    public void handleReset(ResetEvent evt) {
        tableView.getSelectionModel().clearSelection();
    }

    private void fade(double dur) {
        if (lastMediaCueViewModel != null && lastMediaCueViewModel.getState() == CueState.PLAYING) {
            lastMediaCueViewModel.fadeOut(Duration.seconds(dur));
        }
    }

    @RequiredArgsConstructor
    public class FadeEventHandler implements EventHandler<ActionEvent> {

        private final double duration;

        @Override
        public void handle(ActionEvent t) {
            fade(duration);
        }

    }
}
