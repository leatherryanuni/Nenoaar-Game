package com.badlogic.unisim;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * This class processes all the inputs executed by the user when interacting
 * with the in-game map, which will mostly be mouse clicks.
 */
public class GameInputProcessor implements InputProcessor {
    private final UIManager uiManager;
    private final GameTimer gameTimer;
    private final PausePopup pausePopup;
    private final BuildingPlacer buildingPlacer;

    public GameInputProcessor (GameTimer gameTimer, PausePopup pausePopup,
                               UIManager uiManager, BuildingPlacer buildingPlacer) {
        this.gameTimer = gameTimer;
        this.pausePopup = pausePopup;
        this.uiManager = uiManager;
        this.buildingPlacer = buildingPlacer;
    }

    @Override
    public boolean keyDown (int keycode) {
        // The key 'P' allows pausing and resuming in-game.
        if (keycode == Input.Keys.P) {
            if (gameTimer.getIsPaused()) {
                gameTimer.resumeTime();
                pausePopup.hide();
                uiManager.showBuildingMenuPrompt();
                // Allow placed buildings to be clickable
                buildingPlacer.enableBuildingActors();
            } else {
                // Hide/close any open menus and messages.
                gameTimer.pauseTime();
                pausePopup.show();
                uiManager.hideBuildingMenuPrompt();
                uiManager.hideBuildingMenu();
                // Stop placed buildings from being clickable
                buildingPlacer.disableBuildingActors();
            }
            return true;
        }
        // Don't register any other key inputs if the game is paused.
        if (gameTimer.getIsPaused()) {
            return false;
        }
        if (keycode == Input.Keys.M) {
            // The key 'M' allows closing and opening the building menu.
            if (uiManager.isBuildingMenuVisible()) {
                uiManager.hideBuildingMenu();
                // Make placed buildings clickable
                buildingPlacer.enableBuildingActors();
            } else {
                uiManager.showBuildingMenu();
                // If the building menu is opened when a placed building is
                // selected, the building will be deleted.
                if (buildingPlacer.isPlacedBuildingSelected) {
                    buildingPlacer.deleteBuilding();
                }
                // Make placed buildings no longer clickable
                buildingPlacer.disableBuildingActors();
                buildingPlacer.deselectBuilding();
            }
        }
        if (keycode == Input.Keys.BACKSPACE) {
            // BACKSPACE will delete a selected placed building
            if (buildingPlacer.isPlacedBuildingSelected) {
                buildingPlacer.deleteBuilding();
                buildingPlacer.deselectBuilding();
                uiManager.showBuildingMenuPrompt();
            }
        }
        return false;
    }

    @Override
    public boolean keyUp (int keycode) { return false; }

    @Override
    public boolean keyTyped(char character) { return false; }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Disable click input if game is paused.
        if (gameTimer.getIsPaused()) {
            return false;
        }
        // Don't register click input if the building is not in a buildable area
        if (!buildingPlacer.isBuildable) {
            return false;
        }
        // Place a building on click input
        if (buildingPlacer.isNewBuildingSelected || buildingPlacer.isPlacedBuildingSelected) {
            buildingPlacer.placeBuilding(screenX, screenY);
            buildingPlacer.deselectBuilding();
            uiManager.hideBuildingMenuPrompt();
            uiManager.showBuildingMenuPrompt();
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // Don't register mouse movement if game is paused
        if (gameTimer.getIsPaused()) {
            return false;
        }
        // If either a new building or placed building is selected.
        if (buildingPlacer.isNewBuildingSelected || buildingPlacer.isPlacedBuildingSelected) {
            buildingPlacer.snapBuildingToGrid(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) { return false; }

}
