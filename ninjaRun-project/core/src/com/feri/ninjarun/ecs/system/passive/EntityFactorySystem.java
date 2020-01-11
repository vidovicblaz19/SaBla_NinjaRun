package com.feri.ninjarun.ecs.system.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.feri.ninjarun.assets.AssetDescriptors;
import com.feri.ninjarun.assets.RegionNames;
import com.feri.ninjarun.config.GameConfig;
import com.feri.ninjarun.ecs.component.AnimationComponent;
import com.feri.ninjarun.ecs.component.BoundsComponent;
import com.feri.ninjarun.ecs.component.CleanUpComponent;
import com.feri.ninjarun.ecs.component.DimensionComponent;
import com.feri.ninjarun.ecs.component.GravityComponent;
import com.feri.ninjarun.ecs.component.MovementComponentXYR;
import com.feri.ninjarun.ecs.component.PositionComponent;
import com.feri.ninjarun.ecs.component.ShurikenComponent;
import com.feri.ninjarun.ecs.component.SkierComponent;
import com.feri.ninjarun.ecs.component.TextureComponent;
import com.feri.ninjarun.ecs.component.TransformComponent;
import com.feri.ninjarun.ecs.component.WorldWrapComponent;
import com.feri.ninjarun.ecs.component.ZOrderComponent;
import com.feri.ninjarun.ecs.system.debug.support.ViewportUtils;

public class EntityFactorySystem extends EntitySystem {

    private static final int BACKGROUND_Z_ORDER = 0;
    private static final int TREE_Z_ORDER = 1;
    private static final int ROCK_Z_ORDER = 2;
    private static final int SHURIKEN_Z_ORDER = 3;
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
        position.x = 70f*5;
        position.y = 70f*16;

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.SKIER_WIDTH;
        dimension.height = GameConfig.SKIER_HEIGHT;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x*2, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

        MovementComponentXYR movement = engine.createComponent(MovementComponentXYR.class);
        //
        GravityComponent gravity = engine.createComponent(GravityComponent.class);
        TransformComponent transform = engine.createComponent(TransformComponent.class);

        SkierComponent rocket = engine.createComponent(SkierComponent.class);

        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);

        AnimationComponent animation = engine.createComponent(AnimationComponent.class);
        animation.region0 = new Animation<TextureRegion>(0.044f,gamePlayAtlas.findRegions(RegionNames.NINJA_RUNNING_ANIM), Animation.PlayMode.LOOP);
        animation.region1 = new Animation<TextureRegion>(0.06f,gamePlayAtlas.findRegions(RegionNames.NINJA_SLIDE_ANIM), Animation.PlayMode.LOOP);
        animation.region2 = new Animation<TextureRegion>(0.06f,gamePlayAtlas.findRegions(RegionNames.NINJA_JUMP_ANIM), Animation.PlayMode.NORMAL);
        animation.region = animation.region0;
        //namesto texture uporabi animacijo
        //diceAnimation = new Animation(0.08f,gamePlayAtlas.findRegions(RegionNames.DICEANIM), Animation.PlayMode.LOOP);


        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = SKIER_Z_ORDER;

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movement);
        //
        entity.add(gravity);
        entity.add(transform);
        entity.add(rocket);
        entity.add(worldWrap);
        entity.add(animation);
        entity.add(zOrder);

        engine.addEntity(entity);

        return entity;
    }

    public void createShuriken() {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        float min = 11;
        float max = 17;

        //position.x = GameConfig.W_WIDTH;
        position.x = GameConfig.POSITION_X+70f*30;
        position.y = 70f*MathUtils.floor(MathUtils.random(min, max)) + 9;

        MovementComponentXYR movementComponent = engine.createComponent(MovementComponentXYR.class);
        movementComponent.xSpeed = -GameConfig.TREE_SPEED_X_MIN * MathUtils.random(4f, 8f);
        //movementComponent.ySpeed = -GameConfig.TREE_SPEED_X_MIN /** MathUtils.random(1f, 2f)*/;
        movementComponent.rSpeed = MathUtils.random(1f, 5f);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.SHURIKEN_SIZE;
        dimension.height = GameConfig.SHURIKEN_SIZE;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.SHURIKEN);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = SHURIKEN_Z_ORDER;

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movementComponent);
        entity.add(engine.createComponent(ShurikenComponent.class));
        entity.add(texture);
        entity.add(zOrder);
        entity.add(engine.createComponent(CleanUpComponent.class));
        engine.addEntity(entity);
    }

}
