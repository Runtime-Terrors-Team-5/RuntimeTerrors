package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * food items creation class
 */
public class foodItems {

    String itemName;
    TextureAtlas atlas;
    int stage;
    private boolean isProgressable;

    /**
     *instantiates a new item fetched from assets
     * @param itemName food item's name
     */
    public foodItems(String itemName) {
        atlas = new TextureAtlas(Gdx.files.internal("ENG1_Assets_V2.atlas"));
        this.itemName = itemName;
        stage = 0;
        isProgressable = false;
        if (atlas.findRegion(this.itemName, stage) == null) {
            stage = -1;
        }


    }

    /**
     * sets progressable
     */
    public void setProgressableTrue() {
        isProgressable = true;
    }

    /**
     * sets false progressable
     */
    public void setProgressableFalse() {
        isProgressable = false;
    }

    /**
     * decides if its progressable
     * @return boolean
     */
    public boolean isProgressable() {
        if (isProgressable) {
            if (stage != 2) {
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    /**
     * decides its next stage
     */
    public void nextStage() {
        if (stage != 3) {
            stage += 1;
        }
    }

    /**
     * for testing auto turns the item to fail stage
     * object ingredient is unusable (burnt) if reaches a burnt stage
     */

    public void fail() {
        if (itemName == "E_Bun") {
            stage = 2;
        } else {
            stage = 3;
        }
    }

    /**
     * returns Items name
     * @return String
     */
    public String getItemName() {
        return itemName;
    }

    public int getStage() {
        return stage;
    }

    /**
     * finds the correct sprite
     * @return the sprite and stage
     */
    public TextureRegion getItemSprite() {
        //System.out.println(stage);
        return atlas.findRegion(itemName, stage);
    }
}
