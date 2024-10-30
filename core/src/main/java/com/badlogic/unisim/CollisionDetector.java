package com.badlogic.unisim;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * This class is responsible for 'building to building' and 'building to map'
 * collision detection.
 */
public class CollisionDetector {
    private final TiledMapTileLayer buildableLayer;

    public CollisionDetector (TiledMapTileLayer buildableLayer) {
        this.buildableLayer = buildableLayer;
    }

    /**
     * Checks if the tiles covered by a selected building are buildable tiles.
     * @param tileX the tile at which the bottom left corner of the building is on.
     * @param tileY the tile at which the bottom left corner of the building is on.
     * @param buildingWidth the width of the building in tiles.
     * @param buildingHeight the height of the building in tiles.
     * @return true if all the tiles that the building is on are buildable, false otherwise
     */
    public boolean isBuildingBuildable(int tileX, int tileY, int buildingWidth,
                                       int buildingHeight, Sprite buildingSprite,
                                       ArrayList<Building> placedBuildings) {
        for (int x = tileX ; x < tileX + buildingWidth ; x++) {
            for (int y = tileY ; y < tileY + buildingHeight ; y++) {
                if (!(isTileBuildable(x, y))) {
                    return false;
                }
            }
        }
        for (Building placedBuilding : placedBuildings) {
            Sprite placedBuildingSprite = placedBuilding.getBuildingSprite();
            if (isBuildingOverlapping(buildingSprite, placedBuildingSprite)) {
                return false;
            }
        }
        return true;

    }

    /**
     * Checks if a tile is buildable by verifying whether such a tile exists
     * in the collision layer of the map.
     * @param x x-coordinate of the tile in the layer.
     * @param y y-coordinate of the tile in the layer.
     * @return true if a buildable tile exists, false otherwise.
     */
    private boolean isTileBuildable(int x, int y) {
        TiledMapTileLayer.Cell cell = buildableLayer.getCell(x, y);
        return cell != null;
    }

    private boolean isBuildingOverlapping(Sprite sprite1, Sprite sprite2) {
        Rectangle rectangle1 = sprite1.getBoundingRectangle();
        Rectangle rectangle2 = sprite2.getBoundingRectangle();
        return rectangle1.overlaps(rectangle2);
    }
}
