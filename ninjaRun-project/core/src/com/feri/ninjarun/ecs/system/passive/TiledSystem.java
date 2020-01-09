package com.feri.ninjarun.ecs.system.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.feri.ninjarun.GameManager;
import com.feri.ninjarun.config.GameConfig;
import com.feri.ninjarun.ecs.component.BoundsComponent;
import com.feri.ninjarun.ecs.component.GroundComponent;
import com.feri.ninjarun.ecs.component.ObstacleComponent;
import com.feri.ninjarun.util.OrthogonalTiledMapRendererStopStartAnimated;

public class TiledSystem extends EntitySystem {
    public static float UNIT_SCALE = 1f;
    public final TiledMap tiledMap;
    float tileWidth;
    float tileHeight;
    int widthInt;
    int heightInt;
    float widthMapInPx;
    float heightMapInPx;
    TiledMapTileLayer colideTileLayer;
    MapLayer collideObjectsLayer;
    MapLayer groundObjectsLayer;
    MapLayer killObjectsLayer;
    Rectangle tmp;
    Rectangle tmpArea;
    Array<BoundsComponent> debug;


    OrthogonalTiledMapRendererStopStartAnimated mapRenderer;

    public TiledSystem(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
        setProcessing(false); //passive
        if (GameConfig.debug) {
            debug = new Array<BoundsComponent>();
            for (int i = 0; i < 30; i++) debug.add(new BoundsComponent());
        }
        init();
        tmp = new Rectangle();
        tmpArea = new Rectangle();
    }

    private void init() {
        mapRenderer = new OrthogonalTiledMapRendererStopStartAnimated(tiledMap, UNIT_SCALE);
        mapRenderer.setAnimate(true);
        TiledMapTileLayer tiledLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Platform1");
        //TiledMapTileLayer tiledLayer2 = (TiledMapTileLayer) tiledMap.getLayers().get("Decor1");
        //colideTileLayer = (TiledMapTileLayer) tiledMap.getLayers().get("l_grass");
        //collideObjectsLayer = tiledMap.getLayers().get("l_objects_kill");
        groundObjectsLayer= tiledMap.getLayers().get("Bounds1");
        killObjectsLayer = tiledMap.getLayers().get("KillObjects1");

        //int myCostum = collideObjectsLayer.getProperties().get("custum_key",2, Integer.class);
        widthInt = tiledLayer.getWidth();
        heightInt = tiledLayer.getHeight();
        tileWidth = tiledLayer.getTileWidth();
        tileHeight = tiledLayer.getTileHeight();
        widthMapInPx = tileWidth * widthInt;
        heightMapInPx = tileHeight * heightInt;
        GameConfig.W_WIDTH = widthMapInPx;
        GameConfig.W_HEIGHT = heightMapInPx;
    }

    private void addGround(MapLayer layer, Engine engine) {
        MapObjects objects = layer.getObjects();
        for (MapObject object: objects) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            Entity entity = engine.createEntity();
            BoundsComponent bc = engine.createComponent(BoundsComponent.class);
            bc.rectangle.set(rectangle);
            GroundComponent gc = engine.createComponent(GroundComponent.class);
            entity.add(bc).add(gc);
            engine.addEntity(entity);
        }
    }

    private void addObstacle(MapLayer layer, Engine engine) {
        MapObjects objects = layer.getObjects();
        for (MapObject object: objects) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            Entity entity = engine.createEntity();
            BoundsComponent bc = engine.createComponent(BoundsComponent.class);
            bc.rectangle.set(rectangle);
            ObstacleComponent oc = engine.createComponent(ObstacleComponent.class);
            entity.add(bc).add(oc);
            engine.addEntity(entity);
        }
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        addGround(groundObjectsLayer,engine);
        addObstacle(killObjectsLayer,engine);
        //add debug bounds
        if (GameConfig.debug) {
            for (BoundsComponent bc : debug) {
                Entity entity = engine.createEntity();
                entity.add(bc);
                engine.addEntity(entity);
            }
        }
    }

    /**
     * Explain camera frame!
     *
     * @param camera
     * @param x
     * @param y
     */
    public void renderTiledView(OrthographicCamera camera, float x, float y) {
        camera.position.x = x;
        camera.position.y = MathUtils.clamp(y, camera.viewportHeight /2 + 70f*2, camera.viewportHeight /2 + 70f*2);
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

}
