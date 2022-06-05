package org.mattie.osm.app.application;

import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Matt
 */
public class ClearEvent extends ApplicationEvent {

    public ClearEvent(Object source) {
        super(source);
    }

}
