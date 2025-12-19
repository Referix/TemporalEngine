package org.referix.temporalEngine.api.event;


import org.referix.temporalEngine.api.phase.Phase;

import java.time.Instant;

public final class PhaseEndEvent {

    private final Phase phase;
    private final Instant at;

    public PhaseEndEvent(Phase phase, Instant at) {
        this.phase = phase;
        this.at = at;
    }

    public Phase getPhase() {
        return phase;
    }

    public Instant getAt() {
        return at;
    }
}

