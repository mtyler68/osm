package org.mattie.osm.app.application;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Matt
 */
@RequiredArgsConstructor
public class NewRichTextPagesEvent {

    @Getter
    private final List<String> pages;
}
