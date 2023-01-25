package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class Onion_dispenser extends InteractivetileObject{
    private final String Item;

    public Onion_dispenser(World world, TiledMap map, MapObject object){
        super(world, map, object);
        fixture.setUserData(this);
        this.Item = "E_Onion";

    }
    public void DispenseItem(Player chef){
        if (chef.inventory.isSpace()) {
            chef.inventory.addItem(new foodItems(Item));
        }
    }
}
