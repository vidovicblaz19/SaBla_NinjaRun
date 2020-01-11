package com.feri.ninjarun;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
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
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class NinjaRun extends Game {
	private AssetManager assetManager;
	private SpriteBatch batch;
	private Socket socket;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		ViewportUtils.DEFAULT_CELL_SIZE = 70;

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
					Gdx.app.log("SocketIO", "msg_err");
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
					Gdx.app.log("SocketIO", "msg_err");
				}
			}
		});
	}

	public void selectGameScreen() {setScreen(new GameScreen(this));}
	public void selectIntroScreen() {setScreen(new IntroScreen(this));}
	public void selectLoginScreen() {setScreen(new LoginScreen(this));}

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
