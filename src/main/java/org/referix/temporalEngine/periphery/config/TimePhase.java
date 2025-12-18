package org.referix.temporalEngine.periphery.config;


import java.time.Duration;

public class TimePhase {

    private final String id;
    private final Duration duration; // null = infinite

    public TimePhase(String id, Duration duration) {
        this.id = id;
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

