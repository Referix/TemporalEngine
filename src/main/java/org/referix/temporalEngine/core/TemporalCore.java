package org.referix.temporalEngine.core;



import org.referix.temporalEngine.periphery.config.TemporalCoreConfig;
import org.referix.temporalEngine.periphery.config.TimePhase;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public final class TemporalCore {

    private final List<TimePhase> phases;

    private int currentPhaseIndex;
    private Instant phaseStartedAt;

    public TemporalCore(TemporalCoreConfig config, Instant startTime) {
        Objects.requireNonNull(config, "config");
        Objects.requireNonNull(startTime, "startTime");

        this.phases = config.getPhases();
        this.currentPhaseIndex = 0;
        this.phaseStartedAt = startTime;
    }

    /**
     * Основний метод оцінки.
     * Викликається engine'ом періодично.
     */
    public void evaluate(Instant now) {
        Objects.requireNonNull(now, "now");

        TimePhase current = getCurrentPhase();

        // infinite фаза ніколи не закінчується
        if (current.isInfinite()) {
            return;
        }

        Duration elapsed = Duration.between(phaseStartedAt, now);

        if (elapsed.compareTo(current.getDuration()) >= 0) {
            advanceToNextPhase(now);
        }
    }

    private void advanceToNextPhase(Instant now) {
        if (currentPhaseIndex >= phases.size() - 1) {
            return; // остання фаза
        }

        currentPhaseIndex++;
        phaseStartedAt = now;
    }

    public TimePhase getCurrentPhase() {
        return phases.get(currentPhaseIndex);
    }

    public Instant getPhaseStartedAt() {
        return phaseStartedAt;
    }

    public int getCurrentPhaseIndex() {
        return currentPhaseIndex;
    }
}

