package com.badlogic.unisim;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for displaying the placed buildings and tracking
 * the number of each building.
 */
public class BuildingsTracker {
    private final Map<Building, String> placedBuildingsToType;
    public final Map<String, Integer> buildingTypesAvailability;
    private final Map<String, Integer> placedBuildingTypesCount;


    public BuildingsTracker() {
        this.placedBuildingsToType = new HashMap<>();
        this.buildingTypesAvailability = new HashMap<>();
        this.placedBuildingTypesCount = new HashMap<>();
        setBuildingTypeAvailability();
        setBuildingTypesCount();
    }

    /**
     * Returns the number of buildings currently placed on the map.
     * @return the number of buildings placed on the map.
     */
    public int getBuildingCount(String buildingType) {
        return placedBuildingTypesCount.get(buildingType);
    }

    /**
     * Retrieve the Building objects and their types.
     * @return the Building objects mapped to their types.
     */
    public Map<Building, String> getPlacedBuildingsToType() {
        return placedBuildingsToType;
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
        placedBuildingsToType.put(building, buildingType);
        decreaseBuildingTypeAvailability(buildingType);
        increaseBuildingCount(buildingType);
    }

    /**
     * Remove a building - type pair from the hashmap.
     * @param building the Building object that has been deleted from the map.
     */
    public void removeBuilding(Building building) {
        String buildingType = building.getBuildingType();
        placedBuildingsToType.remove(building);
        increaseBuildingTypeAvailability(buildingType);
        decreaseBuildingCount(buildingType);
    }


    /**
     * Temporarily disable its collision detection to stop the selected
     * building from colliding with itself and disable its clickable region.
     * @param building the building object to be 'disabled'.
     */
    public void disableBuildingOnMap(Building building) {
        placedBuildingsToType.remove(building);
        building.disableActor();
    }

    /**
     * Enable its collision detection when placed and enable its clickable
     * region.
     * @param building the building object to be 'enabled'.
     */
    public void enableBuildingOnMap(Building building) {
        placedBuildingsToType.put(building, building.getBuildingType());
        building.enableActor();
    }

    public void drawBuildings() {
        for (Building building : placedBuildingsToType.keySet()) {
            building.draw();
        }
    }

    /**
     * Sets the limit for how many buildings of each type can be placed.
     */
    private void setBuildingTypeAvailability() {
        buildingTypesAvailability.put("eat", 1);
        buildingTypesAvailability.put("sleep", 2);
        buildingTypesAvailability.put("learn", 1);
        buildingTypesAvailability.put("recreation", 1);
    }

    /**
     * Initialises the count for each building type to 0.
     */
    private void setBuildingTypesCount() {
        placedBuildingTypesCount.put("eat", 0);
        placedBuildingTypesCount.put("sleep", 0);
        placedBuildingTypesCount.put("learn", 0);
        placedBuildingTypesCount.put("recreation", 0);
    }

    /**
     * Increases building type count by 1.
     * @param buildingType the type of building.
     */
    private void increaseBuildingCount(String buildingType) {
        int currentCount = placedBuildingTypesCount.get(buildingType);
        currentCount++;
        placedBuildingTypesCount.put(buildingType, currentCount);
    }

    /**
     * Decreases building type count by 1.
     * @param buildingType the type of building.
     */
    private void decreaseBuildingCount(String buildingType) {
        int currentCount = placedBuildingTypesCount.get(buildingType);
        currentCount--;
        placedBuildingTypesCount.put(buildingType, currentCount);
    }

    /**
     * Free up availability by 1, this is done when a building is deleted.
     * @param buildingType the type of building.
     */
    private void increaseBuildingTypeAvailability(String buildingType) {
        int currentAvailability = buildingTypesAvailability.get(buildingType);
        currentAvailability++;
        buildingTypesAvailability.put(buildingType, currentAvailability);
    }

    /**
     * Decrease availability by 1, this is done when a building is placed.
     * @param buildingType the type of building.
     */
    private void decreaseBuildingTypeAvailability(String buildingType) {
        int currentAvailability = buildingTypesAvailability.get(buildingType);
        currentAvailability--;
        buildingTypesAvailability.put(buildingType, currentAvailability);
    }
}
