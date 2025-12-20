package org.referix.temporalEngine.periphery.config.loader;

import java.util.List;
import java.util.Map;


public record RawConfig(
        RawEngineConfig engine,
        RawAutosaveConfig autosave,
        RawPersistenceConfig persistence,
        List<Map<String, String>> phases
) {}


