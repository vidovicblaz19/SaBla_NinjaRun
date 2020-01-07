package com.feri.ninjarun.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.feri.ninjarun.config.GameConfig;
import com.feri.ninjarun.ecs.component.DimensionComponent;
import com.feri.ninjarun.ecs.component.Mappers;
import com.feri.ninjarun.ecs.component.MovementComponentXYR;
import com.feri.ninjarun.ecs.component.PositionComponent;
import com.feri.ninjarun.ecs.component.WorldWrapComponent;

public class WorldWrapSystem extends IteratingSystem {
    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            DimensionComponent.class,
            WorldWrapComponent.class,
            MovementComponentXYR.class
    ).get();

    public WorldWrapSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);
        DimensionComponent dimensionComponent = Mappers.DIMENSION.get(entity);
        MovementComponentXYR movement = Mappers.MOVEMENT.get(entity);

        if (position.x >= GameConfig.WIDTH-dimensionComponent.width) {
            position.x = GameConfig.WIDTH-dimensionComponent.width;
        } else if (position.x < 0) {
            position.x = 0;
        }
        if (position.y <= 0) {
            position.y = 0;

        }
    }
}
