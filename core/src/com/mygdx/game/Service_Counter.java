package com.mygdx.game;
import Screens.Play_Screen;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Queue;
import java.util.Stack;

public class Service_Counter extends InteractivetileObject{

    public Service_Counter(World world, TiledMap map, MapObject bounds){
        super(world, map, bounds);
        fixture.setUserData(this);

    }

    public boolean CheckOrders(foodItems Food, Queue<String> Orders){
        if (Orders.peek().equals(Food.getItemName())){return true;}
        else {return false;}


    }
}
