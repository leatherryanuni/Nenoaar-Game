package com.badlogic.unisim;

import java.util.Map;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * This class is responsible for UI elements that show the selection of buildings.
 */
public class BuildingUIManager {
    private final UniSimGame game;
    private final Texture[] defaultTextures;
    private final Texture[] buildableTextures;
    private final Texture[] nonBuildableTextures;
    private final String[] buildingNames;
    private Label[] buildingLabels;
    private final Map<String, String> buildingNameToType;
    private final ScrollPane scrollPane;
    private final BuildingPlacer buildingPlacer;
    private final BuildingsTracker buildingsTracker;

    public BuildingUIManager(UniSimGame game, Stage stage,
                             BuildingPlacer buildingPlacer,
                             BuildingsTracker buildingsTracker) {
        this.game = game;
        this.buildingPlacer = buildingPlacer;
        this.buildingsTracker = buildingsTracker;
        // Load in and create all the assets and data structures required for
        // the buildings
        BuildingAssetsManager buildingAssetsManager = new BuildingAssetsManager();
        this.defaultTextures = buildingAssetsManager.loadDefaultTextures();
        this.buildableTextures = buildingAssetsManager.loadBuildableTextures();
        this.nonBuildableTextures = buildingAssetsManager.loadNonBuildableTextures();
        this.buildingNames = buildingAssetsManager.getBuildingNames();
        this.buildingNameToType = buildingAssetsManager.getBuildingNameToType();
        this.buildingLabels = new Label[defaultTextures.length];
        // Create all the tables required to organise UI elements
        Table buildingTable = createBuildingTable(buildingNames);
        this.scrollPane = createScrollPane(buildingTable);
        Table mainTable = createMainTable(scrollPane);
        // Add the UI elements to the stage.
        stage.addActor(mainTable);
    }

    /**
     * Returns true if the building menu is visible, false otherwise.
     * @return true if the building menu is visible, false otherwise.
     */
    public boolean isVisible() {
        return scrollPane.isVisible();
    }

    public void showBuildingMenu() {
        scrollPane.setVisible(true);
    }

    public void hideBuildingMenu() {
        scrollPane.setVisible(false);
    }

    /**
     * Changes the availability count of each building type in the building menu.
     */
    public void updateBuildingLabels() {
        for (int i = 0; i < buildingLabels.length; i++) {
            String updatedAvailability = buildingNames[i] + ": " + getTypeAvailability(buildingNames[i]);
            buildingLabels[i].setText(updatedAvailability);
        }
    }

    /**
     * Creates a table containing a collection of labelled ImageButton objects with their
     * corresponding buildings.
     * @return a table containing labelled ImageButton objects.
     */
    private Table createBuildingTable(String[] buildingNames) {
        ImageButton[] buildingButtons = new ImageButton[defaultTextures.length];
        buildingLabels = new Label[defaultTextures.length];
        Table buildingTable = new Table();
        addImagesToTable(buildingTable, buildingButtons, defaultTextures,
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
     * @param defaultTextures An array containing the normal textures of each building.
     */
    private void addImagesToTable(Table buildingTable, ImageButton[] buildingButtons,
                                  Texture[] defaultTextures, Label[] buildingLabels,
                                  String[] buildingNames) {
        for (int i = 0; i < defaultTextures.length; i++) {
            // Style the image buttons
            buildingButtons[i] = createStyledImageButton(defaultTextures[i], game.skin);
            // Create label for each building using the names
            buildingLabels[i] = new Label(
                buildingNames[i]+ ": " + getTypeAvailability(buildingNames[i]), game.skin
            );
            buildingLabels[i].setFontScale(1.5f);
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
     * Creates and styles a new ImageButton object using the building texture and an ui skin.
     * @param defaultTexture The normal texture of the building.
     * @param skin collection of assets to style a UI component.
     * @return new ImageButton implementing the ui skins and building texture.
     */
    private ImageButton createStyledImageButton(Texture defaultTexture, Skin skin) {
        // Create a texture region and wrap it in a drawable, allowing the
        // image to be used inside an image button.
        Drawable buildingDrawable = new TextureRegionDrawable(new TextureRegion(defaultTexture));
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
                                                                     buildingsTracker,
                                                                     defaultTextures[i],
                                                                     buildableTextures[i],
                                                                     nonBuildableTextures[i],
                                                                     BuildingUIManager.this,
                                                                     buildingNameToType.get(buildingNames[i])) {}
            );
        }
    }

    /**
     * Returns how many of the building's type can still be placed.
     * @param buildingName the name of the building.
     * @return how many of the building's type can still be placed.
     */
    private int getTypeAvailability(String buildingName) {
        String buildingType = buildingNameToType.get(buildingName);
        return buildingsTracker.buildingTypesAvailability.get(buildingType);
    }


    public void dispose() {
        for (int i = 0; i < defaultTextures.length ; i++) {
            defaultTextures[i].dispose();
            buildableTextures[i].dispose();
            nonBuildableTextures[i].dispose();
        }
    }
}
