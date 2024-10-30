package com.badlogic.unisim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Building {
    private final UniSimGame game;
    private final Sprite buildingSprite;
    private final String buildingType;

    public Building (UniSimGame game, Texture buildingTexture,
                     String buildingType, int snappedPositionX,
                     int snappedPositionY) {
        this.game = game;
        this.buildingSprite = new Sprite(buildingTexture);
        this.buildingType = buildingType;
        buildingSprite.setPosition(snappedPositionX, snappedPositionY);
    }

    public Sprite getBuildingSprite () {
        return buildingSprite;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void draw() {
        buildingSprite.draw(game.batch);
    }
}
