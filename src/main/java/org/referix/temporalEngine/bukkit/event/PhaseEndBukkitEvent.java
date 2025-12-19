package org.referix.temporalEngine.bukkit.event;


import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.referix.temporalEngine.api.phase.Phase;

import java.time.Instant;

public final class PhaseEndBukkitEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Phase phase;
    private final Instant endedAt;

    public PhaseEndBukkitEvent(Phase phase, Instant endedAt) {
        this.phase = phase;
        this.endedAt = endedAt;
    }

    public Phase getPhase() {
        return phase;
    }

    public Instant getEndedAt() {
        return endedAt;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

