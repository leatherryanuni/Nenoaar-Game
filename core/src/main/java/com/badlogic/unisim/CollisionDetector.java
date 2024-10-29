package com.badlogic.unisim;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * This class is responsible for 'building to building' and 'building to map'
 * collision detection.
 */
public class CollisionDetector {
    private final FitViewport viewport;
    private final TiledMapTileLayer buildableLayer;
    private final Vector3 mousePosition = new Vector3();

    public CollisionDetector (FitViewport viewport, TiledMapTileLayer buildableLayer) {
        this.viewport = viewport;
        this.buildableLayer = buildableLayer;
    }

    /**
     * Checks if the clicked tile is buildable.
     * @param screenX x-coordinate of the mouse on the screen
     * @param screenY y-coordinate of the mouse on the screen
     */
    public void checkBuildable (int screenX, int screenY) {
        mousePosition.set(screenX, screenY, 0);
        // Convert mouse position to map coordinates
        viewport.unproject(mousePosition);
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
