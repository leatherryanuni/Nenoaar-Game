package com.badlogic.unisim;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * This class is responsible for displaying and hiding a message, indicating
 * whether the game is paused or not.
 */
public class PausePopup implements PopUp {
    private final UniSimGame game;
    private boolean isVisible;

    public PausePopup(UniSimGame game) {
        this.isVisible = false;
        this.game = game;
    }

    /**
     * Shows the paused message.
     */
    public void show() {
        isVisible = true;
    }

    /**
     * Hides the paused message.
     */
    public void hide() {
        isVisible = false;
    }

    /**
     * 'Draws' the paused message on the screen if the game is paused.
     * @param batch the SpriteBatch object used for rendering the message.
     */
    public void draw(SpriteBatch batch) {
        if (isVisible()) {
            int MESSAGE_POSITION_X = 285;
            int MESSAGE_POSITION_Y = 260;
            String pausedMessage = "Game Paused. Press P to resume.";
            game.font.draw(game.batch, pausedMessage, MESSAGE_POSITION_X, MESSAGE_POSITION_Y);
        }
    }

    public void dispose() {

    }

    /**
     * Checks if the message is visible or not.
     * @return true if the message is visible, otherwise false.
     */
    public boolean isVisible() {
        return isVisible;
    }
}
