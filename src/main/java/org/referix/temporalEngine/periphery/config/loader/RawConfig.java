package org.referix.temporalEngine.periphery.config.loader;

import java.util.List;
import java.util.Map;

public class RawConfig {

    private final String evaluationInterval;
    private final List<Map<String, String>> phases;

    public RawConfig(String evaluationInterval, List<Map<String, String>> phases) {
        this.evaluationInterval = evaluationInterval;
        this.phases = phases;
    }

    public String getEvaluationInterval() {
        return evaluationInterval;
    }

    public List<Map<String, String>> getPhases() {
        return phases;
    }
}

