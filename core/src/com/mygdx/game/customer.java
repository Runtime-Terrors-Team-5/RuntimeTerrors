package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class customer {
    private foodItems order;
    private TextureRegion sprite;
    public customer(foodItems order, TextureAtlas atlas){
        this.order = order;
        this.sprite = atlas.findRegion("C_Yellow_N");
    }

    public String getItemName(){
        return order.getItemName();
    }

    public TextureRegion getOrderSprite(){
        return order.getItemSprite();
    }
    public TextureRegion getSprite() {
        return sprite;
    }
}
