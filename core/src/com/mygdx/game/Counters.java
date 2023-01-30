package com.mygdx.game;

import Screens.Play_Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class Counters extends InteractivetileObject {

    public Counters(World world, TiledMap map, MapObject object) {
        super(world, map, object);
        fixture.setUserData(this);
    }

    public void takeItem(foodItems item) {
        if (item != null) {
            currentItem = item;
            System.out.println("cooking");
            Play_Screen.activeStations.add(this);
        }
    }

    public void removeItem(Player chef) {
        chef.inventory.addItem(currentItem);
        Play_Screen.activeStations.remove(this);
        currentItem = null;
    }

    public void drawProgress(Batch batch, OrthographicCamera cam) {
        batch.begin();
        batch.draw(currentItem.getItemSprite(), bounds.getX(), bounds.getY());
        batch.end();
    }

}
