package org.mattie.osm.app.application;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mattie.osm.app.viewmodel.MediaPlaylistCueViewModel;

/**
 *
 */
@RequiredArgsConstructor
public class MediaPlaylistLoadedEvent {

    @Getter
    private final MediaPlaylistCueViewModel playlistCueViewModel;
}
