package org.mattie.osm.app.dmx;

import javafx.util.Duration;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Matt Tyler
 */
@Data
@NoArgsConstructor
public class DmxKeyFrame {

    private Duration time;

    private DmxKeyValue[] values;

    public DmxKeyFrame(Duration time, DmxKeyValue... values) {
        this.time = time;
        this.values = values;
    }

}
