package org.mattie.osm.app.viewcontrollers;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionUtils;
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

    protected Action configureActionButton(String actionId, ButtonBase button) {
        Action action = ActionMap.action(actionId);
        ActionUtils.configureButton(action, button);
        return action;
    }

    protected Action configureGraphicOnlyActionButton(String actionId, ButtonBase button) {
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        Action action = ActionMap.action(actionId);
        ActionUtils.configureButton(action, button);
        return action;
    }
}
