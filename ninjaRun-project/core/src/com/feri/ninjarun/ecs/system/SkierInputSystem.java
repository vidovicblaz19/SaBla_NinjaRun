package com.feri.ninjarun.ecs.system;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.feri.ninjarun.GameManager;
import com.feri.ninjarun.config.GameConfig;
import com.feri.ninjarun.ecs.component.DimensionComponent;
import com.feri.ninjarun.ecs.component.Mappers;
import com.feri.ninjarun.ecs.component.MovementComponentXYR;
import com.feri.ninjarun.ecs.component.PositionComponent;
import com.feri.ninjarun.ecs.component.SkierComponent;
import com.feri.ninjarun.ecs.component.TransformComponent;


public class SkierInputSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            SkierComponent.class,
            MovementComponentXYR.class,
            TransformComponent.class,
            DimensionComponent.class,
            PositionComponent.class
    ).get();
    float Speedup;
    public SkierInputSystem() {
        super(FAMILY);
        Speedup = 0;
    }

    // we don't need to override update Iterating system method because there is no batch.begin/end

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponentXYR movement = Mappers.MOVEMENT.get(entity);
        TransformComponent transform = Mappers.TRANSFORM.get(entity);
        DimensionComponent dimension = Mappers.DIMENSION.get(entity);
        PositionComponent position = Mappers.POSITION.get(entity);

        transform.newHeightMultiplier = 1f;
        dimension.height = GameConfig.SKIER_HEIGHT;

        //Movement speed increasing
        Speedup += GameConfig.NINJA_INCREASE_SPEED_INTERVAL;

        movement.xSpeed = GameConfig.MAX_SKIER_X_SPEED * deltaTime + Speedup;

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (GameManager.INSTANCE.getJumpCounter() < 2) {
                movement.ySpeed = GameConfig.JUMP_SPEED * deltaTime;
                GameManager.INSTANCE.incJumpCounter();
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            transform.newHeightMultiplier = GameConfig.SKIER_HEIGHT_TRANSFORM_MULTIPLIER;
            dimension.height = GameConfig.SKIER_HEIGHT * transform.newHeightMultiplier;
        }


        if (GameManager.INSTANCE.isGameOver() || GameManager.INSTANCE.isGameWon()) {
            movement.reset();
        } else {
            GameManager.INSTANCE.incResult();
        }

        if (GameManager.INSTANCE.isResetRunner()) {
            position.x = 70f * 5;
            position.y = 70f * 16;
            Speedup = 0;
            GameManager.INSTANCE.setResetRunner(false);
        }
    }
}
