package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class foodItems {
    String itemName;
    TextureAtlas atlas;
    int stage;
    public foodItems(String itemName){
        atlas = new TextureAtlas(Gdx.files.internal("ENG1_Assets_V2.atlas"));
        this.itemName = itemName;
        stage=0;
        if (atlas.findRegion(this.itemName,stage) == null){stage=-1;}


    }
    public void nextStage(){
        if (stage != 3){stage += 1;}
    }

    public void fail(){stage=3;}

    public TextureRegion getItemSprite() {
        //System.out.println(stage);
        return atlas.findRegion(itemName, stage);
    }
}
