package com.badlogic.unisim;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class UniSimGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        // Spritebatch initialised here so multiple screens can access it
        batch = new SpriteBatch();
        font = new BitmapFont();
        // Set the initial screen
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        // Dispose of recourses that are no longer needed when e.g changing
        // screens to prevent memory leaks.
        batch.dispose();
        font.dispose();
        super.dispose();
    }
}
