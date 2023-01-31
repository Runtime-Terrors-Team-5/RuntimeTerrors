package com.mygdx.game;

import Screens.Play_Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

/**
 * handles counter interaction  , covers all types
 */
public class Counters extends InteractivetileObject {
    /**
     * instantiates counters ( for all types)
     * @param world
     * @param map
     * @param object
     */
    public Counters(World world, TiledMap map, MapObject object) {
        super(world, map, object);
        fixture.setUserData(this);
    }

    /**
     * addds item to counter
     * @param item
     */
    public void takeItem(foodItems item) {
        if (item != null) {
            currentItem = item;
            System.out.println("cooking");
            Play_Screen.activeStations.add(this);
        }
    }

    /**
     * removes item from counter
     * @param chef
     */
    public void removeItem(Player chef) {
        chef.inventory.addItem(currentItem);
        Play_Screen.activeStations.remove(this);
        currentItem = null;
    }

    /**
     * if counter is interactable with cooking it can draw a progress bar
     * @param batch
     * @param cam
     */
    public void drawProgress(Batch batch, OrthographicCamera cam) {
        batch.begin();
        batch.draw(currentItem.getItemSprite(), bounds.getX(), bounds.getY());
        batch.end();
    }

}
