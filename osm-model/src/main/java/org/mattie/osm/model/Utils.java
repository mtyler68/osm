package org.mattie.osm.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.util.ServiceLoader;

/**
 *
 * @author Matthew Tyler
 */
public final class Utils {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.findAndRegisterModules(); // Loads JSR 310

        ServiceLoader<Cue> loaders = ServiceLoader.load(Cue.class);
        loaders.stream()
                .map(clz -> new NamedType(clz.type()))
                .forEach(objectMapper::registerSubtypes);

    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}
