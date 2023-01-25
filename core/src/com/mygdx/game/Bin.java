package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.*;

import java.awt.Rectangle;
public class Bin extends InteractivetileObject {
    public Bin(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        fixture.setUserData(this);


    }

    public void DisposeTrash(String trash){


    }
}
