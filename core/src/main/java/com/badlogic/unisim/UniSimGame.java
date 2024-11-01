package com.badlogic.unisim;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class UniSimGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public Skin skin;

    @Override
    public void create() {
        // Spritebatch initialised here so multiple screens can access it
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
        font = skin.getFont("font-label");
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
        skin.dispose();
        super.dispose();
    }
}
