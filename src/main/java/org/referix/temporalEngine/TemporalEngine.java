package org.referix.temporalEngine;

import org.bukkit.plugin.java.JavaPlugin;
import org.referix.temporalEngine.api.TemporalAPI;
import org.referix.temporalEngine.bukkit.bridge.TemporalBukkitBridge;
import org.referix.temporalEngine.core.TemporalCore;
import org.referix.temporalEngine.engine.TemporalEngineRunner;
import org.referix.temporalEngine.engine.api.TemporalAPIImpl;
import org.referix.temporalEngine.engine.phase.TemporalPhaseCoordinator;
import org.referix.temporalEngine.engine.timeline.PhaseTimelineImpl;
import org.referix.temporalEngine.periphery.config.TemporalCoreConfig;
import org.referix.temporalEngine.periphery.config.builder.TemporalCoreConfigBuilder;
import org.referix.temporalEngine.periphery.config.factory.IntermediateConfig;
import org.referix.temporalEngine.periphery.config.factory.TemporalConfigFactory;
import org.referix.temporalEngine.periphery.config.loader.RawConfig;
import org.referix.temporalEngine.periphery.config.loader.TemporalConfigLoader;
import org.referix.temporalEngine.periphery.config.validators.ConfigException;
import org.referix.temporalEngine.periphery.config.validators.TemporalConfigValidator;
import org.referix.temporalEngine.periphery.util.ConfigLogger;

import java.time.Instant;

public final class TemporalEngine extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        try {
            // config pipeline
            TemporalConfigLoader loader = new TemporalConfigLoader();
            RawConfig raw = loader.load(getConfig());

            TemporalConfigFactory factory = new TemporalConfigFactory();
            IntermediateConfig intermediate = factory.create(raw);

            new TemporalConfigValidator().validate(intermediate);

            TemporalCoreConfig coreConfig =
                    new TemporalCoreConfigBuilder().build(intermediate);

            ConfigLogger.log(this, coreConfig);

            // core
            TemporalCore core = new TemporalCore(coreConfig, Instant.now());

            PhaseTimelineImpl timeline = new PhaseTimelineImpl(core);
            TemporalAPIImpl api = new TemporalAPIImpl(timeline);

            TemporalPhaseCoordinator coordinator =
                    new TemporalPhaseCoordinator(core, api);

            TemporalEngineRunner runner =
                    new TemporalEngineRunner(
                            this,
                            coordinator,
                            coreConfig.getEvaluationInterval().toSeconds() * 20
                    );
            TemporalBukkitBridge bridge =
                    new TemporalBukkitBridge(this);

            api.registerListener(bridge);
            runner.start();

        } catch (ConfigException e) {
            getLogger().severe(e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
        }
    }



    @Override
    public void onDisable() {


    }


}
