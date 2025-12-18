package org.referix.temporalEngine.engine;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.referix.temporalEngine.core.TemporalCore;

import java.time.Instant;
import java.util.Objects;

public final class TemporalEngineRunner {

    private final Plugin plugin;
    private final TemporalCore core;
    private final long tickInterval;

    private BukkitTask task;

    public TemporalEngineRunner(
            Plugin plugin,
            TemporalCore core,
            long tickInterval
    ) {
        this.plugin = Objects.requireNonNull(plugin, "plugin");
        this.core = Objects.requireNonNull(core, "core");
        this.tickInterval = tickInterval;
    }

    public void start() {
        if (task != null) return;

        plugin.getLogger().info("TemporalEngineRunner started, tickInterval=" + tickInterval + " ticks");

        task = Bukkit.getScheduler().runTaskTimer(
                plugin,
                () -> {
                    try {
                        core.evaluate(Instant.now());
                        plugin.getLogger().info("Current phase: " + core.getCurrentPhase().getId());
                    } catch (Exception e) {
                        plugin.getLogger().severe("Error in TemporalCore evaluation: " + e.getMessage());
                        e.printStackTrace();
                    }
                },
                0L,
                tickInterval
        );
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
            plugin.getLogger().info("TemporalEngineRunner stopped");
        }
    }

}

