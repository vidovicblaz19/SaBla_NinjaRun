package com.feri.ninjarun.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.feri.ninjarun.GameManager;
import com.feri.ninjarun.assets.AssetPaths;
import com.feri.ninjarun.config.GameConfig;
import com.feri.ninjarun.ecs.component.AnimationComponent;
import com.feri.ninjarun.ecs.component.DimensionComponent;
import com.feri.ninjarun.ecs.component.Mappers;
import com.feri.ninjarun.ecs.component.PositionComponent;
import com.feri.ninjarun.ecs.component.TextureComponent;
import com.feri.ninjarun.ecs.component.ZOrderComparator;
import com.feri.ninjarun.ecs.component.ZOrderComponent;
import com.feri.ninjarun.ecs.system.passive.TiledSystem;

public class RenderAnimationSystem extends SortedIteratingSystem {

    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            DimensionComponent.class,
            AnimationComponent.class,
            ZOrderComponent.class
    ).get();

    private final SpriteBatch batch;
    private final Viewport viewport;
    public float animationTime = 0;

    public RenderAnimationSystem(SpriteBatch batch, Viewport viewport) {
        super(FAMILY, ZOrderComparator.INSTANCE);
        this.batch = batch;
        this.viewport = viewport;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
    }

    @Override
    public void update(float deltaTime) { //override to avoid calling batch.begin/end for each entity
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);
        DimensionComponent dimension = Mappers.DIMENSION.get(entity);
        AnimationComponent animation = Mappers.ANIMATION.get(entity);

        animationTime += deltaTime;

        batch.draw(animation.region.getKeyFrame(animationTime),
                position.x, position.y,
                dimension.width / 2, dimension.height / 2,
                dimension.width, dimension.height,
                1, 1,
                position.r);

        //batch.draw(diceAnimation.getKeyFrame(controller.animationTime), x, y, 1, 1);


    }
}
