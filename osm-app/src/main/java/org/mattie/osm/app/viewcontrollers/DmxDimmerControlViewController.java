package org.mattie.osm.app.viewcontrollers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.mattie.osm.model.dmx.DmxChannel;
import org.mattie.osm.model.dmx.DmxDimmerChannel;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Matt
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@FxmlView("DmxDimmerControlView.fxml")
public class DmxDimmerControlViewController {

    @FXML
    public Node root;

    @FXML
    public Slider slider;

    @FXML
    public TextField valueField;

    @FXML
    public Button onButton;

    @FXML
    public Button offButton;

    @FXML
    public TextArea nameText;

    @FXML
    public Hyperlink deleteLink;

    @FXML
    public CheckBox keyFrameCheck;

    @FXML
    public Label addressLabel;

    @FXML
    public Hyperlink editLink;

    @Getter
    private int address;

    private GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");

    private IntegerProperty value = new SimpleIntegerProperty(0);

    public IntegerProperty valueProperty() {
        return value;
    }

    public void setValue(int value) {
        this.value.set(value);
    }

    public int getValue() {
        return value.get();
    }

    public void setAddress(int address) {
        this.address = address;
        addressLabel.setText(Integer.toString(address));
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {

        onButton.setOnAction(evt -> setValue(100));
        offButton.setOnAction(evt -> setValue(0));

        Bindings.bindBidirectional(valueField.textProperty(), value, new StringConverter<Number>() {
            @Override
            public String toString(Number t) {
                return Integer.toString(t.intValue());
            }

            @Override
            public Number fromString(String string) {
                return Integer.parseInt(string);
            }
        });

        Bindings.bindBidirectional(slider.valueProperty(), value);

        editLink.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        editLink.setGraphic(fontAwesome.create(FontAwesome.Glyph.EDIT).size(9));

        deleteLink.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        deleteLink.setGraphic(fontAwesome.create(FontAwesome.Glyph.TRASH).size(9));
    }

    public DmxChannel toChannel() {
        return new DmxDimmerChannel()
                .setAddresses(new int[]{getAddress()})
                .setName(nameText.getText());
    }

    public void turnOn() {
        setValue(100);
    }

    public void turnOff() {
        setValue(0);
    }

}
