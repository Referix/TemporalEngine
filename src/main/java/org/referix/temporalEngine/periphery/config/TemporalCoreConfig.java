package org.referix.temporalEngine.periphery.config;


import java.util.List;

public record TemporalCoreConfig(
        TemporalEngineConfig engine,
        AutosaveConfig autosave,
        PersistenceConfig persistence,
        List<TimePhase> phases
) {}

