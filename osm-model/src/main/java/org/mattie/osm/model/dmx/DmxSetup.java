package org.mattie.osm.model.dmx;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author Matt Tyler
 */
@Data
@Accessors(chain = true)
public class DmxSetup {

    private List<DmxChannel> channels;
}
