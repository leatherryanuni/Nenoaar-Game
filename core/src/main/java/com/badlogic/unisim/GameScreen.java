package com.badlogic.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * This class handles all the game logic and visuals for the main game screen
 */
public class GameScreen implements Screen {
    // Reference the main game class to communicate with main game manager.
    private final UniSimGame game;
    private final GameTimer gameTimer;
    private final PausePopup pausePopup;
    private UIManager uiManager;
    private BuildingPlacer buildingPlacer;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;

    float MAP_WIDTH = 1920;
    float MAP_HEIGHT = 1056;

    public GameScreen(UniSimGame game) {
        this.game = game;
        this.gameTimer = new GameTimer(5);
        this.pausePopup = new PausePopup(game);
    }

    @Override
    public void show() {
        // Prepare your screen here
        // Initialise camera and viewport to fit the size of the map.
        camera = new OrthographicCamera();
        viewport = new FitViewport(MAP_WIDTH, MAP_HEIGHT, camera);
        // Load map and 'buildable' layer
        tiledMap = new TmxMapLoader().load("map/MarsMap.tmx");
        TiledMapTileLayer buildableLayer = (TiledMapTileLayer) tiledMap.getLayers().get("BuildableLayer");
        // Create a map renderer to be able to render the map in game.
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        System.out.println(viewport.getScreenWidth() + "," + viewport.getScreenHeight());
        // Create stage
        Stage stage = new Stage(viewport);
        buildingPlacer = new BuildingPlacer(game, viewport, stage, buildableLayer);
        // Load UI
        uiManager = new UIManager(game, stage, buildingPlacer);
        // Load input processor for the game.
        GameInputProcessor gameInputProcessor = new GameInputProcessor(
                                            gameTimer, pausePopup,
                                            uiManager, buildingPlacer);
        // As we need an additional input processor for UI elements, we can
        // combine the two input processors in an input multiplexer.
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(gameInputProcessor);
        // Set the InputMultiplexer as the main input processor
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        viewport.apply();
        camera.update();
        // Enable the spriteBatch to position and scale textures correctly on-screen
        game.batch.setProjectionMatrix(camera.combined);
        // Update timer
        gameTimer.updateTime(delta);
        // Check if the timer has ended, end the game once it has
        if (gameTimer.isTimeEnded()) {
            game.setScreen(new EndScreen(game));
            this.dispose();
        }
        // Clear the screen
        ScreenUtils.clear(Color.BLACK);
        // Set the camera to the map renderer to make the map visible
        mapRenderer.setView(camera);
        mapRenderer.render();
        // Begin drawing
        game.batch.begin();
        buildingPlacer.drawBuildings();
        buildingPlacer.attachBuildingToMouse();
        // Increase the size of the font used for on-screen writing
        game.font.getData().setScale(3.0f);
        // Display the timer on-screen
        game.font.draw(game.batch, "Time remaining: " + gameTimer.getFormattedTime(),
                20, 40);
        uiManager.drawBuildingMenuPrompt();
        uiManager.drawBuildingCounter();
        pausePopup.draw();
        // Stop drawing
        game.batch.end();

        uiManager.renderUI(delta);
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
        viewport.update(width, height, true);
        camera.update();
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
        uiManager.dispose();
    }
}
