package com.badlogic.unisim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * This class is responsible for the properties of each building that is placed
 * on the game map.
 */
public class Building {
    private final UniSimGame game;
    private final Sprite buildingSprite;
    private final Actor buildingActor;
    private final String buildingType;
    private final Texture defaultTexture;
    private final Texture buildableTexture;
    private final Texture nonBuildableTexture;
    private int buildingPositionX;
    private int buildingPositionY;

    public Building(UniSimGame game, Stage stage, String buildingType, Texture defaultTexture,
                    Texture buildableTexture, Texture nonBuildableTexture,
                    int snappedPositionX, int snappedPositionY) {
        this.game = game;
        this.buildingType = buildingType;
        this.defaultTexture = defaultTexture;
        this.buildableTexture = buildableTexture;
        this.nonBuildableTexture = nonBuildableTexture;
        this.buildingPositionX = snappedPositionX;
        this.buildingPositionY = snappedPositionY;
        // Create new sprite for the building
        this.buildingSprite = new Sprite(defaultTexture);
        buildingSprite.setPosition(snappedPositionX, snappedPositionY);
        // Create new clickable actor for the building
        this.buildingActor = new Actor() {};
        // Ensure the clickable region lies within the sprite
        buildingActor.setBounds(snappedPositionX, snappedPositionY,
            buildingSprite.getWidth(), buildingSprite.getHeight());

        stage.addActor(buildingActor);
    }

    public Actor getBuildingActor() {
        return buildingActor;
    }

    public Sprite getBuildingSprite() {
        return buildingSprite;
    }

    public Texture getDefaultTexture() {
        return defaultTexture;
    }

    public Texture getBuildableTexture() {
        return buildableTexture;
    }

    public Texture getNonBuildableTexture() {
        return nonBuildableTexture;
    }

    public String getBuildingType() {
        return buildingType;
    }

    // Enables the sprite to be clickable.
    public void enableActor() {
        buildingActor.setVisible(true);
    }

    /**
     * Temporarily removes the clickable region of a sprite.
     */
    public void disableActor() {
        buildingActor.setVisible(false);
    }

    /**
     * Places a Building object on the map that has been moved.
     * @param snappedPositionX screen x-coordinate of bottom left corner
     *                         of the map's tile in that location.
     * @param snappedPositionY screen y-coordinate of bottom left corner
     *                         of the map's tile in that location.
     */
    public void place(int snappedPositionX, int snappedPositionY) {
        buildingPositionX = snappedPositionX;
        buildingPositionY = snappedPositionY;
        buildingSprite.setTexture(defaultTexture);
        buildingSprite.setPosition(buildingPositionX, buildingPositionY);
        buildingActor.setBounds(buildingPositionX, buildingPositionY,
            buildingSprite.getWidth(), buildingSprite.getHeight());
    }

    public void deleteActor() {
        buildingActor.remove();
    }

    public void draw() {
        buildingSprite.draw(game.batch);
    }
}
