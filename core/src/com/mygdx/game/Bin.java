package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class Bin extends InteractivetileObject {

    public Bin(World world, TiledMap map, MapObject bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);

    }

    public void DisposeTrash(Player chef) {
        chef.inventory.returnHead();
    }
}
