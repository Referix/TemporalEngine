package org.referix.temporalEngine.periphery.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.referix.temporalEngine.periphery.config.TemporalCoreConfig;
import org.referix.temporalEngine.periphery.config.TimePhase;

public final class ConfigLogger {

    private ConfigLogger() {}

    public static void log(JavaPlugin plugin, TemporalCoreConfig config) {

        plugin.getLogger().info("=== TemporalEngine Config Loaded ===");
        plugin.getLogger().info(
                "Evaluation interval: " + config.engine().evaluationInterval()
        );

        for (TimePhase phase : config.phases()) {
            plugin.getLogger().info(
                    "Phase: " + phase.id() +
                            " | duration: " +
                            (phase.isInfinite() ? "infinite" : phase.duration())
            );
        }
    }
}

