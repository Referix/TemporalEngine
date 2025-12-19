package org.referix.temporalEngine.engine.phase;


import org.referix.temporalEngine.api.event.PhaseChangeEvent;
import org.referix.temporalEngine.api.event.PhaseEndEvent;
import org.referix.temporalEngine.api.event.PhaseStartEvent;
import org.referix.temporalEngine.api.phase.Phase;
import org.referix.temporalEngine.core.TemporalCore;
import org.referix.temporalEngine.engine.api.TemporalAPIImpl;
import org.referix.temporalEngine.periphery.config.TimePhase;

import java.time.Instant;
import java.util.Objects;

public final class TemporalPhaseCoordinator {

    private final TemporalCore core;
    private final TemporalAPIImpl api;

    public TemporalPhaseCoordinator(
            TemporalCore core,
            TemporalAPIImpl api
    ) {
        this.core = Objects.requireNonNull(core, "core");
        this.api = Objects.requireNonNull(api, "api");
    }

    /**
     * Викликається engine'ом замість прямого core.evaluate()
     */
    public void evaluate(Instant now) {
        Objects.requireNonNull(now, "now");

        // 1. Запамʼятовуємо фазу ДО
        TimePhase before = core.getCurrentPhase();

        // 2. Даємо core виконати свою логіку
        core.evaluate(now);

        // 3. Беремо фазу ПІСЛЯ
        TimePhase after = core.getCurrentPhase();

        // 4. Якщо фаза не змінилась — нічого не робимо
        if (before == after) {
            return;
        }

        // 5. Перетворюємо core-фази в API-фази
        Phase from = toApiPhase(before);
        Phase to = toApiPhase(after);

        // 6. Створюємо події
        PhaseEndEvent endEvent =
                new PhaseEndEvent(from, now);

        PhaseStartEvent startEvent =
                new PhaseStartEvent(to, now);

        PhaseChangeEvent changeEvent =
                new PhaseChangeEvent(from, to, now);

        // 7. Розсилаємо події слухачам
        dispatch(endEvent, startEvent, changeEvent);
    }

    private void dispatch(
            PhaseEndEvent end,
            PhaseStartEvent start,
            PhaseChangeEvent change
    ) {
        for (var listener : api.listeners()) {
            listener.onPhaseEnd(end);
            listener.onPhaseStart(start);
            listener.onPhaseChange(change);
        }
    }

    private Phase toApiPhase(TimePhase phase) {
        return new Phase(phase.getId(), phase.getDuration());
    }
}

