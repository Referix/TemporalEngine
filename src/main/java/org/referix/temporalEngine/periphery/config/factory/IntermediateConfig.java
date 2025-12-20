package org.referix.temporalEngine.periphery.config.factory;

import org.referix.temporalEngine.periphery.config.loader.EngineMode;

import java.time.Duration;
import java.util.List;

public final class IntermediateConfig {

    private final boolean engineEnabled;
    private final EngineMode engineMode;
    private final Duration evaluationInterval;
    private final long evaluationIntervalTicks;

    private final boolean autosaveEnabled;
    private final Duration autosaveInterval;

    private final boolean persistenceEnabled;
    private final boolean strictRestore;

    private final List<PhaseData> phases;

    public IntermediateConfig(
            boolean engineEnabled,
            EngineMode engineMode,
            Duration evaluationInterval,
            long evaluationIntervalTicks,
            boolean autosaveEnabled,
            Duration autosaveInterval,
            boolean persistenceEnabled,
            boolean strictRestore,
            List<PhaseData> phases
    ) {
        this.engineEnabled = engineEnabled;
        this.engineMode = engineMode;
        this.evaluationInterval = evaluationInterval;
        this.evaluationIntervalTicks = evaluationIntervalTicks;
        this.autosaveEnabled = autosaveEnabled;
        this.autosaveInterval = autosaveInterval;
        this.persistenceEnabled = persistenceEnabled;
        this.strictRestore = strictRestore;
        this.phases = phases;
    }

    // getters ↓
    public boolean isEngineEnabled() { return engineEnabled; }
    public EngineMode getEngineMode() { return engineMode; }
    public Duration getEvaluationInterval() { return evaluationInterval; }
    public long getEvaluationIntervalTicks() { return evaluationIntervalTicks; }
    public boolean isAutosaveEnabled() { return autosaveEnabled; }
    public Duration getAutosaveInterval() { return autosaveInterval; }
    public boolean isPersistenceEnabled() { return persistenceEnabled; }
    public boolean isStrictRestore() { return strictRestore; }
    public List<PhaseData> getPhases() { return phases; }
}

