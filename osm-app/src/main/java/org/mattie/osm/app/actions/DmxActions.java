package org.mattie.osm.app.actions;

import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionProxy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *
 * @author Matt
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DmxActions {

    @PostConstruct
    public void init() {
        ActionMap.register(this);
    }

    @ActionProxy(id = ActionId.DMX_TOGGLE, text = "DMX Off")
    public void toggleDmx(ActionEvent evt) {
        ToggleButton button = (ToggleButton) evt.getSource();
        log.info("dmxToggle(), selected={}", button.isSelected());

        Action action = ActionMap.action(ActionId.DMX_TOGGLE);
        action.setText(button.isSelected() ? "DMX On" : "DMX Off");
    }
}
