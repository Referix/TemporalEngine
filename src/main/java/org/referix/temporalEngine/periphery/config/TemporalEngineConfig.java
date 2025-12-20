package org.referix.temporalEngine.periphery.config;


import org.referix.temporalEngine.periphery.config.loader.EngineMode;

import java.time.Duration;

public record TemporalEngineConfig(
        boolean enabled,
        EngineMode mode,
        Duration evaluationInterval,
        long evaluationIntervalTicks
) {}

