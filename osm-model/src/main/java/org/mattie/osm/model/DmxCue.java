package org.mattie.osm.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.mattie.osm.model.dmx.DmxKeyFrame;

/**
 *
 * @author Matt Tyler
 */
@Accessors(chain = true)
@ToString(callSuper = true)
@Data
public class DmxCue extends Cue {

    private List<DmxKeyFrame> keyFrames = new ArrayList<>();

    public DmxCue add(DmxKeyFrame... frames) {
        keyFrames.addAll(Arrays.asList(frames));
        return this;
    }
}
