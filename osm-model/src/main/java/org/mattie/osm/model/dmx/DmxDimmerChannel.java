package org.mattie.osm.model.dmx;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author Matt Tyler
 */
@Data
@Accessors(chain = true)
public class DmxDimmerChannel extends DmxChannel {

    public DmxDimmerChannel() {
        setChannelType(Type.DIMMER);
    }
}
