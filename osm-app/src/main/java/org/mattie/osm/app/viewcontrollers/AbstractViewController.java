package org.mattie.osm.app.viewcontrollers;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.mattie.osm.app.viewmodel.CueViewModel;
import org.mattie.osm.app.viewmodel.GroupCueViewModel;

/**
 *
 */
public abstract class AbstractViewController {

    protected void configurePropertyValueFactory(TableColumn colum, String propName) {
        colum.setCellValueFactory(new PropertyValueFactory(propName));
    }

    protected void configurePropertyValueFactory(TreeTableColumn colum, String propName) {
        colum.setCellValueFactory(new PropertyValueFactory(propName));
    }

    protected void configureCues(List<CueViewModel> cueViewModels,
            Predicate<CueViewModel> filter, Consumer<CueViewModel> processor) {
        cueViewModels.stream()
                .filter(cvm -> filter.test(cvm))
                .forEach(cvm -> processor.accept(cvm));
        cueViewModels.stream()
                .filter(cvm -> cvm instanceof GroupCueViewModel)
                .map(cvm -> (GroupCueViewModel) cvm)
                .forEach(cvm -> configureCues(cvm.getChildren(), filter, processor));
    }
}
