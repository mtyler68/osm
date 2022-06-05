package org.mattie.osm.app.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.mattie.osm.model.Cue;

/**
 *
 */
public abstract class DisplayableCueViewModel<C extends Cue> extends CueViewModel<C> {

    private BooleanProperty displayed = new SimpleBooleanProperty(false);

    public ReadOnlyBooleanProperty displayedProperty() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed.set(displayed);
    }

    public boolean isDisplayed() {
        return displayed.get();
    }
}
