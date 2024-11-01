package com.badlogic.unisim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * This class is responsible for the clickListener attached to the ImageButtons
 * containing the building images.
 */
public class NewBuildingClickListener extends ClickListener {
    private final BuildingUIManager buildingUIManager;
    private final BuildingPlacer buildingPlacer;
    private final Texture buildingTexture;
    private final Texture buildableBuildingTexture;
    private final Texture nonBuildableBuildingTexture;
    private final String buildingType;

    public NewBuildingClickListener(BuildingPlacer buildingPlacer,
                                    Texture buildingTexture,
                                    Texture buildableBuildingTexture,
                                    Texture nonBuildableBuildingTexture,
                                    BuildingUIManager buildingUIManager,
                                    String buildingType) {
        this.buildingPlacer = buildingPlacer;
        this.buildingTexture = buildingTexture;
        this.buildableBuildingTexture = buildableBuildingTexture;
        this.nonBuildableBuildingTexture = nonBuildableBuildingTexture;
        this.buildingUIManager = buildingUIManager;
        this.buildingType = buildingType;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        buildingPlacer.selectNewBuilding(buildingTexture, buildableBuildingTexture,
                                      nonBuildableBuildingTexture,
                                      buildingType);
        buildingUIManager.hideBuildingMenu();
    }
}
