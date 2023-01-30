package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class foodItems {

    String itemName;
    TextureAtlas atlas;
    int stage;
    private boolean isProgressable;

    // instantiates a new item fetched from assets
    public foodItems(String itemName) {
        atlas = new TextureAtlas(Gdx.files.internal("ENG1_Assets_V2.atlas"));
        this.itemName = itemName;
        stage = 0;
        isProgressable = false;
        if (atlas.findRegion(this.itemName, stage) == null) {
            stage = -1;
        }


    }

    public void setProgressableTrue() {
        isProgressable = true;
    }

    public void setProgressableFalse() {
        isProgressable = false;
    }

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

    public void nextStage() {
        if (stage != 3) {
            stage += 1;
        }
    }

    //for testing auto turns the item to fail stage
    public void fail() {
        if (itemName == "E_Bun") { // object ingredient is unusable (burnt) if reaches a burnt stage
            stage = 2;
        } else {
            stage = 3;
        }
    }

    public String getItemName() {
        return itemName;
    }

    public int getStage() {
        return stage;
    }

    public TextureRegion getItemSprite() {
        //System.out.println(stage);
        return atlas.findRegion(itemName, stage);
    }
}
