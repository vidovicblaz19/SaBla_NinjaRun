package com.feri.ninjarun.ecs.system.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.feri.ninjarun.assets.AssetDescriptors;
import com.feri.ninjarun.assets.RegionNames;
import com.feri.ninjarun.config.GameConfig;
import com.feri.ninjarun.ecs.component.BoundsComponent;
import com.feri.ninjarun.ecs.component.DimensionComponent;
import com.feri.ninjarun.ecs.component.GravityComponent;
import com.feri.ninjarun.ecs.component.MovementComponentXYR;
import com.feri.ninjarun.ecs.component.PositionComponent;
import com.feri.ninjarun.ecs.component.SkierComponent;
import com.feri.ninjarun.ecs.component.TextureComponent;
import com.feri.ninjarun.ecs.component.WorldWrapComponent;
import com.feri.ninjarun.ecs.component.ZOrderComponent;

public class EntityFactorySystem extends EntitySystem {

    private static final int BACKGROUND_Z_ORDER = 0;
    private static final int TREE_Z_ORDER = 1;
    private static final int ROCK_Z_ORDER = 2;
    private static final int CHECKPOINT_Z_ORDER = 3;
    private static final int SKIER_Z_ORDER = 4;

    private final AssetManager assetManager;

    private PooledEngine engine;
    private TextureAtlas gamePlayAtlas;

    public EntityFactorySystem(AssetManager assetManager) {
        this.assetManager = assetManager;
        setProcessing(false); //passive
        init();
    }

    private void init() {
        gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = (PooledEngine) engine;
    }

    public Entity createSkier() {
        PositionComponent position = engine.createComponent(PositionComponent.class);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.SKIER_WIDTH;
        dimension.height = GameConfig.SKIER_HEIGHT;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);


        MovementComponentXYR movement = engine.createComponent(MovementComponentXYR.class);
        //
        GravityComponent gravitiy = engine.createComponent(GravityComponent.class);

        SkierComponent rocket = engine.createComponent(SkierComponent.class);

        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.SKIER);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = SKIER_Z_ORDER;

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movement);
        //
        entity.add(gravitiy);
        entity.add(rocket);
        entity.add(worldWrap);
        entity.add(texture);
        entity.add(zOrder);

        engine.addEntity(entity);

        return entity;
    }

}
