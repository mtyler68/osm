package org.mattie.osm.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author Matthew Tyler
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@Data
@Accessors(chain = true)
public abstract class Cue {

    private String id;

    private String name;

    private String desc;

    private TriggerType trigger = TriggerType.MANUAL;

    private Map<String, Object> triggerProps = new HashMap<>();

    private Duration delay = Duration.ZERO;

    public Cue setTriggerProp(String key, Object value) {
        triggerProps.put(key, value);
        return this;
    }
}
