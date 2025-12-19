package org.referix.temporalEngine.bukkit.bridge;


import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.referix.temporalEngine.api.event.PhaseChangeEvent;
import org.referix.temporalEngine.api.event.PhaseEndEvent;
import org.referix.temporalEngine.api.event.PhaseStartEvent;
import org.referix.temporalEngine.api.listener.TemporalListener;
import org.referix.temporalEngine.bukkit.event.PhaseChangeBukkitEvent;
import org.referix.temporalEngine.bukkit.event.PhaseEndBukkitEvent;
import org.referix.temporalEngine.bukkit.event.PhaseStartBukkitEvent;

import java.util.Objects;

public final class TemporalBukkitBridge implements TemporalListener {

    private final Plugin plugin;

    public TemporalBukkitBridge(Plugin plugin) {
        this.plugin = Objects.requireNonNull(plugin, "plugin");
    }

    @Override
    public void onPhaseStart(PhaseStartEvent event) {
        Bukkit.getScheduler().runTask(
                plugin,
                () -> Bukkit.getPluginManager().callEvent(
                        new PhaseStartBukkitEvent(
                                event.getPhase(),
                                event.getAt()
                        )
                )
        );
    }

    @Override
    public void onPhaseEnd(PhaseEndEvent event) {
        Bukkit.getScheduler().runTask(
                plugin,
                () -> Bukkit.getPluginManager().callEvent(
                        new PhaseEndBukkitEvent(
                                event.getPhase(),
                                event.getAt()
                        )
                )
        );
    }

    @Override
    public void onPhaseChange(PhaseChangeEvent event) {
        Bukkit.getScheduler().runTask(
                plugin,
                () -> Bukkit.getPluginManager().callEvent(
                        new PhaseChangeBukkitEvent(
                                event.getFrom(),
                                event.getTo(),
                                event.getAt()
                        )
                )
        );
    }
}

