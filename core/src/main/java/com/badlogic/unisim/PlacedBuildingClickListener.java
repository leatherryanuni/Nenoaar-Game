package com.badlogic.unisim;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PlacedBuildingClickListener extends ClickListener {
    BuildingPlacer buildingPlacer;
    Building building;

    public PlacedBuildingClickListener(BuildingPlacer buildingPlacer, Building building) {
        this.buildingPlacer = buildingPlacer;
        this.building = building;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        if (buildingPlacer.isNewBuildingSelected || buildingPlacer.isPlacedBuildingSelected) {
            return;
        }
        buildingPlacer.selectPlacedBuilding(building);
    }
}
