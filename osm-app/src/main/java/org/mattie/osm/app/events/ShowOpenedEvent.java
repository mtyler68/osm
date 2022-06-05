package org.mattie.osm.app.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mattie.osm.app.viewmodel.ShowViewModel;

/**
 *
 * @author Matt
 */
@RequiredArgsConstructor
public class ShowOpenedEvent {

    @Getter
    private final ShowViewModel showViewModel;
}
