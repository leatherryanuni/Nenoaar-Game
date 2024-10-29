package com.badlogic.unisim;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.viewport.FitViewport;

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
            if (gameTimer.isPaused()) {
                gameTimer.resumeTime();
                pausePopup.hide();
                uiManager.showBuildingMenuPrompt();
            } else {
                gameTimer.pauseTime();
                pausePopup.show();
                uiManager.hideBuildingMenuPrompt();
                uiManager.hideBuildingMenu();
            }
            return true;
        }
        if (keycode == Input.Keys.M) {
            // The key 'M' allows closing and opening the building menu.
            // Cannot open building menu if game is paused, so return false.
            if (gameTimer.isPaused()) { return false; }
            if (uiManager.isBuildingMenuVisible()) {
                uiManager.hideBuildingMenu();
            } else {
                uiManager.showBuildingMenu();
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
        return pausePopup.isVisible();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (buildingPlacer.isBuildingSelected) {
            buildingPlacer.snapBuildingToGrid(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) { return false; }

}
