package com.badlogic.unisim;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PausePopup implements PopUp {
    private final UniSimGame game;
    private boolean isVisible;

    public PausePopup(UniSimGame game) {
        this.isVisible = false;
        this.game = game;
    }

    public void show() {
        System.out.println("show() func called");
        isVisible = true;
    }

    public void hide() {
        isVisible = false;
    }

    public void draw(SpriteBatch batch) {
        if (isVisible()) {
            int x = 280;
            int y = 260;
            String pausedMessage = "Game Paused. Press P to resume.";
            game.font.draw(game.batch, pausedMessage, x, y);
        }
    }

    public void dispose() {
    }

    public boolean isVisible() {
        return isVisible;
    }
}
