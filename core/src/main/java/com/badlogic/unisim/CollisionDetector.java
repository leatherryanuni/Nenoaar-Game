package com.badlogic.unisim;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

import java.util.Map;

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
                                       Map<Building, String> placedBuildings) {

        return isTileAreaBuildable(tileX, tileY, buildingWidth, buildingHeight)
            && areOtherBuildingsOverlapping(placedBuildings, buildingSprite);
    }

    /**
     * Checks if all the tiles covered by a selected building are buildable.
     * @param tileX bottom left tile x-coordinate of the building.
     * @param tileY bottom left tile x-coordinate of the building.
     * @param buildingWidth width of the building in tiles.
     * @param buildingHeight height of the building in tiles.
     * @return true if all the tiles are buildable, false otherwise.
     */
    private boolean isTileAreaBuildable(int tileX, int tileY, int buildingWidth,
                               int buildingHeight) {
        // Iterate through all tiles covered by the building
        for (int x = tileX ;x < tileX + buildingWidth ;x++) {
            for (int y = tileY ; y < tileY + buildingHeight ; y++) {
                if (!isTileBuildable(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the selected building is overlapping with any other placed
     * buildings on the map.
     * @param placedBuildings hashmap containing the placed buildings.
     * @param buildingSprite the sprite of the selected building.
     * @return true if the selected sprite is not overlapping any of the
     * placed building sprites, false otherwise.
     */
    private boolean areOtherBuildingsOverlapping(Map<Building, String> placedBuildings,
                                                 Sprite buildingSprite) {
        // Iterate through all placed buildings
        for (Building placedBuilding : placedBuildings.keySet()) {
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
     * @param tileX x-coordinate of the tile in the layer.
     * @param tileY y-coordinate of the tile in the layer.
     * @return true if a buildable tile exists, false otherwise.
     */
    private boolean isTileBuildable(int tileX, int tileY) {
        TiledMapTileLayer.Cell cell = buildableLayer.getCell(tileX, tileY);
        return cell != null;
    }

    /**
     * Checks if a selected building is overlapping a placed building.
     * (Order of the two building sprites in the method is irrelevant)
     * @param sprite1 selected/placed building.
     * @param sprite2 selected/placed building.
     * @return true if the sprites overlap, false otheriwse.
     */
    private boolean isBuildingOverlapping(Sprite sprite1, Sprite sprite2) {
        Rectangle rectangle1 = sprite1.getBoundingRectangle();
        Rectangle rectangle2 = sprite2.getBoundingRectangle();
        return rectangle1.overlaps(rectangle2);
    }
}
