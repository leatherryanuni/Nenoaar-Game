package com.badlogic.unisim;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for loading the assets used to create buildings,
 * and providing and organising building names and types accordingly.
 */
public class BuildingAssetsManager {
    private final String[] defaultPaths;
    private final String[] buildablePaths;
    private final String[] nonBuildablePaths;

    public BuildingAssetsManager () {
        // When adding additional building textures, ensure all their paths are in
        // the same order in each array.
        defaultPaths =  new String[]{
            "building-textures-default/eat_potato-shop.png",
            "building-textures-default/learn_space-science.png",
            "building-textures-default/recreation_zero-g-bowling.png",
            "building-textures-default/sleep_motel-mars.png",
            "building-textures-default/recreation_nightclub.png"
        };

        buildablePaths =  new String[]{
            "building-textures-buildable/eat_potato-shop.png",
            "building-textures-buildable/learn_space-science.png",
            "building-textures-buildable/recreation_zero-g-bowling.png",
            "building-textures-buildable/sleep_motel-mars.png",
            "building-textures-buildable/recreation_nightclub.png"
        };

        nonBuildablePaths =  new String[]{
            "building-textures-nonbuildable/eat_potato-shop.png",
            "building-textures-nonbuildable/learn_space-science.png",
            "building-textures-nonbuildable/recreation_zero-g-bowling.png",
            "building-textures-nonbuildable/sleep_motel-mars.png",
            "building-textures-nonbuildable/recreation_nightclub.png"
        };

        // Check if all path arrays are of equal length
        if (defaultPaths.length != buildablePaths.length
        || nonBuildablePaths.length != defaultPaths.length) {
            throw new IllegalArgumentException("The number of default, buildable"
                + " and non-buildable paths must be equal.");
        }
    }

    /**
     * Creates an array of default building textures.
     * @return a new array of default building textures.
     */
    public Texture[] loadDefaultTextures () {
        Texture[] defaultTextures = new Texture[defaultPaths.length];
        for (int i = 0; i < defaultPaths.length; i++) {
            Texture texture = new Texture(defaultPaths[i]);
            defaultTextures[i] = texture;
        }
        return defaultTextures;
    }

    /**
     * Creates an array of buildable building textures.
     * @return a new array of buildable building textures.
     */
    public Texture[] loadBuildableTextures() {
        Texture[] buildableTextures = new Texture[buildablePaths.length];
        for (int i = 0; i < buildablePaths.length; i++) {
            Texture texture = new Texture(buildablePaths[i]);
            buildableTextures[i] = texture;
        }
        return buildableTextures;
    }

    /**
     * Creates an array of non-buildable building textures.
     * @return a new array of non-buildable building textures
     */
    public Texture[] loadNonBuildableTextures() {
        Texture[] nonBuildableTextures = new Texture[nonBuildablePaths.length];
        for (int i = 0; i < nonBuildablePaths.length; i++) {
            Texture texture = new Texture(nonBuildablePaths[i]);
            nonBuildableTextures[i] = texture;
        }
        return nonBuildableTextures;
    }

    /**
     * Creates a Map containing building names and their types.
     * @return a new Map mapping building names to their type.
     */
    public Map<String, String> getBuildingNameToType() {
        Map<String, String> buildingNameToType = new HashMap<>();
        for (String pathName : defaultPaths) {
            String buildingName = extractName(pathName);
            String buildingType = extractType(pathName);
            buildingNameToType.put(buildingName, buildingType);
        }
        return buildingNameToType;
    }

    /**
     * Creates an array of each building's name.
     * @return a new array of each building's name.
     */
    public String[] getBuildingNames() {
        String[] buildingNames = new String[defaultPaths.length];
        for (int i = 0; i < defaultPaths.length; i++) {
            buildingNames[i] = extractName(defaultPaths[i]);
        }
        return buildingNames;
    }

    /**
     * Extracts the name of a building from its pathname.
     * @param pathName the path to the building's .png file.
     * @return the name of the building.
     */
    private String extractName(String pathName) {
        // This regular expression will isolate the building name from a
        // path name e.g 'motel-mars' from '.../sleep_motel-mars.png'
        String regex = ".*/[^_/]+_([a-zA-Z]+[-a-zA-Z]*)\\.png$";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(pathName);
        // If the building name is in the pathname, we return it
        if (matcher.find()) {
            String buildingName = matcher.group(1);
            return buildingName.replace("-", " ");
        }
        // If no match was found we return an empty string
        return "";
    }

    /**
     * Extracts the type of building from its pathname.
     * @param pathName the path to the building's .png file.
     * @return the type of building.
     */
    private String extractType(String pathName) {
        // This regular expression will isolate the building type from the
        // path name e.g 'sleep' from '.../sleep_motel-mars.png'
        String regex = ".*/([^_/]+)[_-]";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(pathName);
        // If the building type is in the pathname, we return it
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}
