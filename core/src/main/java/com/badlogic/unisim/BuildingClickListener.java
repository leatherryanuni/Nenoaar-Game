package com.badlogic.unisim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class BuildingClickListener extends ClickListener {
    private final BuildingUIManager buildingUIManager;
    private final BuildingPlacer buildingPlacer;
    private final Texture buildingTexture;
    private final Texture buildableBuildingTexture;
    private final Texture nonBuildableBuildingTexture;

    public BuildingClickListener(BuildingPlacer buildingPlacer,
                                 Texture buildingTexture,
                                 Texture buildableBuildingTexture,
                                 Texture nonBuildableBuildingTexture,
                                 BuildingUIManager buildingUIManager) {
        this.buildingPlacer = buildingPlacer;
        this.buildingTexture = buildingTexture;
        this.buildableBuildingTexture = buildableBuildingTexture;
        this.nonBuildableBuildingTexture = nonBuildableBuildingTexture;
        this.buildingUIManager = buildingUIManager;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        buildingPlacer.selectBuilding(buildingTexture, buildableBuildingTexture, nonBuildableBuildingTexture);
        buildingUIManager.hideBuildingMenu();
    }
}
