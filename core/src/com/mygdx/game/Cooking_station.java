package com.mygdx.game;

import Screens.Play_Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import java.util.Arrays;

/**
 * handles cooking station interactions
 */
public class Cooking_station extends InteractivetileObject {

    private float progress;

    /**
     * instantates the cooking stations
     * @param world
     * @param map
     * @param object
     */
    public Cooking_station(World world, TiledMap map, MapObject object) {
        super(world, map, object);
        fixture.setUserData(this);
        AcceptableItems = new String[]{"E_Patty", "E_Bun"};
        progress = 10;
    }

    /**
     * takes item from chef and adds to station
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
     * removes item from station and adds to chef stack
     * @param chef
     */
    public void removeItem(Player chef) {
        chef.inventory.addItem(currentItem);
        Play_Screen.activeStations.remove(this);
        currentItem = null;
        progress = 10;
    }

    /**
     * decides if item is progressable
     * when progress count down to below 0 the item is progressable
     * and require interaction from player to progress
     * @param dt
     */
    public void progress(float dt) {
        progress -= dt;
        if (progress > 0) {
            currentItem.setProgressableFalse();
        } else {
            currentItem.setProgressableTrue();
        }
    }

    /**
     * adds progess to current items
     */
    @Override
    public void nextStage() {
        if (currentItem.isProgressable()) {

            currentItem.nextStage();
            progress = 10;
        }
    }

    /**
     * checks if item is progressing to another stage
     * @return boolean or item progress
     */
    public boolean isProgressing() {
        if (progress > 0) {
            return currentItem.stage != 2;
        }
        return false;
    }

    /**
     *  draws cooking progress and object
     * @param batch sprites
     * @param cam game camera
     */
    public void drawProgress(Batch batch, OrthographicCamera cam) {
        DrawProgressBar(cam, progress);

        batch.begin();
        batch.draw(currentItem.getItemSprite(), bounds.getX(), bounds.getY());
        batch.end();
    }

    /**
     * checks if the item given can be cooked
     * @param item
     * @return same item if it's in the array
     */
    public boolean acceptableItem(String item) {
        return Arrays.asList(AcceptableItems).contains(item);
    }
}
