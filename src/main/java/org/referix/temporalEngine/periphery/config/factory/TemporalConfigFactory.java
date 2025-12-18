package org.referix.temporalEngine.periphery.config.factory;

import org.referix.temporalEngine.periphery.config.loader.RawConfig;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class TemporalConfigFactory {

    public IntermediateConfig create(RawConfig raw) {
        Objects.requireNonNull(raw, "raw");

        // 1. Parse evaluationInterval
        Duration evaluationInterval = parseDuration(raw.getEvaluationInterval());

        // 2. Convert Duration → ticks
        long evaluationIntervalTicks = durationToTicks(evaluationInterval);

        // 3. Parse phases
        List<PhaseData> phases = raw.getPhases().stream()
                .map(map -> new PhaseData(
                        map.get("id"),
                        parseDurationOrNull(map.get("duration"))
                ))
                .toList();

        return new IntermediateConfig(
                evaluationInterval,
                evaluationIntervalTicks,
                phases
        );
    }

    // ===== HELPER METHODS =====

    private Duration parseDuration(String raw) {
        if (raw == null) throw new IllegalArgumentException("Duration cannot be null");
        raw = raw.trim().toLowerCase();

        if (raw.equals("infinite")) {
            return null;
        }

        // regex: (\d+)([smhd])
        var matcher = java.util.regex.Pattern.compile("(\\d+)([smhd])").matcher(raw);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid duration format: " + raw);
        }

        long value = Long.parseLong(matcher.group(1));
        String unit = matcher.group(2);

        return switch (unit) {
            case "s" -> Duration.ofSeconds(value);
            case "m" -> Duration.ofMinutes(value);
            case "h" -> Duration.ofHours(value);
            case "d" -> Duration.ofDays(value);
            default -> throw new IllegalArgumentException("Unknown time unit: " + unit);
        };
    }

    private Duration parseDurationOrNull(String raw) {
        if (raw == null) return null;
        return parseDuration(raw);
    }

    private long durationToTicks(Duration duration) {
        if (duration == null) return Long.MAX_VALUE; // infinite → effectively "never"
        return duration.getSeconds() * 20; // 20 ticks per second
    }
}
