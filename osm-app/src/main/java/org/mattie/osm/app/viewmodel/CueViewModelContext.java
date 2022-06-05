package org.mattie.osm.app.viewmodel;

import lombok.Data;
import lombok.experimental.Accessors;
import org.mattie.osm.model.Cue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;

/**
 *
 * @author Matt
 */
@Data
@Accessors(chain = true)
public class CueViewModelContext {

    private Cue cue;

    private ApplicationEventPublisher eventPublisher;

    private ApplicationContext applicationContext;

}
