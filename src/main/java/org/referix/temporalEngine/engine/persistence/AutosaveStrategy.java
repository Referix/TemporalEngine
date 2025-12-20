package org.referix.temporalEngine.engine.persistence;

import java.time.Instant;

public interface AutosaveStrategy {

    boolean shouldSave(Instant now);

    static AutosaveStrategy disabled() {
        return now -> false;
    }

}
