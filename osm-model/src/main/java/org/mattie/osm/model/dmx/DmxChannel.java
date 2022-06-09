package org.mattie.osm.model.dmx;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * @author Matt Tyler
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@Data
@Accessors(chain = true)
public class DmxChannel {

    public static enum Type {
        DIMMER,
        DISCRETE,
        CONTROL,
        RGB
    }

    private Type channelType = Type.DIMMER;

    @Setter(AccessLevel.NONE)
    private int[] addresses;

    private String name;

    public DmxChannel setAddresses(int... address) {
        this.addresses = address;
        return this;
    }
}
