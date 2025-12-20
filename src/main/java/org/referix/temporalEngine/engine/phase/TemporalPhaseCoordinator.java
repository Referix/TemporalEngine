package org.referix.temporalEngine.engine.phase;

import org.referix.temporalEngine.api.event.PhaseChangeEvent;
import org.referix.temporalEngine.api.event.PhaseEndEvent;
import org.referix.temporalEngine.api.event.PhaseStartEvent;
import org.referix.temporalEngine.api.phase.Phase;
import org.referix.temporalEngine.core.TemporalCore;
import org.referix.temporalEngine.engine.api.TemporalAPIImpl;
import org.referix.temporalEngine.engine.persistence.AutosaveStrategy;
import org.referix.temporalEngine.engine.persistence.TemporalSnapshot;
import org.referix.temporalEngine.engine.persistence.TemporalStateStore;
import org.referix.temporalEngine.periphery.config.TimePhase;

import java.time.Instant;

public final class TemporalPhaseCoordinator {

    private final TemporalCore core;
    private final TemporalAPIImpl api;
    private final TemporalStateStore store;
    private final AutosaveStrategy autosave;

    public TemporalPhaseCoordinator(
            TemporalCore core,
            TemporalAPIImpl api,
            TemporalStateStore store,
            AutosaveStrategy autosave
    ) {
        this.core = core;
        this.api = api;
        this.store = store;
        this.autosave = autosave;

        // restore on startup
        store.load().ifPresent(snapshot ->
                core.restorePhase(
                        snapshot.phaseId(),
                        snapshot.phaseStartedAt()
                )
        );
    }

    public void evaluate(Instant now) {
        var before = core.getCurrentPhase();

        core.evaluate(now);

        if (autosave.shouldSave(now)) {
            store.save(new TemporalSnapshot(
                    core.getCurrentPhase().id(),
                    core.getPhaseStartedAt()
            ));
        }

        var after = core.getCurrentPhase();
        if (before == after) return;

        dispatch(before, after, now);
    }

    private void dispatch(TimePhase before, TimePhase after, Instant now) {
        Phase from = new Phase(before.id(), before.duration());
        Phase to = new Phase(after.id(), after.duration());

        for (var l : api.listeners()) {
            l.onPhaseEnd(new PhaseEndEvent(from, now));
            l.onPhaseStart(new PhaseStartEvent(to, now));
            l.onPhaseChange(new PhaseChangeEvent(from, to, now));
        }
    }
}
