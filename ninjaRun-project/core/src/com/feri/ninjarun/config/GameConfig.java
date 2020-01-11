package com.feri.ninjarun.config;

public class GameConfig {
    public static String playerUsername = "";
    public static String playerPassword = "";
    public static String playerCookie = "";

    public static final float NINJA_WIDTH = 70f*2;
    public static final float NINJA_HEIGHT = 70f*2;
    public static final float NINJA_HEIGHT_TRANSFORM_MULTIPLIER = 0.5f;
    public static final float MAX_SKIER_X_SPEED = 70f*8;
    public static final float GRAVITY = 24f; //m/s2
    public static final float JUMP_SPEED = 70f*10f; //m/s2
    public static float NINJA_INCREASE_SPEED_INTERVAL = 0.007f;
    public static float SHURIKEN_SPAWN_TIME = 1f;
    public static final float TREE_SPEED_X_MIN = 3f;
    public static final float SHURIKEN_SIZE = 52f;

      public static float WIDTH = 70f*50;
      public static float HEIGHT = 70f*22;
      public static float W_WIDTH = 70f*70;
      public static float W_HEIGHT = 70f*20;

    public static boolean debug = false;
    public static boolean restartGame = false;

    public static float POSITION_X=0;
    public static float POSITION_Y=0;
    public static boolean ISSLIDE = false;
    public static boolean ISJUMP = false;
    public static int SERVER_MSG_JUMP = 0;
    public static int SERVER_MSG_SLIDE = 0;
    private GameConfig() {
    }
}
