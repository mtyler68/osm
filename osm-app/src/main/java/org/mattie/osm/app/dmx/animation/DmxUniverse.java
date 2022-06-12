package org.mattie.osm.app.dmx.animation;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Matt Tyler
 */
public class DmxUniverse {

    private int universe = 1;

    private List<IntegerProperty> values = new ArrayList<>(512);

    public DmxUniverse() {
        for (int ndx = 0; ndx < 512; ndx++) {
            values.add(new SimpleIntegerProperty(0));
        }
    }

    public IntegerProperty getValueProperty(int address) {
        return values.get(address - 1);
    }

    /**
     * Sets DMX value based on integer range 0..100 inclusive.
     *
     * @param address
     * @param centile
     */
    public void setCentileValue(int address, int centile) {
        int value = (int) (255d * (double) centile / 100d);
        setValue(address, value);
    }

    /**
     * Sets absolute DMX value (0..255)
     *
     * @param address
     * @param value
     */
    public void setValue(int address, int value) {
        getValueProperty(address).set(value);
    }
}
