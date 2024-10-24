package com.badlogic.unisim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Building {
    private final UniSimGame game;
    private final Texture buildingTexture;
    private final Sprite buildingSprite;

    public Building (UniSimGame game, String imagePath) {
        this.game = game;
        buildingTexture = new Texture(imagePath);
        buildingSprite = new Sprite(buildingTexture);
    }

    public void draw() {
        buildingSprite.setPosition(0, 0);
        buildingSprite.draw(game.batch);
    }

    public Texture getBuildingTexture () {
        return buildingTexture;
    }

    public void dispose() {
        buildingTexture.dispose();
    }




}
