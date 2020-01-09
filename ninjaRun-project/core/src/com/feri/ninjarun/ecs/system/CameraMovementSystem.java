package com.feri.ninjarun.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.feri.ninjarun.config.GameConfig;
import com.feri.ninjarun.ecs.component.BoundsComponent;
import com.feri.ninjarun.ecs.component.Mappers;
import com.feri.ninjarun.ecs.component.PositionComponent;
import com.feri.ninjarun.ecs.component.SkierComponent;
import com.feri.ninjarun.ecs.system.passive.TiledSystem;

public class CameraMovementSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            SkierComponent.class
    ).get();

    public CameraMovementSystem() {
        super(FAMILY);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);

        GameConfig.POSITION_X = position.x + GameConfig.WIDTH/2.5f;
        GameConfig.POSITION_Y = position.y;

        //MathUtils.lerp(GameConfig.POSITION_Y, position.y, 0.1f); //movement.speed;
    }
}
