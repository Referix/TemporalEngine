package org.referix.temporalEngine.api.event;


import org.referix.temporalEngine.api.phase.Phase;

import java.time.Instant;

public final class PhaseChangeEvent {

    private final Phase from;
    private final Phase to;
    private final Instant at;

    public PhaseChangeEvent(Phase from, Phase to, Instant at) {
        this.from = from;
        this.to = to;
        this.at = at;
    }

    public Phase getFrom() {
        return from;
    }

    public Phase getTo() {
        return to;
    }

    public Instant getAt() {
        return at;
    }
}

