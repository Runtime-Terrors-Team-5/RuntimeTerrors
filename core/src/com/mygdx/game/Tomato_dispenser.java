package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

/**
 * handles tomato dispenser interactables
 */
public class Tomato_dispenser extends InteractivetileObject {
    /**
     * Creates a tomato object fixture
     * @param world
     * @param map
     * @param object
     */
    public Tomato_dispenser(World world, TiledMap map, MapObject object) {
        super(world, map, object);
        fixture.setUserData(this);
        this.Item = "E_Tomato";

    }

    /**
     * adds a new tomato to the inventory
     * @param chef
     */
    public void DispenseItem(Player chef) {
        if (chef.inventory.isSpace()) {
            chef.inventory.addItem(new foodItems(Item));
        }
    }
}
