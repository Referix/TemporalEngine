package org.referix.temporalEngine.periphery.config.builder;

import org.referix.temporalEngine.periphery.config.TemporalCoreConfig;
import org.referix.temporalEngine.periphery.config.TimePhase;
import org.referix.temporalEngine.periphery.config.factory.IntermediateConfig;
import org.referix.temporalEngine.periphery.config.factory.PhaseData;

import java.util.List;
import java.util.Objects;

public final class TemporalCoreConfigBuilder {

    public TemporalCoreConfig build(IntermediateConfig config) {
        Objects.requireNonNull(config, "config");

        // Тут вже готовий Duration з factory
        var evaluationInterval = config.getEvaluationInterval();
        var evaluationIntervalTick =  config.getEvaluationIntervalTicks();
        // Кожна фаза вже має Duration / null
        List<TimePhase> phases = config.getPhases().stream()
                .map(this::buildPhase)
                .toList();

        return new TemporalCoreConfig(evaluationInterval, evaluationIntervalTick, phases);
    }

    private TimePhase buildPhase(PhaseData data) {
        return new TimePhase(
                data.getId(),
                data.getDuration() // просто беремо готову Duration / null
        );
    }
}
