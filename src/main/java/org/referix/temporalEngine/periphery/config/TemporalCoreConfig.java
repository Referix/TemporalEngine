package org.referix.temporalEngine.periphery.config;


import java.time.Duration;
import java.util.List;

public class TemporalCoreConfig {

    private final Duration evaluationInterval;
    private final List<TimePhase> phases;

    private final Long evaluationIntervalTick;

    public TemporalCoreConfig(Duration evaluationInterval,Long evaluationIntervalTick, List<TimePhase> phases) {
        this.evaluationInterval = evaluationInterval;
        this.phases = List.copyOf(phases);
        this.evaluationIntervalTick = evaluationIntervalTick;
    }

    public Duration getEvaluationInterval() {
        return evaluationInterval;
    }

    public List<TimePhase> getPhases() {
        return phases;
    }

    public Long getEvaluationIntervalTick() {
        return evaluationIntervalTick;
    }
}

