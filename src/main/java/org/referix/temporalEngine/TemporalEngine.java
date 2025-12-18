package org.referix.temporalEngine;

import org.bukkit.plugin.java.JavaPlugin;
import org.referix.temporalEngine.core.TemporalCore;
import org.referix.temporalEngine.engine.TemporalEngineRunner;
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
            // loader
            TemporalConfigLoader loader = new TemporalConfigLoader();
            RawConfig raw = loader.load(getConfig());

            // factory
            TemporalConfigFactory factory = new TemporalConfigFactory();
            IntermediateConfig intermediate = factory.create(raw);

            // validator
            TemporalConfigValidator validator = new TemporalConfigValidator();
            validator.validate(intermediate);

            // builder
            TemporalCoreConfigBuilder builder =
                    new TemporalCoreConfigBuilder();
            TemporalCoreConfig coreConfig =
                    builder.build(intermediate);

            // log
            ConfigLogger.log(this, coreConfig);

            TemporalCore core = new TemporalCore(coreConfig, Instant.now());

            // замість прямого runTaskTimer
            TemporalEngineRunner runner = new TemporalEngineRunner(
                    this,
                    core,
                    coreConfig.getEvaluationIntervalTick()
            );
            runner.start();

        } catch (ConfigException e) {
            getLogger().severe("Failed to start TemporalEngine:");
            getLogger().severe(e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
        }


    }


    @Override
    public void onDisable() {
    }


}
