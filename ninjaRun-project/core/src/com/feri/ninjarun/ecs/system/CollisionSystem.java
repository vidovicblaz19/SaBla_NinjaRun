package com.feri.ninjarun.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.feri.ninjarun.GameManager;
import com.feri.ninjarun.NinjaRun;
import com.feri.ninjarun.ecs.component.BoundsComponent;
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

public class CollisionSystem extends EntitySystem {

    private static final Family SKIER_FAMILY = Family.all(SkierComponent.class, BoundsComponent.class).get();
    private static final Family FAMILY_GROUND = Family.all(GroundComponent.class, BoundsComponent.class).get();
    private static final Family FAMILY_OBSTACLE = Family.all(ObstacleComponent.class, BoundsComponent.class).get();
    private static final Family SHURIKEN_FAMILY = Family.all(ShurikenComponent.class, BoundsComponent.class).get();

    private EntityFactorySystem factory;
    private SoundSystem soundSystem;
    private TiledSystem tiledSystem;


    public CollisionSystem() {
    }

    @Override
    public void addedToEngine(Engine engine) {
        factory = engine.getSystem(EntityFactorySystem.class);
        soundSystem = engine.getSystem(SoundSystem.class);
        tiledSystem = engine.getSystem(TiledSystem.class);
    }

    @Override
    public void update(float deltaTime) {
        //if (GameManager.INSTANCE.isGameOver()) return;

        ImmutableArray<Entity> skiers = getEngine().getEntitiesFor(SKIER_FAMILY);
        ImmutableArray<Entity> grounds = getEngine().getEntitiesFor(FAMILY_GROUND);
        ImmutableArray<Entity> obstacles = getEngine().getEntitiesFor(FAMILY_OBSTACLE);
        ImmutableArray<Entity> shurikens = getEngine().getEntitiesFor(SHURIKEN_FAMILY);

        if (GameManager.INSTANCE.isResetRunner()){  //ob resetu izbrisem vse shurikene iz prejsnje igre
            for (Entity shurikenEntity : shurikens) {
                getEngine().removeEntity(shurikenEntity);
            }
        }

        for (Entity skierEntity : skiers) {
            BoundsComponent firstBounds = Mappers.BOUNDS.get(skierEntity);
            PositionComponent firstPosition = Mappers.POSITION.get(skierEntity);
            MovementComponentXYR firstMovement = Mappers.MOVEMENT.get(skierEntity);

            for (Entity ground : grounds) {
                GroundComponent groundComponent = Mappers.GROUND.get(ground);
                if (groundComponent.hit) {
                    //soundSystem.pick();
                    continue;
                }
                BoundsComponent secondBounds = Mappers.BOUNDS.get(ground);
                if (Intersector.overlaps(firstBounds.rectangle, secondBounds.rectangle)) {
                    if(firstPosition.y <= secondBounds.rectangle.height){
                        firstMovement.ySpeed = 0;
                        firstPosition.y = secondBounds.rectangle.y + secondBounds.rectangle.height;
                        GameManager.INSTANCE.setJumpCounter(0);
                    }

                }
            }

            for (Entity obstacle : obstacles) {
                ObstacleComponent obstacleComponent = Mappers.OBSTACLE.get(obstacle);
                if (obstacleComponent.hit) {
                    //soundSystem.pick();
                    continue;
                }
                BoundsComponent secondBounds = Mappers.BOUNDS.get(obstacle);
                if (Intersector.overlaps(firstBounds.rectangle, secondBounds.rectangle)) {
                    //groundComponent.hit = true;
                    GameManager.INSTANCE.damage();
                    //soundSystem.pick();

                }
            }
            for (Entity shuriken : shurikens) {
                ShurikenComponent shurikenComponent = Mappers.SHURIKEN.get(shuriken);
                if (shurikenComponent.hit) {
                    //soundSystem.pick();
                    continue;
                }
                BoundsComponent secondBounds = Mappers.BOUNDS.get(shuriken);
                if (Intersector.overlaps(firstBounds.rectangle, secondBounds.rectangle)) {
                    //groundComponent.hit = true;
                    GameManager.INSTANCE.damage();
                    //soundSystem.pick();
                }
                if(GameManager.INSTANCE.isGameOver() || GameManager.INSTANCE.isGameWon()){
                    MovementComponentXYR secondMovement = Mappers.MOVEMENT.get(shuriken);
                    secondMovement.reset();
                }
            }
        }
        for (Entity shurikenEntity : shurikens) {
            BoundsComponent firstBounds = Mappers.BOUNDS.get(shurikenEntity);
            PositionComponent firstPosition = Mappers.POSITION.get(shurikenEntity);
            MovementComponentXYR firstMovement = Mappers.MOVEMENT.get(shurikenEntity);

            for (Entity ground : grounds) {
                GroundComponent groundComponent = Mappers.GROUND.get(ground);
                if (groundComponent.hit) {
                    //soundSystem.pick();
                    continue;
                }
                BoundsComponent secondBounds = Mappers.BOUNDS.get(ground);
                if (Intersector.overlaps(firstBounds.rectangle, secondBounds.rectangle)) {
                    getEngine().removeEntity(shurikenEntity);

                }
            }
        }

    }

}
