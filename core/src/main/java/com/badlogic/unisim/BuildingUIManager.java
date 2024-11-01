package com.badlogic.unisim;

import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * This class is responsible for UI elements that allow building selection.
 */
public class BuildingUIManager {
    private final UniSimGame game;
    private final Texture[] buildingTextures;
    private final Texture[] buildableBuildingTextures;
    private final Texture[] nonBuildableBuildingTextures;
    private final String[] buildingNames;
    private final Map<String, String> buildingNameToType;
    private final ScrollPane scrollPane;
    private final BuildingPlacer buildingPlacer;

    public BuildingUIManager(UniSimGame game, Stage stage, BuildingPlacer buildingPlacer) {
        this.game = game;
        this.buildingPlacer = buildingPlacer;
        this.buildingTextures = loadBuildingTextures();
        this.buildableBuildingTextures = loadBuildableTextures();
        this.nonBuildableBuildingTextures = loadNonBuildableTextures();
        this.buildingNames = new String[]{"Potato Shop", "Space science",
                                            "Motel Mars", "Zero-g bowling"};
        this.buildingNameToType = loadBuildingNameToType(buildingNames);
        // Create all the tables required to organise UI elements
        Table buildingTable = createBuildingTable(buildingNames);
        this.scrollPane = createScrollPane(buildingTable);
        Table mainTable = createMainTable(scrollPane);

        stage.addActor(mainTable);
    }

    public boolean isVisible() {
        return scrollPane.isVisible();
    }

    public void showBuildingMenu() {
        scrollPane.setVisible(true);
    }

    public void hideBuildingMenu() {
        scrollPane.setVisible(false);
    }

    public int getBuildingCount() {
        return buildingPlacer.getBuildingCount();
    }

    /**
     * Creates an array of Texture objects that represent the building images.
     * @return an array of Texture objects.
     */
    private Texture[] loadBuildingTextures() {
        return new Texture[] {
            new Texture("building-textures/eat-potato-shop.png"),
            new Texture("building-textures/learn-space-science.png"),
            new Texture("building-textures/sleep-motel-mars.png"),
            new Texture("building-textures/recreation-bowling.png")
        };
    }

    /**
     * Creates an array of Texture objects that have a green tint to indicate
     * a building can be placed in a given location on the map.
     * @return an array of Texture objects.
     */
    private Texture[] loadBuildableTextures() {
        return new Texture[] {
            new Texture("building-textures-buildable/eat-potato-shop-buildable.png"),
            new Texture("building-textures-buildable/learn-space-science-buildable.png"),
            new Texture("building-textures-buildable/sleep-motel-mars-buildable.png"),
            new Texture("building-textures-buildable/recreation-bowling-buildable.png")
        };
    }

    /**
     * Creates an array of Texture objects that have a red tint to indicate
     * a building cannot be placed in a given location on the map.
     * @return an array of Texture objects.
     */
    private Texture[] loadNonBuildableTextures() { // Building textures with a red tint to
        return new Texture[] {                     // indicate a building cannot be placed
            new Texture("building-textures-nonbuildable/eat-potato-shop-nonbuildable.png"),
            new Texture("building-textures-nonbuildable/learn-space-science-nonbuildable.png"),
            new Texture("building-textures-nonbuildable/sleep-motel-mars-nonbuildable.png"),
            new Texture("building-textures-nonbuildable/recreation-bowling-nonbuildable.png")
        };
    }

    /**
     * Creates a Map object that maps the name of the building to its type
     * e.g {potato shop: eat, space science: learn} .
     * @param buildingNames array of building names.
     * @return the Map object with each building name mapped to its type.
     */
    private Map<String, String> loadBuildingNameToType(String[] buildingNames) {
        String[] buildingTypes = {"eat", "learn", "sleep", "recreation"};
        Map<String, String> buildingNameToType = new HashMap<>();
        for (int i = 0; i < buildingNames.length; i++) {
            buildingNameToType.put(buildingNames[i], buildingTypes[i]);
        }
        return buildingNameToType;
    }

    /**
     * Creates a table containing a collection of labelled ImageButton objects with their
     * corresponding buildings.
     * @return a table containing labelled ImageButton objects.
     */
    private Table createBuildingTable(String[] buildingNames) {
        ImageButton[] buildingButtons = new ImageButton[buildingTextures.length];
        Label[] buildingLabels = new Label[buildingTextures.length];
        Table buildingTable = new Table();
        addImagesToTable(buildingTable, buildingButtons, buildingTextures,
                         buildingLabels, buildingNames);
        addClickListenerToImageButtons(buildingButtons);
        buildingTable.pack();
        buildingTable.padTop(30).padBottom(30);
        return buildingTable;
    }

    /**
     * Creates a scrollPane to contain the buildingTable, this acts as a visual
     * container in the game screen.
     * @param table contains the collection of ImageButton objects representing
     *              each building.
     * @return a scrollPane containing the collection of ImageButton objects.
     */
    private ScrollPane createScrollPane(Table table) {
        ScrollPane scrollPane = new ScrollPane(table, game.skin);
        scrollPane.pack();
        // ScrollPane is set to invisible at first, so that it is not visible
        // when the game screen is loaded.
        scrollPane.setVisible(false);
        return scrollPane;
    }

    /**
     * Creates a table that contains the scrollPane.
     * @param scrollPane a container consisting of the buildingTable.
     * @return a table that contains the scrollPane for accurate positioning.
     */
    private Table createMainTable(ScrollPane scrollPane) {
        Table mainTable = new Table();
        // The mainTable being the size of the screen means the scrollPane
        // can easily be centred.
        mainTable.setFillParent(true);
        mainTable.add(scrollPane).center();
        return mainTable;
    }

    /**
     * Adds image buttons to a table using an array of textures
     * @param buildingTable The table that will contain the images.
     * @param buildingButtons An array containing ImageButton objects created from
     *                       the building textures.
     * @param buildingTextures An array containing the textures of each building
     */
    private void addImagesToTable(Table buildingTable, ImageButton[] buildingButtons,
                                  Texture[] buildingTextures, Label[] buildingLabels,
                                  String[] buildingNames) {
        for (int i = 0; i < buildingTextures.length; i++) {
            // Style the image buttons
            buildingButtons[i] = createStyledImageButton(buildingTextures[i], game.skin);
            // Create label for each building using the names
            buildingLabels[i] = new Label(buildingNames[i], game.skin);
            // Create image-label table
            Table imageLabelTable = createImageLabelTable(buildingLabels[i], buildingButtons[i]);
            // Add the table of image-label pairs to the buildings table
            buildingTable.add(imageLabelTable);
            // As we want a max of 5 buildings per row in our table,
            // every 5th index in the building textures list, we create a new row.
            if ((i + 1) % 5 == 0) {
                buildingTable.row();
            }
        }
    }

    /**
     * Creates and styles a new ImageButton object using the building texture and a ui skin.
     * @param buildingTexture The texture of the building.
     * @param skin collection of assets to style a UI component.
     * @return new ImageButton implementing the ui skins and building texture.
     */
    private ImageButton createStyledImageButton(Texture buildingTexture, Skin skin) {
        // Create a texture region and wrap it in a drawable, allowing the
        // image to be used inside an image button.
        Drawable buildingDrawable = new TextureRegionDrawable(new TextureRegion(buildingTexture));
        // ImageButtonStyle is responsible for how the button's visuals change
        // from its default state to its clicked (button-down) state.
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.up = skin.getDrawable("button");
        buttonStyle.down = skin.getDrawable("button-down");
        // Set the image for the button.
        buttonStyle.imageUp = buildingDrawable;
        return new ImageButton(buttonStyle);
    }

    /**
     * Creates a table containing image-label pairs.
     * @param buildingLabel the name of the building.
     * @param buildingButton button containing the image of the building.
     * @return the table containing image-label pairs.
     */
    private Table createImageLabelTable(Label buildingLabel, ImageButton buildingButton ) {
        Table imageLabelTable = new Table();
        // Add building button to top row
        imageLabelTable.add(buildingButton);
        // Indicate that subsequent additions are put into a row below the first.
        imageLabelTable.row();
        // Add the label for the button in the row below.
        imageLabelTable.add(buildingLabel);
        imageLabelTable.pad(10);
        return imageLabelTable;
    }

    /**
     * Adds a click listener to each ImageButton object.
     * @param buildingButtons An array of ImageButton objects
     */
    private void addClickListenerToImageButtons(ImageButton[] buildingButtons) {
        for (int i = 0; i < buildingButtons.length; i++) {
            buildingButtons[i].addListener(new NewBuildingClickListener(buildingPlacer,
                                                                     buildingTextures[i],
                                                                     buildableBuildingTextures[i],
                                                                     nonBuildableBuildingTextures[i],
                                                                     BuildingUIManager.this,
                                                                     buildingNameToType.get(buildingNames[i])) {}
            );
        }
    }

    public void dispose() {
        for (int i = 0; i < buildingTextures.length ; i++) {
            buildingTextures[i].dispose();
            buildableBuildingTextures[i].dispose();
            nonBuildableBuildingTextures[i].dispose();
            buildingPlacer.dispose();
        }
    }
}
