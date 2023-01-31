package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import java.util.Queue;

/**
 * Creates service counter objects
 */
public class Service_Counter extends InteractivetileObject {
    /**
     * constructor for a Service counter
     * @param world
     * @param map
     * @param bounds
     */
    public Service_Counter(World world, TiledMap map, MapObject bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);

    }

    /**
     * checks if order(argument) is matching / correct
     * @param Food
     * @param Orders
     * @return if its matching or not eg correct
     */
    public boolean CheckOrders(foodItems Food, Queue<customer> Orders) {
        if (Orders.peek().getItemName().equals(Food.getItemName())) {
            return true;
        } else {
            return false;
        }


    }
}
