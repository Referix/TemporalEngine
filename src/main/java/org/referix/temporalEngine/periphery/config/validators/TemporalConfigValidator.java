package org.referix.temporalEngine.periphery.config.validators;

import org.referix.temporalEngine.periphery.config.factory.IntermediateConfig;
import org.referix.temporalEngine.periphery.config.factory.PhaseData;

import java.util.HashSet;
import java.util.Set;

public class TemporalConfigValidator {

    public void validate(IntermediateConfig config) {
        if (config.getEvaluationInterval() == null) {
            throw new ConfigException("engine.evaluation-interval is missing");
        }

        if (config.getPhases().isEmpty()) {
            throw new ConfigException("At least one phase must be defined");
        }

        Set<String> ids = new HashSet<>();

        for (int i = 0; i < config.getPhases().size(); i++) {
            PhaseData phase = config.getPhases().get(i);

            if (phase.getId().isBlank()) {
                throw new ConfigException("Phase id is missing or blank");
            }

            if (!ids.add(phase.getId())) {
                throw new ConfigException("Duplicate phase id: " + phase.getId());
            }

            // duration must be positive or null for infinite
            if (phase.getDuration() != null && phase.getDuration().isNegative()) {
                throw new ConfigException("Phase duration must be positive: " + phase.getId());
            }

            // infinite (null) only allowed for last phase
            if (phase.getDuration() == null && i != config.getPhases().size() - 1) {
                throw new ConfigException("'infinite' duration allowed only for last phase: " + phase.getId());
            }
        }
    }
}
