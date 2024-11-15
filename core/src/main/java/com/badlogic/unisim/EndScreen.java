package com.badlogic.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class EndScreen implements Screen {
    private final UniSimGame game;
    private final OrthographicCamera camera;

    public EndScreen(UniSimGame game) {
        this.game = game;
        camera = new OrthographicCamera();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        camera.update();

        game.batch.begin();
        game.font.draw(game.batch, "Click anywhere to restart", 650, 560);
        game.batch.end();

        if (Gdx.input.justTouched()) {
            // Switch to main menu screen upon clicking, i.e start new game.
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
