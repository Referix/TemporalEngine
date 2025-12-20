package org.referix.temporalEngine.periphery.config;


public record PersistenceConfig(
        boolean enabled,
        boolean strictRestore
) {}
