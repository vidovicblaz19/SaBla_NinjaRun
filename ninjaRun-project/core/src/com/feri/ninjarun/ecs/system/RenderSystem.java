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
import com.feri.ninjarun.ecs.component.ZOrderComparator;
import com.feri.ninjarun.ecs.component.DimensionComponent;
import com.feri.ninjarun.ecs.component.Mappers;
import com.feri.ninjarun.ecs.component.PositionComponent;
import com.feri.ninjarun.ecs.component.TextureComponent;
import com.feri.ninjarun.ecs.component.ZOrderComponent;
import com.feri.ninjarun.ecs.system.passive.TiledSystem;


public class RenderSystem extends SortedIteratingSystem {

    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            DimensionComponent.class,
            TextureComponent.class,
            ZOrderComponent.class
    ).get();

    private final SpriteBatch batch;
    private final Viewport viewport;
    TiledSystem tiledSystem;
    Texture background = new Texture(AssetPaths.BACKGROUND); //added background
    int srcx;

    public RenderSystem(SpriteBatch batch, Viewport viewport) {
        super(FAMILY, ZOrderComparator.INSTANCE);
        this.batch = batch;
        this.viewport = viewport;
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        tiledSystem = engine.getSystem(TiledSystem.class);
    }

    @Override
    public void update(float deltaTime) { //override to avoid calling batch.begin/end for each entity
        viewport.apply();
        //temp
        batch.begin();
        //batch.draw(background, 0, 0,GameConfig.WIDTH/2,GameConfig.HEIGHT/1.8f);
        batch.draw(background,0, 0, srcx , 0, Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        if(!GameManager.INSTANCE.isGameOver()) {
                srcx += 1;
        }
        batch.end();

        tiledSystem.renderTiledView((OrthographicCamera) viewport.getCamera(), GameConfig.POSITION_X, GameConfig.POSITION_Y);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);
        DimensionComponent dimension = Mappers.DIMENSION.get(entity);
        TextureComponent texture = Mappers.TEXTURE.get(entity);

        batch.draw(texture.region,
                position.x, position.y,
                dimension.width / 2, dimension.height / 2,
                dimension.width, dimension.height,
                1, 1,
                position.r);


    }
}
