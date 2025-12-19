package org.referix.temporalEngine.bukkit.event;


import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.referix.temporalEngine.api.phase.Phase;

import java.time.Instant;

public final class PhaseChangeBukkitEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Phase from;
    private final Phase to;
    private final Instant at;

    public PhaseChangeBukkitEvent(
            Phase from,
            Phase to,
            Instant at
    ) {
        this.from = from;
        this.to = to;
        this.at = at;
    }

    public Phase getFrom() {
        return from;
    }

    public Phase getTo() {
        return to;
    }

    public Instant getAt() {
        return at;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
