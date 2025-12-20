package org.referix.temporalEngine.persistence.sqlite;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import org.referix.temporalEngine.engine.persistence.TemporalSnapshot;
import org.referix.temporalEngine.engine.persistence.TemporalStateStore;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;

public final class SqliteTemporalStateStore implements TemporalStateStore {

    private final Dao<TemporalStateEntity, Integer> dao;

    public SqliteTemporalStateStore(ConnectionSource source) {
        try {
            this.dao = DaoManager.createDao(source, TemporalStateEntity.class);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to initialize TemporalState DAO", e);
        }
    }

    @Override
    public Optional<TemporalSnapshot> load() {
        try {
            TemporalStateEntity entity = dao.queryForId(1);
            if (entity == null) {
                return Optional.empty();
            }

            return Optional.of(new TemporalSnapshot(
                    entity.getPhaseId(),
                    Instant.ofEpochMilli(entity.getPhaseStartedAt())
            ));
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to load TemporalSnapshot", e);
        }
    }

    @Override
    public void save(TemporalSnapshot snapshot) {
        try {
            TemporalStateEntity entity = new TemporalStateEntity(
                    snapshot.phaseId(),
                    snapshot.phaseStartedAt().toEpochMilli()
            );

            dao.createOrUpdate(entity);
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save TemporalSnapshot", e);
        }
    }
}
