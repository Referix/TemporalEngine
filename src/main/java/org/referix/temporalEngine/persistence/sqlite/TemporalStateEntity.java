package org.referix.temporalEngine.persistence.sqlite;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "temporal_state")
public class TemporalStateEntity {

    @DatabaseField(id = true)
    private int id = 1; // singleton row

    @DatabaseField(canBeNull = false)
    private String phaseId;

    @DatabaseField(canBeNull = false)
    private long phaseStartedAt;

    public TemporalStateEntity() {
        // ORMLite
    }

    public TemporalStateEntity(String phaseId, long phaseStartedAt) {
        this.id = 1;
        this.phaseId = phaseId;
        this.phaseStartedAt = phaseStartedAt;
    }

    public String getPhaseId() {
        return phaseId;
    }

    public long getPhaseStartedAt() {
        return phaseStartedAt;
    }
}

