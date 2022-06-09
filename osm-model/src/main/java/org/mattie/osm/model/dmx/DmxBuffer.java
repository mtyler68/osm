package org.mattie.osm.model.dmx;

import lombok.Getter;

/**
 *
 * @author Matt Tyler
 */
public class DmxBuffer {

    @Getter
    private int[] dataBuffer = new int[512];

    public void render(int address, int data) {
        dataBuffer[address - 1] = data;
    }
}
