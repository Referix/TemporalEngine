package org.referix.temporalEngine.api.listener;


import org.referix.temporalEngine.api.event.PhaseChangeEvent;
import org.referix.temporalEngine.api.event.PhaseEndEvent;
import org.referix.temporalEngine.api.event.PhaseStartEvent;

public interface TemporalListener {

    default void onPhaseStart(PhaseStartEvent event) {}

    default void onPhaseEnd(PhaseEndEvent event) {}

    default void onPhaseChange(PhaseChangeEvent event) {}
}

