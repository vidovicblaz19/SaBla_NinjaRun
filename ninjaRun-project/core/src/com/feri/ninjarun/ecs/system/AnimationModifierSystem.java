package com.feri.ninjarun.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.feri.ninjarun.GameManager;
import com.feri.ninjarun.assets.AssetDescriptors;
import com.feri.ninjarun.assets.RegionNames;
import com.feri.ninjarun.config.GameConfig;
import com.feri.ninjarun.ecs.component.AnimationComponent;
import com.feri.ninjarun.ecs.component.BoundsComponent;
import com.feri.ninjarun.ecs.component.DimensionComponent;
import com.feri.ninjarun.ecs.component.GroundComponent;
import com.feri.ninjarun.ecs.component.Mappers;
import com.feri.ninjarun.ecs.component.MovementComponentXYR;
import com.feri.ninjarun.ecs.component.ObstacleComponent;
import com.feri.ninjarun.ecs.component.PositionComponent;
import com.feri.ninjarun.ecs.component.ShurikenComponent;
import com.feri.ninjarun.ecs.component.SkierComponent;
import com.feri.ninjarun.ecs.system.passive.EntityFactorySystem;
import com.feri.ninjarun.ecs.system.passive.SoundSystem;
import com.feri.ninjarun.ecs.system.passive.TiledSystem;
import com.feri.ninjarun.screen.GameScreen;

public class AnimationModifierSystem extends IteratingSystem {

    private static final Family SKIER_FAMILY = Family.all(SkierComponent.class, BoundsComponent.class).get();

    public AnimationModifierSystem() {
        super(SKIER_FAMILY);
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //ImmutableArray<Entity> skiers = getEngine().getEntitiesFor(SKIER_FAMILY);

        //for (Entity skierEntity : skiers) {
            BoundsComponent firstBounds = Mappers.BOUNDS.get(entity);
            PositionComponent firstPosition = Mappers.POSITION.get(entity);
            MovementComponentXYR firstMovement = Mappers.MOVEMENT.get(entity);
            AnimationComponent firstAnimation = Mappers.ANIMATION.get(entity);
        DimensionComponent firstDimension = Mappers.DIMENSION.get(entity);

        firstAnimation.region = firstAnimation.region0;
        //firstDimension.height = GameConfig.SKIER_HEIGHT;
        firstDimension.width = GameConfig.SKIER_WIDTH;
        firstDimension.height = GameConfig.SKIER_HEIGHT;

            if(GameConfig.ISSLIDE){
                firstAnimation.region = firstAnimation.region1;
                firstDimension.width = GameConfig.SKIER_HEIGHT;
                firstDimension.height = GameConfig.SKIER_WIDTH/2;
                //GameScreen.log.debug("se sploh zgodi to");
                //firstAnimation.reset();
                //firstAnimation.region = new Animation<TextureRegion>(0.06f,gamePlayAtlas.findRegions(RegionNames.NINJA_SLIDE_ANIM), Animation.PlayMode.LOOP);

            }else if(GameConfig.ISJUMP){
                firstAnimation.region = firstAnimation.region2;
            }
        //}
    }

}