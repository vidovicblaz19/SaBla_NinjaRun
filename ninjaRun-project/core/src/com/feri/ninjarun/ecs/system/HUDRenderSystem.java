package com.feri.ninjarun.ecs.system;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.feri.ninjarun.GameManager;


public class HUDRenderSystem extends EntitySystem {

    private static final float PADDING = 20.0f;

    private final SpriteBatch batch;
    private final Viewport hudViewport;
    private final BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();

    public HUDRenderSystem(SpriteBatch batch, Viewport hudViewport, BitmapFont font) {
        this.batch = batch;
        this.hudViewport = hudViewport;
        this.font = font;
    }

    // we use EntitySystem

    @Override
    public void update(float deltaTime) {

        hudViewport.apply();
        batch.setProjectionMatrix(hudViewport.getCamera().combined);
        batch.begin();
        font.setColor(Color.BLUE);
        // high score
        String highScoreString = "HIGH: " + GameManager.INSTANCE.getBestResult();
        float y = hudViewport.getWorldHeight() - PADDING;

        layout.setText(font, highScoreString);
        font.draw(batch, layout, PADDING, y);

        String scoreString = "SCORE: " + GameManager.INSTANCE.getResult();
        layout.setText(font, scoreString);
        float scoreX = hudViewport.getWorldWidth() - layout.width;
        font.draw(batch, layout, scoreX, y);

        // health
        String healthString = "HEALTH: " + GameManager.INSTANCE.getHealth();
        layout.setText(font, healthString);
        float healthX = (hudViewport.getWorldWidth() + layout.width) / 2 - layout.width;

        font.draw(batch, layout, healthX, y);

        if (GameManager.INSTANCE.isGameOver()) {
            font.setColor(Color.RED);
            layout.setText(font, "The END");
            float endX = (hudViewport.getWorldWidth() + layout.width) / 2 - layout.width;
            float endY = (hudViewport.getWorldHeight() + layout.height) / 2 - layout.height;
            font.draw(batch, layout, endX, endY);
        }

        if(GameManager.INSTANCE.isGameWon()){
            font.setColor(Color.GREEN);
            layout.setText(font, "WINNER! You made it to the end!");
            float endX = (hudViewport.getWorldWidth() + layout.width) / 2 - layout.width;
            float endY = (hudViewport.getWorldHeight() + layout.height) / 2 - layout.height;
            font.draw(batch, layout, endX, endY);
        }

        batch.end();
    }
}
