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

    public BuildingUIManager(Stage stage) {
        this.skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
        buildingTextures = new Texture[] {
            new Texture("building-textures/eat-potato-shop.png"),
            new Texture("building-textures/learn-space-science.png"),
            new Texture("building-textures/sleep-motel-mars.png"),
            new Texture("building-textures/recreation-bowling.png"),
        };
        //Image[] buildingImages = new Image[buildingTextures.length];
        ImageButton[] buildingButtons = new ImageButton[buildingTextures.length];
        Label[] buildingLabels = new Label[buildingTextures.length];
        String[] buildingNames = {"Potato Shop", "Space science dept.", "Motel Mars",
                                  "Zero-g bowling"};

        Table buildingTable = new Table();
        buildingTable.top();
        buildingTable.padBottom(40);
        buildingTable.setName("BUILDING TABLE");
        //buildingTable.debug();

        addImagesToTable(buildingTable, buildingButtons, buildingTextures,
                         buildingLabels, buildingNames);
        addClickListenerToImageButtons(buildingButtons, buildingNames);

        buildingTable.pack();

        ScrollPane scrollPane = new ScrollPane(buildingTable, skin);
        scrollPane.setScrollingDisabled(false, true);
        scrollPane.setFadeScrollBars(false);
        //scrollPane.setFillParent(true);
        scrollPane.setSize(440, 320);
        scrollPane.setName("SCROLLPANE");
        //scrollPane.debug();

        Table mainTable = new Table();
        mainTable.bottom().right();
        mainTable.setFillParent(true);
        mainTable.add(scrollPane);
        //mainTable.debug();

        stage.addActor(mainTable);
        //checkHierarchy(stage);
    }

    /**
     * Adds image buttons to a table using an array of textures
     * @param table The table that will contain the images.
     * @param buildingButtons An array containing ImageButton objects created from
     *                       the building textures.
     * @param buildingTextures An array containing the textures of each building
     */
    private void addImagesToTable(Table table, ImageButton[] buildingButtons,
                                  Texture[] buildingTextures, Label[] buildingLabels,
                                  String[] buildingNames) {
        for (int i = 0; i < buildingTextures.length; i++) {
            Drawable buildingDrawable = new TextureRegionDrawable(new TextureRegion(buildingTextures[i]));
            ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
            buttonStyle.up = skin.getDrawable("button");
            buttonStyle.down = skin.getDrawable("button-down");
            buttonStyle.imageUp = buildingDrawable;
            // Create image object for each texture in the provided array
            buildingButtons[i] = new ImageButton(buttonStyle);
            buildingButtons[i].setName("BUTTON");
            // Create label for each building
            buildingLabels[i] = new Label(buildingNames[i], skin);
            buildingLabels[i].setName("LABEL");
            Table imageLabelTable = new Table();
            imageLabelTable.add(buildingButtons[i]);
            imageLabelTable.row();
            imageLabelTable.add(buildingLabels[i]).padTop(5);
            imageLabelTable.setName("IMG+LABEL TABLE");
            // Add the image object to the table
            table.add(imageLabelTable).pad(20);
        }
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
                    System.out.println("Clicked on building:" + buildingNames[finalI]);
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
