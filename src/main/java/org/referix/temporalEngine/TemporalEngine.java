package org.referix.temporalEngine;

import com.j256.ormlite.support.ConnectionSource;
import org.bukkit.plugin.java.JavaPlugin;
import org.referix.temporalEngine.bukkit.bridge.TemporalBukkitBridge;
import org.referix.temporalEngine.core.TemporalCore;
import org.referix.temporalEngine.engine.TemporalEngineRunner;
import org.referix.temporalEngine.engine.api.TemporalAPIImpl;
import org.referix.temporalEngine.engine.persistence.AutosaveStrategy;
import org.referix.temporalEngine.engine.persistence.FixedIntervalAutosave;
import org.referix.temporalEngine.engine.persistence.TemporalStateStore;
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
import org.referix.temporalEngine.persistence.sqlite.OrmLiteBootstrap;
import org.referix.temporalEngine.persistence.sqlite.SqliteTemporalStateStore;

import java.time.Duration;
import java.time.Instant;

public final class TemporalEngine extends JavaPlugin {

    private TemporalEngineRunner runner;
    private ConnectionSource connectionSource;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        try {
            // 1️⃣ Load config
            TemporalCoreConfig coreConfig = loadCoreConfig();
            TemporalCore core = new TemporalCore(coreConfig, Instant.now());

            // 2️⃣ Persistence
            connectionSource = OrmLiteBootstrap.init(getDataFolder());
            TemporalStateStore stateStore = new SqliteTemporalStateStore(connectionSource);

            // Restore previous state if exists
            stateStore.load().ifPresent(snapshot ->
                    core.restorePhase(snapshot.phaseId(), snapshot.phaseStartedAt())
            );

            // 3️⃣ API
            TemporalAPIImpl api = initializeApi(core);

            // 4️⃣ Coordinator + autosave
            AutosaveStrategy autosave = new FixedIntervalAutosave(Duration.ofMinutes(30));
            TemporalPhaseCoordinator coordinator =
                    new TemporalPhaseCoordinator(core, api, stateStore, autosave);

            // 5️⃣ Runner
            runner = new TemporalEngineRunner(this, coordinator, coreConfig.getEvaluationIntervalTick());

            // 6️⃣ Bridge
            TemporalBukkitBridge bridge = new TemporalBukkitBridge(this);
            api.registerListener(bridge);

            // 7️⃣ Start
            runner.start();
            getLogger().info("TemporalEngine successfully started.");

        } catch (Exception e) {
            failStartup(e);
        }
    }

    @Override
    public void onDisable() {
        if (runner != null) {
            runner.stop();
        }

        if (connectionSource != null) {
            try {
                connectionSource.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        getLogger().info("TemporalEngine stopped.");
    }

    private TemporalCoreConfig loadCoreConfig() throws ConfigException {
        TemporalConfigLoader loader = new TemporalConfigLoader();
        RawConfig raw = loader.load(getConfig());
        TemporalConfigFactory factory = new TemporalConfigFactory();
        IntermediateConfig intermediate = factory.create(raw);
        new TemporalConfigValidator().validate(intermediate);
        TemporalCoreConfig config = new TemporalCoreConfigBuilder().build(intermediate);
        ConfigLogger.log(this, config);
        return config;
    }

    private TemporalAPIImpl initializeApi(TemporalCore core) {
        return new TemporalAPIImpl(new PhaseTimelineImpl(core));
    }

    private void failStartup(Exception e) {
        getLogger().severe(e.getMessage());
        e.printStackTrace();
        getServer().getPluginManager().disablePlugin(this);
    }
}
