package org.referix.temporalEngine.periphery.config.loader;

import org.bukkit.configuration.file.FileConfiguration;


import java.util.List;
import java.util.Map;

public final class TemporalConfigLoader {

    public RawConfig load(FileConfiguration config) {

        RawEngineConfig engine = new RawEngineConfig(
                config.getBoolean("engine.enabled"),
                config.getString("engine.mode"),
                config.getString("engine.evaluation-interval")
        );

        RawAutosaveConfig autosave = new RawAutosaveConfig(
                config.getBoolean("autosave.enabled"),
                config.getString("autosave.interval")
        );

        RawPersistenceConfig persistence = new RawPersistenceConfig(
                config.getBoolean("persistence.enabled"),
                config.getBoolean("persistence.strict-restore")
        );

        List<Map<String, String>> phases =
                config.getMapList("phases").stream()
                        .map(m -> (Map<String, String>) m)
                        .toList();

        return new RawConfig(engine, autosave, persistence, phases);
    }
}


