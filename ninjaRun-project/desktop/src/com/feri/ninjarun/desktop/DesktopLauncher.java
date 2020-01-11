package com.feri.ninjarun.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.feri.ninjarun.NinjaRun;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height=700;
		config.y = 50;
		config.x = 20;
		config.width = 1400;
		new LwjglApplication(new NinjaRun(), config);
	}
}
