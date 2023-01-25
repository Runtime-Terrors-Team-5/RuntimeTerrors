package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class Cooking_station extends InteractivetileObject{

    public Cooking_station(World world, TiledMap map, MapObject bounds){
        super(world, map, bounds);
        fixture.setUserData(this);

    }
}
