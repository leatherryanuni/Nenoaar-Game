package com.badlogic.unisim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class BuildingPlacer {
    private final UniSimGame game;
    private final CollisionDetector collisionDetector;
    private final TiledMapTileLayer buildableLayer;
    private final FitViewport viewport;
    public boolean isBuildingSelected = false;
    private Texture buildableBuildingTexture;
    private Texture nonBuildableBuildingTexture;
    private final Vector3 mousePosition = new Vector3();
    private Sprite buildingSprite;
    private boolean isBuildable;

    public BuildingPlacer(UniSimGame game, FitViewport viewport, TiledMapTileLayer buildableLayer) {
        this.game = game;
        this.viewport = viewport;
        this.buildableLayer = buildableLayer;
        this.isBuildable = true;
        collisionDetector = new CollisionDetector(viewport, buildableLayer);
    }

    public void snapBuildingToGrid(int screenX, int screenY) {
        int TILE_WIDTH = buildableLayer.getTileWidth();
        int TILE_HEIGHT = buildableLayer.getTileHeight();
        // Get mouse coordinates
        mousePosition.set(screenX, screenY, 0);
        // Convert screen coordinates to world coordinates
        Vector3 worldPos = viewport.unproject(mousePosition);
        // This formula finds the closest screen coordinate to a tile from the
        // tiled map layer, providing a 'snap to grid' effect
        int tileX = (int) worldPos.x / TILE_WIDTH;
        int tileY = (int) worldPos.y / TILE_HEIGHT;
        isBuildable = collisionDetector.isTileBuildable(tileX, tileY);
        updateBuildingTexture(isBuildable);
        int snappedPositionX = tileX * TILE_WIDTH;
        int snappedPositionY = tileY * TILE_HEIGHT;
        buildingSprite.setPosition(snappedPositionX, snappedPositionY);
    }

    public void selectBuilding (Texture buildingTexture,
                                Texture buildableBuildingTexture,
                                Texture nonBuildableBuildingTexture) {
        this.buildableBuildingTexture = buildableBuildingTexture;
        this.nonBuildableBuildingTexture = nonBuildableBuildingTexture;
        buildingSprite = new Sprite(buildingTexture);
        this.isBuildingSelected = true;
    }

    public void deselectBuilding () {
        //this.buildingTexture = null;
        this.isBuildingSelected = false;
    }

    public void attachBuildingToMouse() {
        if (isBuildingSelected) {
            buildingSprite.draw(game.batch);
        }
    }

    private void updateBuildingTexture(boolean isBuildable) {
        if (isBuildable) {
            buildingSprite.setTexture(buildableBuildingTexture);
        } else {
            buildingSprite.setTexture(nonBuildableBuildingTexture);
        }
    }

}
