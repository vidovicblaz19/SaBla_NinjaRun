package com.feri.ninjarun.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

public class AnimationComponent implements Component, Pool.Poolable {
    public Animation<TextureRegion> region;
    public Animation<TextureRegion> region0;
    public Animation<TextureRegion> region1;
    public Animation<TextureRegion> region2;


    @Override
    public void reset() {
        region = null;
        region0 = null;
        region1 = null;
        region2 = null;
    }
}