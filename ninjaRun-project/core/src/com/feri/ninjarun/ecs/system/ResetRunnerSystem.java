package com.feri.ninjarun.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.feri.ninjarun.GameManager;
import com.feri.ninjarun.ecs.component.Mappers;
import com.feri.ninjarun.ecs.component.MovementComponentXYR;
import com.feri.ninjarun.ecs.component.PositionComponent;
import com.feri.ninjarun.ecs.component.SkierComponent;

public class ResetRunnerSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            SkierComponent.class
    ).get();

    public ResetRunnerSystem(){
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);

        if(GameManager.INSTANCE.isResetRunner()) {
            //position.x = 70f * 5;
            //position.y = 70f * 16;
            //GameManager.INSTANCE.setResetRunner(false);
        }
    }
}