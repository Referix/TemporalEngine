package org.referix.temporalEngine.periphery.config.factory;

import java.time.Duration;

public final class DurationParser {

    public static Duration parse(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Duration is null");
        }

        if (value.endsWith("s")) {
            return Duration.ofSeconds(Long.parseLong(value.replace("s", "")));
        }

        if (value.endsWith("ms")) {
            return Duration.ofMillis(Long.parseLong(value.replace("ms", "")));
        }

        if (value.endsWith("m")) {
            return Duration.ofMinutes(Long.parseLong(value.replace("m", "")));
        }

        if (value.endsWith("d")) {
            return Duration.ofDays(Long.parseLong(value.replace("d", "")));
        }

        throw new IllegalArgumentException("Invalid duration: " + value);
    }

    public static Duration parseOrNull(String value) {
        if ("infinite".equalsIgnoreCase(value)) {
            return null;
        }
        return parse(value);
    }
}
