package com.badlogic.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * This class is responsible for building selection UI elements.
 */
public class BuildingUIManager {
    private final Texture[] buildingTextures;
    private final Skin skin;
    private final ScrollPane scrollPane;

    public BuildingUIManager(Stage stage) {
        this.skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
        buildingTextures = new Texture[] {
            new Texture("building-textures/eat-potato-shop.png"),
            new Texture("building-textures/learn-space-science.png"),
            new Texture("building-textures/sleep-motel-mars.png"),
            new Texture("building-textures/recreation-bowling.png"),
        };

        ImageButton[] buildingButtons = new ImageButton[buildingTextures.length];
        Label[] buildingLabels = new Label[buildingTextures.length];
        String[] buildingNames = {"Potato Shop", "Space science", "Motel Mars",
                                  "Zero-g bowling"};

        Table buildingTable = new Table();

        addImagesToTable(buildingTable, buildingButtons, buildingTextures,
                         buildingLabels, buildingNames);
        addClickListenerToImageButtons(buildingButtons, buildingNames);
        buildingTable.pack();
        buildingTable.padTop(30).padBottom(30);

        this.scrollPane = new ScrollPane(buildingTable, skin);
        scrollPane.pack();
        scrollPane.setVisible(false);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.add(scrollPane).center();

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
            buildingButtons[i] = createStyledImageButton(buildingTextures[i], skin);
            // Create label for each building
            buildingLabels[i] = new Label(buildingNames[i], skin);
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
    private void addClickListenerToImageButtons(ImageButton[] buildingButtons, String[] buildingNames) {
        for (int i = 0; i < buildingButtons.length; i++) {
            int finalI = i;
            buildingButtons[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Clicked on building: " + buildingNames[finalI]);
                    // To-do: implement interaction logic
                }
            });
        }
    }

    public void dispose() {
        for (Texture texture : buildingTextures) {
            texture.dispose();
        }
    }
}
