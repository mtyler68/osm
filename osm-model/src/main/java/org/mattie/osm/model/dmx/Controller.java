package org.mattie.osm.model.dmx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author Matt Tyler
 */
@Data
@Accessors(chain = true)
public class Controller {

    private List<DmxChannel> channels = new ArrayList<>();

    public Controller add(DmxChannel... chan) {
        channels.addAll(Arrays.asList(chan));
        return this;
    }
}
