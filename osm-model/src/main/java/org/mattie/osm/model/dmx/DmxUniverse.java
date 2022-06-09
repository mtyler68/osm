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
public class DmxUniverse {

    private List<Address<Integer>> addresses;

    public DmxUniverse() {
        addresses = new ArrayList<>(512);
        for (int ndx = 0; ndx < 512; ndx++) {
            addresses.add(new Address<Integer>().setLocation(ndx + 1)
                    .setName(String.format("%d", (ndx + 1))));
        }
    }

    public Address<Integer> getAddress(int location) {
        return addresses.get(location - 1);
    }

    public RenderBuffer createRenderBuffer() {
        return new RenderBuffer<Integer>() {
            private Integer[] buffer = new Integer[512];

            @Override
            public void render(Address[] addresses, Integer[] values) {
                for (int ndx = 0; ndx < addresses.length; ndx++) {
                    buffer[(int) addresses[ndx].getLocation() - 1] = values[ndx];
                }
            }

            @Override
            public Integer[] getBuffer() {
                return buffer;
            }

            @Override
            public void clear() {
                Arrays.fill(buffer, 0);
            }

        };
    }
}
