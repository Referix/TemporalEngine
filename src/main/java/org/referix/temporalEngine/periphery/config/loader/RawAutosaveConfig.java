package org.referix.temporalEngine.periphery.config.loader;

public record RawAutosaveConfig(
        boolean enabled,
        String interval
) {}
