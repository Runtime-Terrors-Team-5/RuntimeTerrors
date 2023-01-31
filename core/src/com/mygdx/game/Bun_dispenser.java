package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

/**
 * handles object creation and dispensing
 */
public class Bun_dispenser extends InteractivetileObject {
    /**
     * creates object
     * @param world
     * @param map
     * @param object
     */
    public Bun_dispenser(World world, TiledMap map, MapObject object) {
        super(world, map, object);
        fixture.setUserData(this);
        this.Item = "E_Bun";

    }

    /**
     * adds bun to stack
     * @param chef
     */
    public void DispenseItem(Player chef) {
        if (chef.inventory.isSpace()) {
            chef.inventory.addItem(new foodItems(Item));
        }
    }
}
