package com.badlogic.unisim;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for displaying the placed buildings and tracking
 * the number of each building.
 */
public class BuildingsTracker {
    private final Map<Building, String> placedBuildings;
    public final Map<String, Integer> buildingTypesAvailability;

    public BuildingsTracker() {
        this.placedBuildings = new HashMap<>();
        this.buildingTypesAvailability = new HashMap<>();
        setBuildingTypeAvailability("eat", 1);
        setBuildingTypeAvailability("sleep", 2);
        setBuildingTypeAvailability("learn", 1);
        setBuildingTypeAvailability("recreation", 1);
    }

    public int getBuildingCount() {
        return placedBuildings.size();
    }

    /**
     * Retrieve the Building objects and their types.
     * @return the Building objects mapped to their types.
     */
    public Map<Building, String> getPlacedBuildings() {
        return placedBuildings;
    }

    public boolean isBuildingTypeAvailable(String buildingType) {
        int availability = buildingTypesAvailability.get(buildingType);
        return availability > 0;
    }

    /**
     * Add a building - type pair to the hashmap.
     * @param building the Building object that has been placed on the map.
     */
    public void addBuilding(Building building) {
        String buildingType = building.getBuildingType();
        placedBuildings.put(building, buildingType);
        decreaseBuildingTypeAvailability(buildingType);
    }

    /**
     * Remove a building - type pair from the hashmap.
     * @param building the Building object that has been deleted from the map.
     */
    public void removeBuilding(Building building) {
        String buildingType = building.getBuildingType();
        placedBuildings.remove(building);
        increaseBuildingTypeAvailability(buildingType);
    }

    public void disableBuildingOnMap(Building building) {
        placedBuildings.remove(building);
        building.disableActor();
    }

    public void enableBuildingOnMap(Building building) {
        placedBuildings.put(building, building.getBuildingType());
        building.enableActor();
    }

    public void drawBuildings() {
        for (Building building : placedBuildings.keySet()) {
            building.draw();
        }
    }

    private void setBuildingTypeAvailability(String buildingType, int availability) {
        buildingTypesAvailability.put(buildingType, availability);
    }

    private void increaseBuildingTypeAvailability(String buildingType) {
        int currentAvailability = buildingTypesAvailability.get(buildingType);
        currentAvailability++;
        buildingTypesAvailability.put(buildingType, currentAvailability);
    }

    private void decreaseBuildingTypeAvailability(String buildingType) {
        int currentAvailability = buildingTypesAvailability.get(buildingType);
        currentAvailability--;
        buildingTypesAvailability.put(buildingType, currentAvailability);
    }
}
