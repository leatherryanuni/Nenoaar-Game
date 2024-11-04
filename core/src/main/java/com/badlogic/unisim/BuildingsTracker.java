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

    /**
     * Returns the number of buildings currently placed on the map.
     * @return the number of buildings placed on the map.
     */
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

    /**
     * Checks whether it is still possible to place more buildings of a given
     * type.
     * @param buildingType the type of building. e.g 'sleep', 'learn'.
     * @return true if there is still availability, false otherwise.
     */
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

    /**
     * Temporarily disable its collision detection to stop the selected
     * building from colliding with itself and disable its clickable region.
     * @param building the building object to be 'disabled'.
     */
    public void disableBuildingOnMap(Building building) {
        placedBuildings.remove(building);
        building.disableActor();
    }

    /**
     * Enable its collision detection when placed and enable its clickable
     * region.
     * @param building the building object to be 'enabled'.
     */
    public void enableBuildingOnMap(Building building) {
        placedBuildings.put(building, building.getBuildingType());
        building.enableActor();
    }

    public void drawBuildings() {
        for (Building building : placedBuildings.keySet()) {
            building.draw();
        }
    }

    /**
     * Sets the limit for how many buildings of each type can be placed.
     * @param buildingType the type of building.
     * @param availability the amount of each building type that can be placed.
     */
    private void setBuildingTypeAvailability(String buildingType, int availability) {
        buildingTypesAvailability.put(buildingType, availability);
    }

    /**
     * Free up availability, this is done when a building is deleted.
     * @param buildingType the type of building.
     */
    private void increaseBuildingTypeAvailability(String buildingType) {
        int currentAvailability = buildingTypesAvailability.get(buildingType);
        currentAvailability++;
        buildingTypesAvailability.put(buildingType, currentAvailability);
    }

    /**
     * Decrease availability, this is done when a building is placed.
     * @param buildingType the type of building.
     */
    private void decreaseBuildingTypeAvailability(String buildingType) {
        int currentAvailability = buildingTypesAvailability.get(buildingType);
        currentAvailability--;
        buildingTypesAvailability.put(buildingType, currentAvailability);
    }
}
