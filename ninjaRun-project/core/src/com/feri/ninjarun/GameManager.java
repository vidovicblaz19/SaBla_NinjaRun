package com.feri.ninjarun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameManager {
    private static final String RESULT_BEST = "BEST_RESULT";
    public String userID;
    private Preferences PREFS;
    public static final GameManager INSTANCE = new GameManager();
    int result;
    int health;
    int jumpCounter;
    boolean resetRunner;

    public void resetResult() {
        result = 0;
        health = 1;
        jumpCounter = 0;
        resetRunner = true;
    }

    public boolean isGameOver() {
        return getHealth() <= 0;
    }
    public boolean isGameWon() {
        if(getHealth() > 0 && result > 5540){
            if (result > getBestResult()) setBestResult(result);
            return true;
        }
        return false;
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
        health--;
        if (health == 0) {
            if (result > getBestResult()) setBestResult(result);
        }
    }

    public int getHealth() {
        return health;
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
        resetRunner = true;
    }

    public boolean isResetRunner() {
        return resetRunner;
    }

    public void setResetRunner(boolean resetRunner) {
        this.resetRunner = resetRunner;
    }

    public void setBestResult(int result) {
        PREFS.putInteger(RESULT_BEST, result);
        PREFS.flush();
    }

    public int getBestResult() {
        return PREFS.getInteger(RESULT_BEST, 0);
    }
}
