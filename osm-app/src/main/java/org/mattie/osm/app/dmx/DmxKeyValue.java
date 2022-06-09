package org.mattie.osm.app.dmx;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Matt Tyler
 */
@Data
@NoArgsConstructor
public class DmxKeyValue {

    private int address;

    /**
     * Range 0..255. Interpolated to percent (0..100) for dimmers.
     */
    private int value;

    public DmxKeyValue(int address, int value) {
        this.address = address;
        this.value = value;
    }

}
