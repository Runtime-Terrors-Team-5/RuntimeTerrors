package com.mygdx.game;

import Screens.Play_Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import java.util.Arrays;

/**
 * handles cutting counter methods and interactions
 */
public class Cutting_Counter extends InteractivetileObject {

    private float progress;

    /**
     * instantiates counter
     * @param world
     * @param map
     * @param object
     */
    public Cutting_Counter(World world, TiledMap map, MapObject object) {
        super(world, map, object);
        fixture.setUserData(this);
        AcceptableItems = new String[]{"E_Onion", "E_Lettuce", "E_Tomato"};
        progress = 10;
    }

    /**
     * takes item from chef
     * @param item
     */
    public void takeItem(foodItems item) {
        if (item != null) {
            currentItem = item;
            System.out.println("cutting");
            Play_Screen.activeStations.add(this);
        }
    }

    /**
     *adds item to chef stack, removes item from station
     * @param chef
     */
    public void removeItem(Player chef) {
        chef.inventory.addItem(currentItem);
        Play_Screen.activeStations.remove(this);
        currentItem = null;
        progress = 10;
    }

    /**
     *progress bar
     * if progress countdown below 0 auto move the item to next stage but not fail stage
     * @param dt
     */
    public void progress(float dt) { 
        progress -= dt;
        if (progress < 0) {
            if (currentItem.stage != 2) {
                currentItem.nextStage();
                progress = 10;
            }
        }
    }

    /**
     * checks if item is progressing
     * @return boolean
     */
    public boolean isProgressing() {
        if (progress > 0) {
            if (currentItem.stage != 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * draws progress bar eg renders
     * @param batch
     * @param cam
     */
    public void drawProgress(Batch batch, OrthographicCamera cam) { // draws progress bar on screen
        DrawProgressBar(cam, progress);

        batch.begin();
        batch.draw(currentItem.getItemSprite(), bounds.getX(), bounds.getY());
        batch.end();
    }

    /**
     * checks if item argument is in the items which can be cut
     * @param item
     * @return boolean
     */

    public boolean acceptableItem(String item) {
        if (Arrays.asList(AcceptableItems).contains(item)) {
            return true;
        }

        return false;
    }
}
