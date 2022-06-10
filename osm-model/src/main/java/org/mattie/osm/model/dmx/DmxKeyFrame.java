package org.mattie.osm.model.dmx;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.util.Duration;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Matt Tyler
 */
@Data
@NoArgsConstructor
public class DmxKeyFrame {

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private Duration time;

    @Setter(AccessLevel.NONE)
    private java.time.Duration legacyTime;

    private DmxKeyValue[] values;

    public DmxKeyFrame(Duration time, DmxKeyValue... values) {
        setTime(time);
        this.values = values;
    }

    public void setTime(Duration time) {
        this.time = time;
        legacyTime = java.time.Duration.ofMillis((long) time.toMillis());
    }

    public void setLegacyTime(java.time.Duration legacyTime) {
        this.legacyTime = legacyTime;
        time = Duration.millis(legacyTime.toMillis());
    }

}
