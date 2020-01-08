package com.feri.ninjarun.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TransformComponent implements Component, Pool.Poolable{
    public float newWidthMultiplier = 1f;
    public float newHeightMultiplier = 1f;
    @Override
    public void reset() {
        newWidthMultiplier = 1f;
        newHeightMultiplier = 1f;
    }
}
