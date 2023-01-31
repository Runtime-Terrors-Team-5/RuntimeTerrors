package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Creates customer sprite and handles its actions
 */
public class customer {

    private foodItems order;
    private TextureRegion sprite;

    /**
     * Creates customer sprite and sets its order
     * @param order
     * @param atlas
     */
    public customer(foodItems order, TextureAtlas atlas) {
        this.order = order;
        this.sprite = atlas.findRegion("C_Yellow_N");
    }

    /**
     * returns item name
     * @return items name (string)
     */
    public String getItemName() {
        return order.getItemName();
    }

    /**
     *
     * @return orders sprite
     */
    public TextureRegion getOrderSprite() {
        return order.getItemSprite();
    }

    /**
     *
     * @return sprite
     */
    public TextureRegion getSprite() {
        return sprite;
    }
}
