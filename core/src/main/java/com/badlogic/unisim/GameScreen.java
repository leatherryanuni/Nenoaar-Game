package com.badlogic.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * This class handles all the game logic and visuals for the main game screen
 */
public class GameScreen implements Screen {
    // Reference the main game class to communicate with main game manager.
    private final UniSimGame game;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;

    public GameScreen(UniSimGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        // Prepare your screen here
        int MAP_WIDTH = 1920; // width in pixels
        int MAP_HEIGHT = 1056; // height in pixels
        // Initialise camera and viewport to fit the size of the map.
        camera = new OrthographicCamera();
        viewport = new FitViewport(MAP_WIDTH, MAP_HEIGHT, camera);

        tiledMap = new TmxMapLoader().load("MarsMap.tmx");
        TiledMapTileLayer buildableLayer = (TiledMapTileLayer) tiledMap.getLayers().get("BuildableLayer");
        // Create a map renderer to be able to render the map in game.
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        // Set up InputProcessor containing collision detection
        GameInputProcessor inputProcessor = new GameInputProcessor(camera, buildableLayer);
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        // Clear the screen
        ScreenUtils.clear(Color.BLACK);
        camera.update();
        // Set the camera to the map renderer to make the map visible.
        mapRenderer.setView(camera);
        mapRenderer.render();

        game.batch.begin();
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
        viewport.update(width, height, true);
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
        // Destroy screen's assets here when we switch to the EndScreen.
        tiledMap.dispose();
        mapRenderer.dispose();
    }

}
