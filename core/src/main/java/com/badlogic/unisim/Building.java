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
    private final Texture buildingTexture;
    private final Texture buildableBuildingTexture;
    private final Texture nonBuildableBuildingTexture;
    private int buildingPositionX;
    private int buildingPositionY;

    public Building (UniSimGame game, Stage stage, String buildingType, Texture buildingTexture,
                     Texture buildableBuildingTexture, Texture nonBuildableBuildingTexture,
                     int snappedPositionX, int snappedPositionY) {
        this.game = game;
        this.buildingType = buildingType;
        this.buildingTexture = buildingTexture;
        this.buildableBuildingTexture = buildableBuildingTexture;
        this.nonBuildableBuildingTexture = nonBuildableBuildingTexture;

        this.buildingPositionX = snappedPositionX;
        this.buildingPositionY = snappedPositionY;

        this.buildingSprite = new Sprite(buildingTexture);
        buildingSprite.setPosition(snappedPositionX, snappedPositionY);

        this.buildingActor = new Actor() {};
        buildingActor.setBounds(snappedPositionX, snappedPositionY,
                                buildingSprite.getWidth(), buildingSprite.getHeight());

        stage.addActor(buildingActor);
    }

    public Actor getBuildingActor () {
        return buildingActor;
    }

    public Sprite getBuildingSprite () {
        return buildingSprite;
    }

    public Texture getBuildingTexture () {
        return buildingTexture;
    }

    public Texture getBuildableBuildingTexture () {
        return buildableBuildingTexture;
    }

    public Texture getNonBuildableBuildingTexture () {
        return nonBuildableBuildingTexture;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void place(int snappedPositionX, int snappedPositionY) {
        buildingPositionX = snappedPositionX;
        buildingPositionY = snappedPositionY;
        buildingSprite.setTexture(buildingTexture);
        buildingSprite.setPosition(buildingPositionX, buildingPositionY);
        buildingActor.setBounds(buildingPositionX, buildingPositionY,
                                buildingSprite.getWidth(), buildingSprite.getHeight());
    }

    public void draw() {
        buildingSprite.draw(game.batch);
    }
}
