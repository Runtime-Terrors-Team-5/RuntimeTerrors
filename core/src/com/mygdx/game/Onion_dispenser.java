package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

/**
 * extends interactivetileobject, handles onion dispenser creation and interaction
 */
public class Onion_dispenser extends InteractivetileObject {
    /**
     * creates dispenser fixture object
     * @param world
     * @param map
     * @param object
     */
    public Onion_dispenser(World world, TiledMap map, MapObject object) {
        super(world, map, object);
        fixture.setUserData(this);
        this.Item = "E_Onion";

    }

    /**
     * dispenses onion, adds to stack
     * @param chef
     */
    public void DispenseItem(Player chef) {
        if (chef.inventory.isSpace()) {
            chef.inventory.addItem(new foodItems(Item));
        }
    }
}
