package org.referix.temporalEngine.engine.persistence;

import java.util.Optional;

public interface TemporalStateStore {

    Optional<TemporalSnapshot> load();

    void save(TemporalSnapshot snapshot);
}

