package org.referix.temporalEngine.periphery.config;


import java.time.Duration;

public record AutosaveConfig(
        boolean enabled,
        Duration interval
) {}
