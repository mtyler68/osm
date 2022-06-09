package org.mattie.osm.model.dmx;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author Matt Tyler
 */
@Data
@Accessors(chain = true)
public class Address<T> {

    private String name;

    private T location;
}
