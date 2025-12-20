package org.referix.temporalEngine.engine.persistence;

import java.time.Instant;

public record TemporalSnapshot(
        String phaseId,
        Instant phaseStartedAt
) {}

