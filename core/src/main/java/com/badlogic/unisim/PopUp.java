package com.badlogic.unisim;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * This interface defines the method for objects that act as pop-up messages
 * in the game screen.
 */
public interface PopUp {
    void show();
    void hide();
    void draw(SpriteBatch batch);
    void dispose();
    boolean isVisible();
}
