package com.badlogic.unisim;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;

/**
 * This class processes all the inputs executed by the user when interacting
 * with the in-game map, which will mostly be mouse clicks.
 * Additionally, this class is responsible for detecting collisions when clicking
 * on non-buildable areas of the map.
 */
public class GameInputProcessor implements InputProcessor {
    private final OrthographicCamera camera;
    private final TiledMapTileLayer buildableLayer;

    public GameInputProcessor (OrthographicCamera camera,
                               TiledMapTileLayer buildableLayer) {
        this.camera = camera;
        this.buildableLayer = buildableLayer;
    }

    @Override
    public boolean keyDown (int keycode) { return false; }

    @Override
    public boolean keyUp (int keycode) { return false; }

    @Override
    public boolean keyTyped(char character) { return false; }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        checkBuildable(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        checkBuildable(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) { return false; }

    /**
     * Checks if the clicked tile is buildable.
     * @param screenX x-coordinate of the mouse on the screen
     * @param screenY y-coordinate of the mouse on the screen
     */
    public void checkBuildable (int screenX, int screenY) {
        Vector3 mousePosition = new Vector3(screenX, screenY, 0);
        // Convert mouse position to map coordinates
        camera.unproject(mousePosition);
        // Convert mouse coordinates to corresponding tile coordinates
        int tileX = (int) (mousePosition.x / buildableLayer.getTileWidth());
        int tileY = (int) (mousePosition.y / buildableLayer.getTileHeight());

        if ((isTileBuildable(tileX, tileY))) {
            System.out.println("Tile is buildable at (" + tileX + ", " + tileY + ")");
        } else {
            System.out.println("Tile is not buildable at (" + tileX + ", " + tileY + ")");
        }
    }

    /**
     * Checks if a tile is buildable by verifying whether such a tile exists
     * in the collision layer of the map.
     * @param x x-coordinate of the tile in the layer.
     * @param y y-coordinate of the tile in the layer.
     * @return true if a buildable tile exists, false otherwise.
     */
    public boolean isTileBuildable(int x, int y) {

        TiledMapTileLayer.Cell cell = buildableLayer.getCell(x, y);
        return cell != null;
    }

}
