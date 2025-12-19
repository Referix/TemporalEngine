package org.referix.temporalEngine.api;


import org.referix.temporalEngine.api.listener.TemporalListener;
import org.referix.temporalEngine.api.phase.Phase;
import org.referix.temporalEngine.api.phase.PhaseTimeline;

public interface TemporalAPI {

    Phase getCurrentPhase();

    PhaseTimeline getTimeline();

    void registerListener(TemporalListener listener);

    void unregisterListener(TemporalListener listener);
}

