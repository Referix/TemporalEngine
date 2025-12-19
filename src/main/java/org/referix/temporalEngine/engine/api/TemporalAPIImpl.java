package org.referix.temporalEngine.engine.api;

import org.referix.temporalEngine.api.TemporalAPI;
import org.referix.temporalEngine.api.listener.TemporalListener;
import org.referix.temporalEngine.api.phase.Phase;
import org.referix.temporalEngine.api.phase.PhaseTimeline;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public final class TemporalAPIImpl implements TemporalAPI {

    private final PhaseTimeline timeline;
    private final Set<TemporalListener> listeners = new CopyOnWriteArraySet<>();

    public TemporalAPIImpl(PhaseTimeline timeline) {
        this.timeline = timeline;
    }

    @Override
    public Phase getCurrentPhase() {
        return timeline.getCurrentPhase();
    }

    @Override
    public PhaseTimeline getTimeline() {
        return timeline;
    }

    @Override
    public void registerListener(TemporalListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unregisterListener(TemporalListener listener) {
        listeners.remove(listener);
    }

    /* engine-only access */
    public Set<TemporalListener> listeners() {
        return listeners;
    }
}
