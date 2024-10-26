package com.badlogic.unisim;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * This class processes all the inputs executed by the user when interacting
 * with the in-game map, which will mostly be mouse clicks.
 */
public class GameInputProcessor implements InputProcessor {
    private final UIManager uiManager;
    private final GameTimer gameTimer;
    private final PausePopup pausePopup;
    private final CollisionDetector collisionDetector;

    public GameInputProcessor (OrthographicCamera camera,
                               TiledMapTileLayer buildableLayer,
                               GameTimer gameTimer, PausePopup pausePopup, UIManager uiManager) {
        collisionDetector = new CollisionDetector(camera, buildableLayer);
        this.gameTimer = gameTimer;
        this.pausePopup = pausePopup;
        this.uiManager = uiManager;
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
        collisionDetector.checkBuildable(screenX, screenY);
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
        //checkBuildable(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) { return false; }

}
