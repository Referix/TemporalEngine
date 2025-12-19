package org.referix.temporalEngine.api.phase;


import java.time.Duration;
import java.util.Optional;

public final class Phase {

    private final String id;
    private final Duration duration; // null = infinite

    public Phase(String id, Duration duration) {
        this.id = id;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public Optional<Duration> getDuration() {
        return Optional.ofNullable(duration);
    }

    public boolean isInfinite() {
        return duration == null;
    }

    @Override
    public String toString() {
        return "Phase{" +
                "id='" + id + '\'' +
                ", duration=" + duration +
                '}';
    }
}

