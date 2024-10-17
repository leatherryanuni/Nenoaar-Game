package com.badlogic.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 * First screen of the application. Displayed after the application is created.
 * This class represents the Main Menu of the game.
 */
public class MainMenuScreen implements Screen {
    // Reference the main game class to communicate with main game manager.
    private final UniSimGame game;

    public MainMenuScreen(UniSimGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        // Prepare your screen here
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        if (Gdx.input.justTouched()) {
            // Switch to GameScreen when user clicks screen.
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}
