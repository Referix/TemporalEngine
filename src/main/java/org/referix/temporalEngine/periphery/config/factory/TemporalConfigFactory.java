package org.referix.temporalEngine.periphery.config.factory;

import org.referix.temporalEngine.periphery.config.loader.*;

import java.time.Duration;
import java.util.List;

public final class TemporalConfigFactory {

    public IntermediateConfig create(RawConfig raw) {

        EngineMode mode;
        try {
            mode = EngineMode.valueOf(raw.engine().mode().toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid engine.mode");
        }

        Duration evaluationInterval = parseDuration(raw.engine().evaluationInterval());
        long ticks = evaluationInterval.toMillis() / 50;

        Duration autosaveInterval = null;
        if (raw.autosave().enabled()) {
            autosaveInterval = parseDuration(raw.autosave().interval());
        }

        List<PhaseData> phases = raw.phases().stream()
                .map(this::parsePhase)
                .toList();

        return new IntermediateConfig(
                raw.engine().enabled(),
                mode,
                evaluationInterval,
                ticks,
                raw.autosave().enabled(),
                autosaveInterval,
                raw.persistence().enabled(),
                raw.persistence().strictRestore(),
                phases
        );
    }

    private PhaseData parsePhase(java.util.Map<String, String> map) {
        String id = map.get("id");
        String rawDuration = map.get("duration");

        if (id == null) {
            throw new IllegalArgumentException("Phase id is missing");
        }

        if ("infinite".equalsIgnoreCase(rawDuration)) {
            return new PhaseData(id, null);
        }

        return new PhaseData(id, parseDuration(rawDuration));
    }

    private Duration parseDuration(String raw) {
        if (raw == null) return null;

        raw = raw.trim().toLowerCase();

        if (raw.equals("infinite")) {
            return null;
        }

        // підтримка d/h/m/s
        if (raw.endsWith("d")) {
            long val = Long.parseLong(raw.substring(0, raw.length() - 1));
            return Duration.ofDays(val);
        } else if (raw.endsWith("h")) {
            long val = Long.parseLong(raw.substring(0, raw.length() - 1));
            return Duration.ofHours(val);
        } else if (raw.endsWith("m")) {
            long val = Long.parseLong(raw.substring(0, raw.length() - 1));
            return Duration.ofMinutes(val);
        } else if (raw.endsWith("s")) {
            long val = Long.parseLong(raw.substring(0, raw.length() - 1));
            return Duration.ofSeconds(val);
        } else {
            // fallback: пробуємо Duration.parse
            return Duration.parse(raw);
        }
    }

}

