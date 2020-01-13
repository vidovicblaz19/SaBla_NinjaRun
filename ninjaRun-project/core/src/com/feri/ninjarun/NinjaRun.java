package com.feri.ninjarun;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Logger;
import com.feri.ninjarun.assets.AssetDescriptors;
import com.feri.ninjarun.config.GameConfig;
import com.feri.ninjarun.ecs.system.SkierInputSystem;
import com.feri.ninjarun.ecs.system.debug.support.ViewportUtils;
import com.feri.ninjarun.screen.GameScreen;
import com.feri.ninjarun.screen.IntroScreen;
import com.feri.ninjarun.screen.LoginScreen;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class NinjaRun extends Game {
	private AssetManager assetManager;
	private SpriteBatch batch;
	private Socket socket;

	private final List<Toast> toasts = new LinkedList<Toast>();
	private Toast.ToastFactory toastFactory;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		ViewportUtils.DEFAULT_CELL_SIZE = 70;

		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		BitmapFont font = skin.getFont("default-font");
		font.getData().setScale(0.5f);

		// create factory
		toastFactory = new Toast.ToastFactory.Builder().font(font).build();

		connectSocket();
		configSocketEvents();

		assetManager = new AssetManager();
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assetManager.getLogger().setLevel(Logger.DEBUG);

		batch = new SpriteBatch();
		assetManager.load(AssetDescriptors.FONT32);
		assetManager.load(AssetDescriptors.FONT96);
		assetManager.load(AssetDescriptors.GAME_PLAY);
		assetManager.load(AssetDescriptors.PICK_SOUND);
		assetManager.load(AssetDescriptors.TILES1);
		assetManager.load(AssetDescriptors.BACKGROUND);
		//assetManager.load(AssetDescriptors.TILES0);
		assetManager.finishLoading();
		//setScreen(new GameScreen(this));
		selectIntroScreen();
	}

	private void connectSocket() {
		try {
			socket = IO.socket("http://localhost:8080"); // server
			socket.connect();
		}
		catch (Exception e){
			System.out.println(e);
		}
	}

	public void toastLong(String text) {
		toasts.add(toastFactory.create(text, Toast.Length.LONG));
	}

	public void toastShort(String text) {
		toasts.add(toastFactory.create(text, Toast.Length.SHORT));
	}

	private void configSocketEvents() {
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				Gdx.app.log("SocketIO", "Connected");
			}
		}).on("jump", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String msg = data.getString("jump");
					if(msg.equals("jump")){
						if(GameConfig.SERVER_MSG_JUMP < 2){
							GameConfig.SERVER_MSG_JUMP++;
						}
					}
				}
				catch (JSONException e)
				{
					Gdx.app.log("SocketIO", "error while jumping (from server)");
				}
			}
		}).on("slide", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String msg = data.getString("slide");
					if(msg.equals("slide")){
						if(GameConfig.SERVER_MSG_SLIDE < 1){
							GameConfig.SERVER_MSG_SLIDE+=15;
						}
					}
				}
				catch (JSONException e)
				{
					Gdx.app.log("SocketIO", "error while sliding (from server)");
				}
			}
		}).on("money", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String money = data.getString("money");

					GameConfig.MONEY = Integer.parseInt(money);
					IntroScreen.money.setText("Money: " + money);
					Gdx.app.log("SocketIO", "Money: " + money);

				}
				catch (JSONException e)
				{
					Gdx.app.log("SocketIO", "error getting money (from server)");
				}
			}
		}).on("port", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String port = data.getString("port");

					GameConfig.SERVER_PORT = port;
					Gdx.app.log("SocketIO", "Server Port: " + port);

				}
				catch (JSONException e)
				{
					Gdx.app.log("SocketIO", "error getting port (from server)");
				}
			}
		});
	}

	public void selectGameScreen() {setScreen(new GameScreen(this));}
	public void selectIntroScreen() {setScreen(new IntroScreen(this));}
	public void selectLoginScreen() {
		if(!GameConfig.SERVER_PORT.equals(""))
			Gdx.net.openURI(GameConfig.SERVER_NAME + GameConfig.SERVER_PORT);
		else{
			Gdx.app.log("SocketIO", "Server not running");
			toastShort("Server not running!");
		}
	}

	@Override
	public void render() {
		super.render();

		Iterator<Toast> it = toasts.iterator();
		while(it.hasNext()) {
			Toast t = it.next();
			if (!t.render(Gdx.graphics.getDeltaTime())) {
				it.remove();
			} else {
				break;
			}
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		assetManager.dispose();
		batch.dispose();
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
	public SpriteBatch getBatch() {
		return batch;
	}
}
