package org.referix.temporalEngine.engine;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.referix.temporalEngine.engine.phase.TemporalPhaseCoordinator;

import java.time.Instant;

public final class TemporalEngineRunner {

    private final Plugin plugin;
    private final TemporalPhaseCoordinator coordinator;
    private final long tickInterval;

    private BukkitTask task;

    public TemporalEngineRunner(
            Plugin plugin,
            TemporalPhaseCoordinator coordinator,
            long tickInterval
    ) {
        this.plugin = plugin;
        this.coordinator = coordinator;
        this.tickInterval = tickInterval;
    }

    public void start() {
        if (task != null) return;

        task = Bukkit.getScheduler().runTaskTimer(
                plugin,
                () -> coordinator.evaluate(Instant.now()),
                0L,
                tickInterval
        );
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }
}
