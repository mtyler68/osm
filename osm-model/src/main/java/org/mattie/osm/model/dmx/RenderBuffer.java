package org.mattie.osm.model.dmx;

/**
 *
 * @author Matt Tyler
 */
public interface RenderBuffer<T> {

    void render(Address[] addresses, T[] values);

    T[] getBuffer();

    void clear();
}
