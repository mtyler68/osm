package org.mattie.osm.app.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Matt
 */
@RequiredArgsConstructor
public class ShowRichTextPageEvent {

    @Getter
    private final int index;
}
