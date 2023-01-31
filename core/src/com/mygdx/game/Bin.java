package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

/**
 * handles bin creation and disposing
 */
public class Bin extends InteractivetileObject {
    /**
     * instantiates bin entity fixture
     * @param world
     * @param map
     * @param bounds
     */
    public Bin(World world, TiledMap map, MapObject bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);

    }

    /**
     * deletes top of stack
     * @param chef
     */
    public void DisposeTrash(Player chef) {
        chef.inventory.returnHead();
    }
}
