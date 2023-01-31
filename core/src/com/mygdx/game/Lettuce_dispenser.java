package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

/**
 * handles lettuce dispenser creation and interation
 */
public class Lettuce_dispenser extends InteractivetileObject {
    /**
     * instantiates lettuce dispenser
     * @param world
     * @param map
     * @param object
     */
    public Lettuce_dispenser(World world, TiledMap map, MapObject object) {
        super(world, map, object);
        fixture.setUserData(this);
        this.Item = "E_Lettuce";

    }

    /**
     * adds lettuce to chef stack
     * @param chef
     */
    public void DispenseItem(Player chef) {
        if (chef.inventory.isSpace()) {
            chef.inventory.addItem(new foodItems(Item));
        }
    }
}
