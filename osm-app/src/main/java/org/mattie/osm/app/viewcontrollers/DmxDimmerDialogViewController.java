package org.mattie.osm.app.viewcontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Matt Tyler
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@FxmlView("DmxDimmerDialogView.fxml")
public class DmxDimmerDialogViewController {

    @FXML
    public TextField addressTextField;

    @FXML
    public TextField nameTextField;

    @FXML
    public Button okButton;

    @FXML
    public Button cancelButton;

    @Getter
    private boolean okPressed;

    @Setter
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        cancelButton.setOnAction((evt) -> {
            stage.close();
        });

        okButton.setOnAction((evt) -> {
            okPressed = true;
            stage.close();
        });
    }

}
