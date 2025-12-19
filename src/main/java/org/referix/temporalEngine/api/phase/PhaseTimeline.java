package org.referix.temporalEngine.api.phase;


import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public interface PhaseTimeline {

    Phase getCurrentPhase();

    Optional<Phase> getPreviousPhase();

    Optional<Phase> getNextPhase();

    Duration getElapsedInPhase(Instant now);

    Optional<Duration> getRemainingInPhase(Instant now);
}

