package org.mattie.osm.app.application;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author Matt
 */
@Getter
public class NewNoteEvent extends ApplicationEvent {

    private String time;

    private String note;

    public NewNoteEvent(Object source, String time, String note) {
        super(source);
        this.time = time;
        this.note = note;
    }

}
