package org.referix.temporalEngine.periphery.config.loader;


import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Map;

public class TemporalConfigLoader {

    public RawConfig load(FileConfiguration config) {

        String evaluationInterval =
                config.getString("engine.evaluation-interval");

        List<Map<?, ?>> rawPhases =
                config.getMapList("phases");

        // Приводимо Map<?, ?> → Map<String, String>
        List<Map<String, String>> phases = rawPhases.stream()
                .map(map -> (Map<String, String>) map)
                .toList();

        return new RawConfig(evaluationInterval, phases);
    }
}
