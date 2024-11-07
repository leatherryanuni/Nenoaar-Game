package com.badlogic.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

/**
 * This class is responsible for the overall UI of the GameScreen, containing
 * smaller UI manager classes. It is responsible for 4 main elements:
 *  - BuildingMenu: the menu that contains the selection of buildings.
 *  - BuildingMenuPrompt: the prompt that indicates what key to press to
 *  open the menu.
 *  - DeleteBuildingPrompt: the prompt that indicates what key to press to
 *  delete a building.
 *  - BuildingCounter: the number of buildings placed on the map.
 */
public class UIManager {
    private final Skin skin;
    private final UniSimGame game;
    private final Stage stage;
    private final BuildingPlacer buildingPlacer;
    private final BuildingsTracker buildingsTracker;
    private final BuildingUIManager buildingUIManager;
    private boolean isBuildingMenuPromptVisible;

    public UIManager(UniSimGame game, Stage stage, BuildingPlacer buildingPlacer, BuildingsTracker buildingsTracker) {
        this.game = game;
        this.stage = stage;
        this.buildingPlacer = buildingPlacer;
        this.buildingsTracker = buildingsTracker;
        // Load the skin which is like a texture for UI elements
        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
        // Hide prompts at start of game
        this.isBuildingMenuPromptVisible = false;
        // Instantiate UI for building selection
        buildingUIManager = new BuildingUIManager(game, stage, buildingPlacer, buildingsTracker);
    }

    /**
     * Displays UI elements on the screen
     * @param delta time since last render in seconds.
     */
    public void renderUI(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void showBuildingMenuPrompt() {
        isBuildingMenuPromptVisible = true;
    }

    public void hideBuildingMenuPrompt() {
        isBuildingMenuPromptVisible = false;
    }

    public void drawBuildingMenuPrompt() {
        if (isBuildingMenuPromptVisible) {
            float PROMPT_POSITION_X = 1410;
            float PROMPT_POSITION_Y = 40;
            String promptMessage = "Press M for Building Menu";
            game.font.draw(game.batch, promptMessage, PROMPT_POSITION_X, PROMPT_POSITION_Y);
        }
    }

    public void drawDeleteBuildingPrompt() {
        if (buildingPlacer.isPlacedBuildingSelected) {
            hideBuildingMenuPrompt();
            float PROMPT_POSITION_X = 1200;
            float PROMPT_POSITION_Y = 40;
            String promptMessage = "Press BACKSPACE to delete building";
            game.font.draw(game.batch, promptMessage, PROMPT_POSITION_X, PROMPT_POSITION_Y);
        }
    }

    public void drawBuildingCounter() {
        String[] buildingTypes = new String[]{"Eat", "Learn", "Sleep", "Recreation"};
        int yPosition = 1040;
        for (String buildingType : buildingTypes) {
            String lowercaseType = buildingType.toLowerCase();
            game.font.draw(game.batch, buildingType + ": "
                + buildingsTracker.getBuildingCount(lowercaseType), 10, yPosition);
            yPosition -= 40;
        }
    }

    public void showBuildingMenu() {
        buildingUIManager.updateBuildingLabels();
        buildingUIManager.showBuildingMenu();
    }

    public void hideBuildingMenu() {
        buildingUIManager.hideBuildingMenu();
    }

    public boolean isBuildingMenuVisible() {
        return buildingUIManager.isVisible();
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
        buildingUIManager.dispose();
    }
}

