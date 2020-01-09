package com.feri.ninjarun.config;

public class GameConfig {
    //public static final float NINJA_WIDTH = 48f;
    //public static final float NINJA_HEIGHT = 64f;
    //public static final float MAX_NINJA_X_SPEED = 64f*5;

    //public static float WIDTH = 900f;
    //public static float HEIGHT = 924f;

    public static final float SKIER_WIDTH = 70f;
    public static final float SKIER_HEIGHT = 70f*2;
    public static final float SKIER_HEIGHT_TRANSFORM_MULTIPLIER = 0.5f;
    public static final float TREE_WIDTH_MIN = 32f;
    public static final float TREE_HEIGHT_MIN = 32f;
    public static final float TREE_WIDTH_MAX = 128f;
    public static final float TREE_HEIGHT_MAX = 128f;
    public static final float ROCK_WIDTH_MIN = 16f;
    public static final float ROCK_HEIGHT_MIN = 16f;
    public static final float ROCK_WIDTH_MAX = 32f;
    public static final float ROCK_HEIGHT_MAX = 32f;
    public static final float MAX_SKIER_X_SPEED = 70f*6;
    public static final float GRAVITY = 24f; //m/s2
    public static final float JUMP_SPEED = 70f*10f; //m/s2
    public static float NINJA_INCREASE_SPEED_INTERVAL = 0.007f;
    public static final float TREE_SPAWN_TIME = 1.2f;
    public static final float TREE_SPEED_X_MIN = 3f;
    public static final float ROCK_SPAWN_TIME = 1.2f;
    public static final float ROCK_SPEED_X_MIN = 3.8f;
    public static final float CHECKPOINT_SIZE = 48f;
    public static final float CHECKPOINT_SPAWN_TIME = 0.6f;

    // public static float WIDTH = 480f;
    // public static float HEIGHT = 800f;

    //public static float WIDTH = 900f;
    //public static float HEIGHT = 924f;


      //could be important
      public static float WIDTH = 70f*50;
      public static float HEIGHT = 70f*20;
      public static float W_WIDTH = 70f*70;
      public static float W_HEIGHT = 70f*20;
    /*
      public static float POSITION_X=(W_WIDTH - CAR_WIDTH)/2;
      public static float POSITION_Y=0;
      public static boolean debug = true;
    */
    public static boolean debug = true;
    //public static float WIDTH = 32*24;
    //public static float HEIGHT = 32*10f;
    //public static float W_WIDTH = 900f;
    //public static float W_HEIGHT = 924f;

    public static float POSITION_X=0;
    public static float POSITION_Y=0;
    private GameConfig() {
    }
}
