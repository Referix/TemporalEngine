package org.referix.temporalEngine.engine.persistence;

import java.util.Optional;

public interface TemporalStateStore {

    Optional<TemporalSnapshot> load();

    void save(TemporalSnapshot snapshot);

    static TemporalStateStore noop() {
        return new TemporalStateStore() {
            @Override
            public Optional<TemporalSnapshot> load() {
                return Optional.empty();
            }

            @Override
            public void save(TemporalSnapshot snapshot) {
                // no-op
            }
        };
    }
}

