package org.referix.temporalEngine.bukkit.event;


import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.referix.temporalEngine.api.phase.Phase;

import java.time.Instant;

public final class PhaseStartBukkitEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Phase phase;
    private final Instant startedAt;

    public PhaseStartBukkitEvent(Phase phase, Instant startedAt) {
        this.phase = phase;
        this.startedAt = startedAt;
    }

    public Phase getPhase() {
        return phase;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

