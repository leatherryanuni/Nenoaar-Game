package com.badlogic.unisim;

/**
 * This interface defines the method for objects that act as pop-up messages
 * in the game screen.
 */
public interface PopUp {
    void show();
    void hide();
    void draw();
    void dispose();
    boolean isVisible();
}
