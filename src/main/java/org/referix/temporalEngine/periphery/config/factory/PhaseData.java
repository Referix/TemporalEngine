package org.referix.temporalEngine.periphery.config.factory;

import java.time.Duration;
import java.util.Objects;

public final class PhaseData {

    private final String id;
    private final Duration duration; // null = infinite

    public PhaseData(String id, Duration duration) {
        this.id = Objects.requireNonNull(id, "id");
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public Duration getDuration() {
        return duration;
    }

    public boolean isInfinite() {
        return duration == null;
    }
}
