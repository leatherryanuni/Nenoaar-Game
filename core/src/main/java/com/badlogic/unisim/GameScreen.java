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
    private final GameTimer gameTimer;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private final PausePopup pausePopup;

    //private final int MAP_WIDTH = 1920;
    //private final int MAP_HEIGHT = 1056;

    public GameScreen(UniSimGame game) {
        this.game = game;
        this.gameTimer = new GameTimer(5);
        this.pausePopup = new PausePopup(game);
    }

    @Override
    public void show() {
        // Prepare your screen here
        int MAP_WIDTH = 1920;
        int MAP_HEIGHT = 1056;
        // Initialise camera and viewport to fit the size of the map.
        camera = new OrthographicCamera();
        viewport = new FitViewport(MAP_WIDTH, MAP_HEIGHT, camera);
        // Load map and 'buildable' layer
        tiledMap = new TmxMapLoader().load("MarsMap.tmx");
        TiledMapTileLayer buildableLayer = (TiledMapTileLayer) tiledMap.getLayers().get("BuildableLayer");
        // Create a map renderer to be able to render the map in game.
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        // Set up InputProcessor containing collision detection and initialise game timer
        GameInputProcessor inputProcessor = new GameInputProcessor(game, camera,
                                            buildableLayer, gameTimer, pausePopup);
        Gdx.input.setInputProcessor(inputProcessor);
        // Game starts paused.
        pausePopup.show();
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        // Update timer
        gameTimer.updateTime(delta);
        // Check if the timer has ended, end the game once it has.
        if (gameTimer.isTimeEnded()) {
            game.setScreen(new EndScreen(game));
        }
        // Clear the screen
        ScreenUtils.clear(Color.BLACK);
        // Set the camera to the map renderer to make the map visible.
        mapRenderer.setView(camera);
        mapRenderer.render();

        game.batch.begin();
        // Display the timer on-screen
        game.font.draw(game.batch, "Time remaining: " + gameTimer.getFormattedTime(),
            10, 20);
        pausePopup.draw(game.batch);
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
        pausePopup.dispose();
    }

}
