package com.badlogic.unisim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Map;

/**
 * This class is responsible for being able to position and place buildings
 * on the map in accordance with the map layout.
 */
public class BuildingPlacer {
    private final UniSimGame game;
    private final BuildingsTracker buildingsTracker;
    private final CollisionDetector collisionDetector;
    private final FitViewport viewport;
    private final Stage stage;
    private final Map<Building, String> placedBuildings;
    private final Vector3 mousePosition = new Vector3();
    private final int TILE_WIDTH;
    private final int TILE_HEIGHT;
    private Building placedBuilding;
    private Texture defaultTexture;
    private Texture buildableTexture;
    private Texture nonBuildableTexture;
    private Sprite buildingSprite;
    private String buildingType;
    public boolean isBuildable;
    public boolean isNewBuildingSelected = false;
    public boolean isPlacedBuildingSelected = false;

    public BuildingPlacer(UniSimGame game, BuildingsTracker buildingsTracker, FitViewport viewport,
                          Stage stage, TiledMapTileLayer buildableLayer) {
        this.game = game;
        this.buildingsTracker = buildingsTracker;
        this.viewport = viewport;
        this.stage = stage;
        this.placedBuildings = buildingsTracker.getPlacedBuildings();
        this.isBuildable = true;
        this.TILE_WIDTH = buildableLayer.getTileWidth();
        this.TILE_HEIGHT = buildableLayer.getTileHeight();
        collisionDetector = new CollisionDetector(buildableLayer);
    }

    /**
     * Enables the buildings to always be correctly positioned on-screen in
     * accordance with the grid arrangement of a tiled map.
     * @param screenX screen x-coordinate of the mouse on screen.
     * @param screenY screen y-coordinate of the mouse on screen.
     */
    public void snapBuildingToGrid(int screenX, int screenY) {
        // Store tile dimensions of the tiled map.
        // Get mouse coordinates
        mousePosition.set(screenX, screenY, 0);
        // Convert screen coordinates of mouse to map coordinates.
        Vector3 mapPosition = viewport.unproject(mousePosition);
        // Obtain building sprite dimensions in tiles.
        int buildingTileWidth = (int) buildingSprite.getWidth() / TILE_WIDTH;
        int buildingTileHeight = (int) buildingSprite.getHeight() / TILE_HEIGHT;
        // Obtain the tiles at which the building is currently located on screen.
        int tileX = (int) mapPosition.x / TILE_WIDTH;
        int tileY = (int) mapPosition.y / TILE_HEIGHT;
        // Check if the tiles on which the building is located are buildable
        // and update the texture depending on this.
        isBuildable = collisionDetector.isBuildingBuildable(tileX, tileY,
            buildingTileWidth,
            buildingTileHeight,
            buildingSprite,
            placedBuildings);
        updateBuildingTexture(isBuildable);
        // 'snap' the building to the screen coordinate closest to the
        // corresponding grid cell.
        int snappedPositionX = tileX * TILE_WIDTH;
        int snappedPositionY = tileY * TILE_HEIGHT;
        buildingSprite.setPosition(snappedPositionX, snappedPositionY);
    }

    /**
     * Creates a building sprite upon selection of a building from building menu.
     * @param buildingTexture The general texture of the building.
     * @param buildableTexture The texture representing the building
     *        when it can be placed in the current location on the map.
     * @param nonBuildableTexture The texture representing the building
     *        when it cannot be placed in the current location of the map.
     */
    public void selectNewBuilding(Texture buildingTexture,
                                  Texture buildableTexture,
                                  Texture nonBuildableTexture,
                                  String buildingType) {
        this.isNewBuildingSelected = true;
        this.defaultTexture = buildingTexture;
        this.buildableTexture = buildableTexture;
        this.nonBuildableTexture = nonBuildableTexture;
        buildingSprite = new Sprite(buildingTexture);
        this.buildingType = buildingType;
    }

    /**
     * Selects a placed building to be moved. Similar to the above method,
     * however uses the already existing building's attributes.
     * @param placedBuilding the Building object on the map that has been selected.
     */
    public void selectPlacedBuilding(Building placedBuilding) {
        isPlacedBuildingSelected = true;
        buildingSprite = placedBuilding.getBuildingSprite();
        this.placedBuilding = placedBuilding;
        this.defaultTexture = placedBuilding.getDefaultTexture();
        this.buildableTexture = placedBuilding.getBuildableTexture();
        this.nonBuildableTexture = placedBuilding.getNonBuildableTexture();
        buildingSprite.setTexture(buildableTexture);
        // Disable the building's clickable actor
        buildingsTracker.disableBuildingOnMap(placedBuilding);
    }

    /**
     * Stops drawing a building sprite at the position of the mouse.
     */
    public void deselectBuilding () {
        isNewBuildingSelected = false;
        isPlacedBuildingSelected = false;
    }

    /**
     * Draws the building sprite at the position of the mouse.
     */
    public void attachBuildingToMouse() {
        if (isNewBuildingSelected || isPlacedBuildingSelected) {
            buildingSprite.draw(game.batch);
        }
    }

    /**
     * Places a new building object at the location of the mouse when a
     * click is registered and increases building count by 1.
     * @param screenX x-coordinate of mouse on the screen
     * @param screenY y-coordinate of mouse on the screen.
     */
    public void placeBuilding(int screenX, int screenY) {
        // Get mouse coordinates
        mousePosition.set(screenX, screenY, 0);
        // Convert mouse coordinates to map coordinates.
        Vector3 mapPosition = viewport.unproject(mousePosition);
        // Obtain map coordinates in terms of map tiles.
        int tileX = (int) mapPosition.x / TILE_WIDTH;
        int tileY = (int) mapPosition.y / TILE_HEIGHT;
        // Obtain map coordinates of the bottom left corner of the current tile
        int snappedPositionX = tileX * TILE_WIDTH;
        int snappedPositionY = tileY * TILE_HEIGHT;
        // If the building has already been created, its position just has to
        // be changed and re-added to the array.
        if (isPlacedBuildingSelected) {
            // Re-activate building's clickable actor and collisions
            buildingsTracker.enableBuildingOnMap(placedBuilding);
            placedBuilding.place(snappedPositionX, snappedPositionY);
            return;
        }
        // Otherwise we create a new building
        Building newPlacedBuilding = new Building(game, stage, buildingType,
                                                defaultTexture, buildableTexture,
                                                nonBuildableTexture,
                                                snappedPositionX, snappedPositionY);
        addClickListenerToBuilding(newPlacedBuilding);
        buildingsTracker.addBuilding(newPlacedBuilding);
    }

    /**
     * Removes the Building object and deletes its clickable region
     * from the map.
     */
    public void deleteBuilding() {
        placedBuilding.deleteActor();
        buildingsTracker.removeBuilding(placedBuilding);
    }

    /**
     * Prevents map regions covered by a building actor from being clickable.
     */
    public void disableBuildingActors() {
        for (Building building : placedBuildings.keySet()) {
            building.disableActor();
        }
    }

    /**
     * Enables map regions covered by a building actor to be clickable.
     */
    public void enableBuildingActors() {
        for (Building building : placedBuildings.keySet()) {
            building.enableActor();
        }
    }

    /**
     * Changes the building texture to indicate whether it can or cannot be
     * placed in the current location on the map.
     * @param isBuildable true if the building covers tiles that are all
     *                    buildable, false otherwise.
     */
    private void updateBuildingTexture(boolean isBuildable) {
        if (isBuildable) {
            buildingSprite.setTexture(buildableTexture);
        } else {
            buildingSprite.setTexture(nonBuildableTexture);
        }
    }

    /**
     * Adds a click listener to the actors of the newly created Building objects
     * @param newPlacedBuilding a newly created Building object.
     */
    private void addClickListenerToBuilding(Building newPlacedBuilding) {
        newPlacedBuilding.getBuildingActor().addListener(
            new PlacedBuildingClickListener(this, newPlacedBuilding));
    }

    public void dispose() {
        defaultTexture.dispose();
        buildableTexture.dispose();
        nonBuildableTexture.dispose();
    }
}
