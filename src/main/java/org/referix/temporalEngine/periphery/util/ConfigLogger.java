package org.referix.temporalEngine.periphery.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.referix.temporalEngine.periphery.config.TemporalCoreConfig;
import org.referix.temporalEngine.periphery.config.TimePhase;

public final class ConfigLogger {

    private ConfigLogger() {}

    public static void log(JavaPlugin plugin, TemporalCoreConfig config) {

        plugin.getLogger().info("=== TemporalEngine Config Loaded ===");
        plugin.getLogger().info(
                "Evaluation interval: " + config.getEvaluationInterval()
        );

        for (TimePhase phase : config.getPhases()) {
            plugin.getLogger().info(
                    "Phase: " + phase.getId() +
                            " | duration: " +
                            (phase.isInfinite() ? "infinite" : phase.getDuration())
            );
        }
    }
}

