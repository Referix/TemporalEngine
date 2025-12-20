package org.referix.temporalEngine.core;



import org.referix.temporalEngine.periphery.config.TemporalCoreConfig;
import org.referix.temporalEngine.periphery.config.TimePhase;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class TemporalCore {

    private final List<TimePhase> phases;

    private int currentPhaseIndex;
    private Instant phaseStartedAt;

    public TemporalCore(TemporalCoreConfig config, Instant startTime) {
        Objects.requireNonNull(config, "config");
        Objects.requireNonNull(startTime, "startTime");

        this.phases = config.phases();
        this.currentPhaseIndex = 0;
        this.phaseStartedAt = startTime;
    }

    public void evaluate(Instant now) {
        Objects.requireNonNull(now, "now");

        TimePhase current = getCurrentPhase();

        // infinite фаза ніколи не закінчується
        if (current.isInfinite()) {
            return;
        }

        Duration elapsed = Duration.between(phaseStartedAt, now);

        if (elapsed.compareTo(current.duration()) >= 0) {
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

    public Optional<TimePhase> getPreviousPhase() {
        if (currentPhaseIndex <= 0) {
            return Optional.empty();
        }
        return Optional.of(phases.get(currentPhaseIndex - 1));
    }

    public Optional<TimePhase> getNextPhase() {
        if (currentPhaseIndex >= phases.size() - 1) {
            return Optional.empty();
        }
        return Optional.of(phases.get(currentPhaseIndex + 1));
    }

    public Duration getElapsedInCurrentPhase(Instant now) {
        return Duration.between(phaseStartedAt, now);
    }

    public Optional<Duration> getRemainingInCurrentPhase(Instant now) {
        TimePhase current = getCurrentPhase();

        if (current.isInfinite()) {
            return Optional.empty();
        }

        Duration elapsed = getElapsedInCurrentPhase(now);
        Duration remaining = current.duration().minus(elapsed);

        return remaining.isNegative()
                ? Optional.of(Duration.ZERO)
                : Optional.of(remaining);
    }


    // ОБОВʼЯЗКОВО для persistence
    public Instant getPhaseStartedAt() {
        return phaseStartedAt;
    }

    // ОБОВʼЯЗКОВО для restore
    public void restorePhase(String phaseId, Instant startedAt) {
        Objects.requireNonNull(phaseId, "phaseId");
        Objects.requireNonNull(startedAt, "startedAt");

        for (int i = 0; i < phases.size(); i++) {
            TimePhase phase = phases.get(i);
            if (phase.id().equals(phaseId)) {
                this.currentPhaseIndex = i;
                this.phaseStartedAt = startedAt;
                return;
            }
        }

        throw new IllegalStateException(
                "Cannot restore TemporalCore: unknown phaseId = " + phaseId
        );
    }



}

