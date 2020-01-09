package com.feri.ninjarun.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IntervalSystem;
import com.feri.ninjarun.config.GameConfig;
import com.feri.ninjarun.ecs.system.passive.EntityFactorySystem;

public class ShurikenSpawnSystem extends IntervalSystem {

    private EntityFactorySystem factory;

    public ShurikenSpawnSystem() {
        super(GameConfig.SHURIKEN_SPAWN_TIME);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        factory = engine.getSystem(EntityFactorySystem.class);
    }

    @Override
    protected void updateInterval() {
        factory.createShuriken();
    }
}
