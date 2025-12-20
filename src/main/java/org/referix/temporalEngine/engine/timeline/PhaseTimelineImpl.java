package org.referix.temporalEngine.engine.timeline;


import org.referix.temporalEngine.api.phase.Phase;
import org.referix.temporalEngine.api.phase.PhaseTimeline;
import org.referix.temporalEngine.core.TemporalCore;
import org.referix.temporalEngine.periphery.config.TimePhase;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public final class PhaseTimelineImpl implements PhaseTimeline {

    private final TemporalCore core;

    public PhaseTimelineImpl(TemporalCore core) {
        this.core = core;
    }

    @Override
    public Phase getCurrentPhase() {
        return toApiPhase(core.getCurrentPhase());
    }

    @Override
    public Optional<Phase> getPreviousPhase() {
        return core.getPreviousPhase()
                .map(this::toApiPhase);
    }

    @Override
    public Optional<Phase> getNextPhase() {
        return core.getNextPhase()
                .map(this::toApiPhase);
    }

    @Override
    public Duration getElapsedInPhase(Instant now) {
        return core.getElapsedInCurrentPhase(now);
    }

    @Override
    public Optional<Duration> getRemainingInPhase(Instant now) {
        return core.getRemainingInCurrentPhase(now);
    }

    private Phase toApiPhase(TimePhase phase) {
        return new Phase(phase.id(), phase.duration());
    }
}

