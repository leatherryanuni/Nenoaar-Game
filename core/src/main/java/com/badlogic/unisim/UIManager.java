package com.badlogic.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * This class is responsible for the overall UI of the GameScreen, containing
 * smaller UI manager classes
 */
public class UIManager {
    private final Skin skin;
    private final Stage stage;
    private BuildingUIManager buildingUIManager;

    public UIManager(UniSimGame game, FitViewport viewport, Stage stage) {
        //stage = new Stage(viewport);//new FitViewport(64, 64));
        this.stage = stage;
        // Load the skin which is like a texture for UI elements
        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
        // Instantiate UI for building selection
        BuildingUIManager buildingUIManager = new BuildingUIManager(stage);
    }

    /**
     * Displays UI elements on the screen
     * @param delta time since last render in seconds.
     */
    public void renderUI(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
        buildingUIManager.dispose();
    }
}

