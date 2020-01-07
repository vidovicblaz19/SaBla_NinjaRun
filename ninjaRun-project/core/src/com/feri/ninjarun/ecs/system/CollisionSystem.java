package com.feri.ninjarun.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.feri.ninjarun.GameManager;
import com.feri.ninjarun.ecs.component.BoundsComponent;
import com.feri.ninjarun.ecs.component.Mappers;
import com.feri.ninjarun.ecs.component.SkierComponent;
import com.feri.ninjarun.ecs.system.passive.EntityFactorySystem;
import com.feri.ninjarun.ecs.system.passive.SoundSystem;

public class CollisionSystem extends EntitySystem {

    private static final Family SKIER_FAMILY = Family.all(SkierComponent.class, BoundsComponent.class).get();

    private EntityFactorySystem factory;
    private SoundSystem soundSystem;


    public CollisionSystem() {
    }

    @Override
    public void addedToEngine(Engine engine) {
        factory = engine.getSystem(EntityFactorySystem.class);
        soundSystem = engine.getSystem(SoundSystem.class);
    }

    @Override
    public void update(float deltaTime) {
        if (GameManager.INSTANCE.isGameOver()) return;
        ImmutableArray<Entity> skiers = getEngine().getEntitiesFor(SKIER_FAMILY);
        for (Entity skierEntity : skiers) {
            BoundsComponent firstBounds = Mappers.BOUNDS.get(skierEntity);
        }

    }

}
