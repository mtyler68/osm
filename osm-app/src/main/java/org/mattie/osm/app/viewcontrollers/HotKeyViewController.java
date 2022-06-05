package org.mattie.osm.app.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.rgielen.fxweaver.core.FxmlView;
import org.mattie.osm.app.events.ShowOpenedEvent;
import org.mattie.osm.app.viewmodel.ShowViewModel;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Matt
 */
@Component
@FxmlView("HotKeyView.fxml")
public class HotKeyViewController {

    @FXML
    public TableView hotKeyTable;

    @FXML
    public TableColumn keyColumn;

    @FXML
    public TableColumn descColumn;

    @FXML
    public void initialize() {
        keyColumn.setCellValueFactory(new PropertyValueFactory("hotKey"));
        descColumn.setCellValueFactory(new PropertyValueFactory("desc"));
    }

    @EventListener
    public void showOpenedHandler(ShowOpenedEvent evt) {
        ShowViewModel showViewModel = evt.getShowViewModel();
        hotKeyTable.itemsProperty().bind(showViewModel.hotKeyCueViewModelsProperty());
    }

}
