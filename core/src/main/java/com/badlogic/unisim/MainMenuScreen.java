package com.badlogic.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * First screen of the application. Displayed after the application is created.
 * This class represents the Main Menu of the game.
 */
public class MainMenuScreen implements Screen {
    // Reference the main game class to communicate with main game manager.
    private final UniSimGame game;
    OrthographicCamera camera;
    Texture menuScreen;

    public MainMenuScreen(UniSimGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Rescales the original menu screen to screen size
        menuScreen = new Texture("StartScreen.jpg");
    }

    @Override
    public void show() {
        // Prepare your screen here
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        ScreenUtils.clear(0.5f, 0, 0, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(menuScreen, 0, 0, 800, 480);
        game.batch.end();

        if (Gdx.input.justTouched()) {
            // Switch to GameScreen when user clicks screen.
            game.setScreen(new GameScreen(game));
            this.dispose();
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
        menuScreen.dispose();
    }
}
