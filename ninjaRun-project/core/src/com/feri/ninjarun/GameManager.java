package com.feri.ninjarun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameManager {
    private static final String RESULT_BEST = "BEST_RESULT";
    public String userID;
    private Preferences PREFS;
    public static final GameManager INSTANCE = new GameManager();
    int result;
    //int health;
    int jumpCounter;

    public void resetResult() {
        result = 0;
        //health = 50;
        jumpCounter = 0;
    }

    public boolean isGameOver() {
        return getHealth() <= 0;
    }

    public int getJumpCounter() {
        return jumpCounter;
    }

    public void setJumpCounter(int jumpCounter) {
        this.jumpCounter = jumpCounter;
    }

    public void incJumpCounter(){
        jumpCounter++;
    }

    public void damage() {
        //health--;
        //if (health == 0) {
        //    if (result > getBestResult()) setBestResult(result);
        //}
    }

    public int getHealth() {
        return 20;
    }

    public void incResult() {
        result++;
    }

    public int getResult() {
        return result;
    }

    private GameManager() {
        PREFS = Gdx.app.getPreferences(GameManager.class.getSimpleName());
        userID = "saBla";
        jumpCounter = 0;
    }


    public void setBestResult(int result) {
        PREFS.putInteger(RESULT_BEST, result);
        PREFS.flush();
    }

    public int getBestResult() {
        return PREFS.getInteger(RESULT_BEST, 0);
    }
}
