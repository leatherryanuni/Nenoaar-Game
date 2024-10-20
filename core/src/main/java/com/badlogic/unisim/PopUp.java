package com.badlogic.unisim;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface PopUp {
    void show();
    void hide();
    void draw(SpriteBatch batch);
    void dispose();
    boolean isVisible();
}
