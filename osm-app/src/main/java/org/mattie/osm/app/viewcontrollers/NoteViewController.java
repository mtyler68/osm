package org.mattie.osm.app.viewcontrollers;

import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.mattie.osm.app.application.ResetEvent;
import org.mattie.osm.app.events.ShowOpenedEvent;
import org.mattie.osm.app.viewmodel.NoteCueViewModel;
import org.mattie.osm.app.viewmodel.ShowViewModel;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Matt
 */
@Component
@FxmlView("NoteView.fxml")
public class NoteViewController extends AbstractViewController {

    @FXML
    public TableView noteTable;

    @FXML
    public TableColumn timeColumn;

    @FXML
    public TableColumn noteColumn;

    @FXML
    public void initialize() {
        configurePropertyValueFactory(timeColumn, "time");
        configurePropertyValueFactory(noteColumn, "note");
    }

    @EventListener
    public void handleReset(ResetEvent evt) {
        noteTable.getItems().clear();
    }

    @EventListener
    public void showOpenedHandler(ShowOpenedEvent evt) {
        noteTable.getItems().clear();
        ShowViewModel show = evt.getShowViewModel();

        // TODO: delete next line
        //configureTargets(show.getCueViewModels());
        configureCues(show.getCueViewModels(),
                ((cvm) -> cvm instanceof NoteCueViewModel),
                ((cvm) -> {
                    ((NoteCueViewModel) cvm).displayedProperty()
                            .addListener((ov, oldVal, newVal) -> {
                                if (newVal.booleanValue()) {
                                    addNote((NoteCueViewModel) cvm);
                                }
                            });
                }));
    }

    private void addNote(NoteCueViewModel cvm) {
        if (cvm.isClearNotes()) {
            noteTable.getItems().clear();
        }
        noteTable.getItems().add(0,
                new NoteViewModel(String.format("%tT", new Date()), cvm.getText()));
    }

    @Getter
    @RequiredArgsConstructor
    public static class NoteViewModel {

        private final String time;

        private final String note;
    }
}
