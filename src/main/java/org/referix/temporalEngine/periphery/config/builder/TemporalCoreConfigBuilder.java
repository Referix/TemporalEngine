package org.referix.temporalEngine.periphery.config.builder;


import org.referix.temporalEngine.periphery.config.*;
import org.referix.temporalEngine.periphery.config.factory.IntermediateConfig;

public final class TemporalCoreConfigBuilder {

    public TemporalCoreConfig build(IntermediateConfig cfg) {

        return new TemporalCoreConfig(
                new TemporalEngineConfig(
                        cfg.isEngineEnabled(),
                        cfg.getEngineMode(),
                        cfg.getEvaluationInterval(),
                        cfg.getEvaluationIntervalTicks()
                ),
                new AutosaveConfig(
                        cfg.isAutosaveEnabled(),
                        cfg.getAutosaveInterval()
                ),
                new PersistenceConfig(
                        cfg.isPersistenceEnabled(),
                        cfg.isStrictRestore()
                ),
                cfg.getPhases().stream()
                        .map(p -> new TimePhase(p.id(), p.duration()))
                        .toList()
        );
    }
}

