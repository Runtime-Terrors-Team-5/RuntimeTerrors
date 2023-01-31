package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Creates patty objects and methods
 */
public class Patty_dispenser extends InteractivetileObject {
    /**
     * constructor for the patty fixture
     * @param world
     * @param map
     * @param object
     */
    public Patty_dispenser(World world, TiledMap map, MapObject object) {
        super(world, map, object);
        fixture.setUserData(this);
        this.Item = "E_Patty";

    }

    /**
     * adds patty to chef inventory in interaction
     * @param chef
     */
    public void DispenseItem(Player chef) {
        if (chef.inventory.isSpace()) {
            chef.inventory.addItem(new foodItems(Item));
        }
    }
}
