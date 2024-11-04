package com.badlogic.unisim;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PlacedBuildingClickListener extends ClickListener {
    private final BuildingPlacer buildingPlacer;
    private final Building building;

    public PlacedBuildingClickListener(BuildingPlacer buildingPlacer,
                                       Building building) {
        this.buildingPlacer = buildingPlacer;
        this.building = building;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        // Don't allow a placed building to be clicked if a building is currently
        // selected.
        if (buildingPlacer.isNewBuildingSelected || buildingPlacer.isPlacedBuildingSelected) {
            return;
        }
        buildingPlacer.selectPlacedBuilding(building);
    }
}
