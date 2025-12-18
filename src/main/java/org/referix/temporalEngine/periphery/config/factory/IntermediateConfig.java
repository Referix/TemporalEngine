package org.referix.temporalEngine.periphery.config.factory;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public final class IntermediateConfig {

    private final Duration evaluationInterval;       // Duration для core
    private final long evaluationIntervalTicks;      // ticks для engine
    private final List<PhaseData> phases;            // phase durations

    public IntermediateConfig(Duration evaluationInterval, long evaluationIntervalTicks, List<PhaseData> phases) {
        this.evaluationInterval = Objects.requireNonNull(evaluationInterval, "evaluationInterval");
        this.evaluationIntervalTicks = evaluationIntervalTicks;
        this.phases = List.copyOf(phases);
    }

    public Duration getEvaluationInterval() {
        return evaluationInterval;
    }

    public long getEvaluationIntervalTicks() {
        return evaluationIntervalTicks;
    }

    public List<PhaseData> getPhases() {
        return phases;
    }
}
