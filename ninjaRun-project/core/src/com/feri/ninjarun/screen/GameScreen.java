package com.feri.ninjarun.screen;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.feri.ninjarun.GameManager;
import com.feri.ninjarun.NinjaRun;
import com.feri.ninjarun.assets.AssetDescriptors;
import com.feri.ninjarun.config.GameConfig;
import com.feri.ninjarun.ecs.system.BoundsSystem;
import com.feri.ninjarun.ecs.system.CleanUpSystem;
import com.feri.ninjarun.ecs.system.CollisionSystem;
import com.feri.ninjarun.ecs.system.GravitySystem;
import com.feri.ninjarun.ecs.system.HUDRenderSystem;
import com.feri.ninjarun.ecs.system.MovementSystem;
import com.feri.ninjarun.ecs.system.RenderSystem;
import com.feri.ninjarun.ecs.system.SkierInputSystem;
import com.feri.ninjarun.ecs.system.WorldWrapSystem;
import com.feri.ninjarun.ecs.system.debug.DebugCameraSystem;
import com.feri.ninjarun.ecs.system.debug.DebugInputSystem;
import com.feri.ninjarun.ecs.system.debug.DebugRenderSystem;
import com.feri.ninjarun.ecs.system.debug.GridRenderSystem;
import com.feri.ninjarun.ecs.system.passive.EntityFactorySystem;
import com.feri.ninjarun.ecs.system.passive.SoundSystem;
import com.feri.ninjarun.ecs.system.passive.StartUpSystem;
import com.feri.ninjarun.util.GdxUtils;

public class GameScreen extends ScreenAdapter {
    private static final Logger log = new Logger(GameScreen.class.getSimpleName(), Logger.DEBUG);

    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private final NinjaRun game;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer renderer;
    private PooledEngine engine; //main ECS class
    private BitmapFont font;
    private boolean debug;

    public GameScreen(NinjaRun game) {
        this.game = game;
        assetManager = game.getAssetManager();
        batch = game.getBatch();
        debug = true;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT, camera);
        hudViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderer = new ShapeRenderer();
        font = assetManager.get(AssetDescriptors.FONT32);
        engine = new PooledEngine();
        engine.addSystem(new EntityFactorySystem(assetManager));
        engine.addSystem(new SoundSystem(assetManager));

        if (debug) {
            engine.addSystem(new GridRenderSystem(viewport, renderer));
            engine.addSystem(new DebugCameraSystem(
                    GameConfig.WIDTH / 2, GameConfig.HEIGHT / 2, //center
                    camera
            ));
            engine.addSystem(new DebugRenderSystem(viewport, renderer));
            engine.addSystem(new DebugInputSystem());
        }
        engine.addSystem(new BoundsSystem());
        //engine.addSystem(new PlayerControlSystem());
        engine.addSystem(new WorldWrapSystem());
        engine.addSystem(new MovementSystem());
        //-----
        engine.addSystem(new GravitySystem());
        engine.addSystem(new SkierInputSystem());
        engine.addSystem(new RenderSystem(batch, viewport));
        engine.addSystem(new StartUpSystem());
        engine.addSystem(new CleanUpSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new HUDRenderSystem(batch, hudViewport, font));
        GameManager.INSTANCE.resetResult();
        printEngine();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        if (Gdx.input.isKeyPressed(Input.Keys.R)) GameManager.INSTANCE.resetResult();
        GdxUtils.clearScreen();
        if (GameManager.INSTANCE.isGameOver())
            engine.update(0);
        else
            engine.update(delta);

        // if (GameManager.INSTANCE.isGameOver()) {
        //     game.setScreen(new MenuScreen(game));
        // }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        engine.removeAllEntities();
    }

    public void printEngine() {
        ImmutableArray<EntitySystem> systems =  engine.getSystems();
        for (EntitySystem system:systems) {
            System.out.println(system.getClass().getSimpleName());
        }
    }
}