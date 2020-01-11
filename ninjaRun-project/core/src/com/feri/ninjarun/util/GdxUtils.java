package com.feri.ninjarun.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class GdxUtils {

    public static void clearScreen() {
        clearScreen(Color.BLACK);
    }

    public static void clearScreen(Color color) {
        // clear screen
        // DRY - Don't repeat yourself
        // WET - Waste everyone's time
        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }


    public static void ProjectWorldCoordinatesInScreenCoordinates(Camera camera, Vector3 inWorldDimensions) {
        camera.project(inWorldDimensions);
    }


    public static String GetFormatedInMMSS(int duration) {
        return String.format("%02d:%02d", (int) (duration / 60), duration % 60);
    }

    private GdxUtils() {
    }
}
