package org.referix.temporalEngine.periphery.config;


import java.time.Duration;

public record TimePhase(
        String id,
        Duration duration // null = infinite
) {
    public boolean isInfinite() {
        return duration == null;
    }
}




