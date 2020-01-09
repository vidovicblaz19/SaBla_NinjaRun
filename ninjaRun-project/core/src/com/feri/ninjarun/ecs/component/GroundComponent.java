package com.feri.ninjarun.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class GroundComponent implements Component, Pool.Poolable {

    public boolean hit;

    @Override
    public void reset() {
        hit = false;
    }
}
