package com.feri.ninjarun.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.feri.ninjarun.NinjaRun;
import com.feri.ninjarun.config.GameConfig;

public class IntroScreen extends ScreenAdapter implements InputProcessor {
    public static final Logger log = new Logger(IntroScreen.class.getName(), Logger.DEBUG);
    NinjaRun gameMain;
    private Stage stage;
    private Skin skin;

    private Table table;
    private TextButton startGameButton;
    private TextButton loginButton;
    private TextButton quitButton;
    public static Label money;
    private SpriteBatch batch;
    private Sprite sprite;
    private Viewport viewport;
    private OrthographicCamera camera;

    public IntroScreen(NinjaRun game) {
        this.gameMain = game;
        create();
    }

    public void create(){
        //camera = new OrthographicCamera();
        //viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT, camera);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new ScreenViewport());

        table = new Table();
        table.setWidth(stage.getWidth());
        table.align(Align.center|Align.top);

        table.setPosition(0, Gdx.graphics.getHeight());

        startGameButton = new TextButton("New game", skin);
        loginButton = new TextButton("Log in",skin);
        quitButton = new TextButton("Quit game",skin);
        money = new Label("Money: " + GameConfig.MONEY, skin);

        startGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMain.selectGameScreen();
            }
        });

        loginButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameMain.selectLoginScreen();
            }
        });

        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.add(money).padTop(10);
        table.row();
        table.padTop(90);
        table.add(startGameButton).padBottom(10);
        table.row();
        table.add(loginButton).padBottom(10);
        table.row();
        table.add(quitButton);

        stage.addActor(table);

        //batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("NinjaRunBackground.png")));
        sprite.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        InputMultiplexer im = new InputMultiplexer(stage, this);
        Gdx.input.setInputProcessor(im);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        stage.getBatch().begin();
        stage.getBatch().draw(sprite, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage.getBatch().end();

        stage.draw();

        //batch.begin();
        //sprite.draw(batch);
        //batch.end();
        //stage.act(delta);
        //stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    public void update(float delta){

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
