package com.feri.ninjarun.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.feri.ninjarun.config.GameConfig;

public class GravityComponent implements Component, Pool.Poolable {
    public float yAcceleration = GameConfig.GRAVITY;

    @Override
    public void reset() {
        yAcceleration = 0f;
    }
}
