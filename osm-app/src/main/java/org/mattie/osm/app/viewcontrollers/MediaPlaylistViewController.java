package org.mattie.osm.app.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import net.rgielen.fxweaver.core.FxmlView;
import org.mattie.osm.app.application.MediaPlaylistLoadedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Matt
 */
@Component
@FxmlView("MediaPlaylistView.fxml")
public class MediaPlaylistViewController extends AbstractViewController {

    @FXML
    private TableView playlistTable;

    @FXML
    public TableColumn nameColumn;

    @FXML
    public TableColumn stateColumn;

    @FXML
    public void initialize() {
        configurePropertyValueFactory(nameColumn, "name");
        configurePropertyValueFactory(stateColumn, "state");
    }

    @EventListener
    public void mediaPlaylistLoadedHandler(MediaPlaylistLoadedEvent evt) {
        playlistTable.getItems().clear();

        evt.getPlaylistCueViewModel().getMediaViewModels().stream()
                .forEach(mvm -> playlistTable.getItems().add(mvm));
    }

}
