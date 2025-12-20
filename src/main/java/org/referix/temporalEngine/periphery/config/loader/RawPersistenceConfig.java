package org.referix.temporalEngine.periphery.config.loader;


public record RawPersistenceConfig(
        boolean enabled,
        boolean strictRestore
) {}

