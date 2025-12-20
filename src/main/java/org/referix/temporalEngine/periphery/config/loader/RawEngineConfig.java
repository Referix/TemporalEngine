package org.referix.temporalEngine.periphery.config.loader;


public record RawEngineConfig(
        boolean enabled,
        String mode,
        String evaluationInterval
) {}

