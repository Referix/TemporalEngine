package org.referix.temporalEngine.periphery.config.factory;


import java.time.Duration;

public record PhaseData(
        String id,
        Duration duration // null = infinite
) {}

