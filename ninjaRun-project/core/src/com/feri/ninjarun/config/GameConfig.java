package com.feri.ninjarun.config;

public class GameConfig {
    //public static final float NINJA_WIDTH = 48f;
    //public static final float NINJA_HEIGHT = 64f;
    //public static final float MAX_NINJA_X_SPEED = 64f*5;

    //public static float WIDTH = 900f;
    //public static float HEIGHT = 924f;

    public static final float SKIER_WIDTH = 48f;
    public static final float SKIER_HEIGHT = 64f;
    public static final float SKIER_HEIGHT_TRANSFORM_MULTIPLIER = 0.5f;
    public static final float TREE_WIDTH_MIN = 32f;
    public static final float TREE_HEIGHT_MIN = 32f;
    public static final float TREE_WIDTH_MAX = 128f;
    public static final float TREE_HEIGHT_MAX = 128f;
    public static final float ROCK_WIDTH_MIN = 16f;
    public static final float ROCK_HEIGHT_MIN = 16f;
    public static final float ROCK_WIDTH_MAX = 32f;
    public static final float ROCK_HEIGHT_MAX = 32f;
    public static final float MAX_SKIER_X_SPEED = 64f*5;
    public static final float GRAVITY = 18f; //m/s2
    public static final float JUMP_SPEED = 64f*7; //m/s2
    public static final float TREE_SPAWN_TIME = 1.2f;
    public static final float TREE_SPEED_X_MIN = 3f;
    public static final float ROCK_SPAWN_TIME = 1.2f;
    public static final float ROCK_SPEED_X_MIN = 3.8f;
    public static final float CHECKPOINT_SIZE = 48f;
    public static final float CHECKPOINT_SPAWN_TIME = 0.6f;

    // public static float WIDTH = 480f;
    // public static float HEIGHT = 800f;

    public static float WIDTH = 900f;
    public static float HEIGHT = 924f;


    /*  //could be important
      public static float WIDTH = 32*24;
      public static float HEIGHT = 32*10f;
      public static float W_WIDTH = 32*20;
      public static float W_HEIGHT = 32*100f;

      public static float POSITION_X=(W_WIDTH - CAR_WIDTH)/2;
      public static float POSITION_Y=0;
      public static boolean debug = true;
    */
    private GameConfig() {
    }
}
