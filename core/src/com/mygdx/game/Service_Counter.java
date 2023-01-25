package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class Service_Counter extends InteractivetileObject{

    public Service_Counter(World world, TiledMap map, MapObject bounds){
        super(world, map, bounds);
        fixture.setUserData(this);

    }
}
