package com.feri.ninjarun.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.feri.ninjarun.GameManager;
import com.feri.ninjarun.config.GameConfig;
import com.feri.ninjarun.ecs.component.GravityComponent;
import com.feri.ninjarun.ecs.component.GroundComponent;
import com.feri.ninjarun.ecs.component.Mappers;
import com.feri.ninjarun.ecs.component.MovementComponentXYR;
import com.feri.ninjarun.ecs.component.PositionComponent;

public class GravitySystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            GravityComponent.class,
            MovementComponentXYR.class
    ).get();

    public GravitySystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);
        MovementComponentXYR movement = Mappers.MOVEMENT.get(entity);
        GravityComponent gravity = Mappers.GRAVITIY.get(entity);

        //Gravity formula
        position.y -= (gravity.yAcceleration * deltaTime + movement.ySpeed) * deltaTime;
        if(position.y <= 0){
            movement.ySpeed = 0;
            position.y = 0;
            GameManager.INSTANCE.setJumpCounter(0);
        }
        movement.ySpeed = -gravity.yAcceleration * deltaTime + movement.ySpeed;
    }
}
