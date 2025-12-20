package org.referix.temporalEngine.engine.persistence;

import java.time.Duration;
import java.time.Instant;

public final class FixedIntervalAutosave implements AutosaveStrategy {

    private final Duration interval;
    private Instant lastSave = Instant.EPOCH;

    public FixedIntervalAutosave(Duration interval) {
        this.interval = interval;
    }

    @Override
    public boolean shouldSave(Instant now) {
        if (Duration.between(lastSave, now).compareTo(interval) >= 0) {
            lastSave = now;
            return true;
        }
        return false;
    }
}

