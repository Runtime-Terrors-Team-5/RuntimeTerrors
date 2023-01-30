package com.mygdx.game;

import Screens.Play_Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import java.util.Arrays;

public class Cutting_Counter extends InteractivetileObject {

    private float progress;

    public Cutting_Counter(World world, TiledMap map, MapObject object) {
        super(world, map, object);
        fixture.setUserData(this);
        AcceptableItems = new String[]{"E_Onion", "E_Lettuce", "E_Tomato"};
        progress = 10;
    }

    public void takeItem(foodItems item) {
        if (item != null) {
            currentItem = item;
            System.out.println("cutting");
            Play_Screen.activeStations.add(this);
        }
    }

    public void removeItem(Player chef) {
        chef.inventory.addItem(currentItem);
        Play_Screen.activeStations.remove(this);
        currentItem = null;
        progress = 10;
    }

    public void progress(float dt) { // progress bar
        progress -= dt;
        if (progress < 0) {
            if (currentItem.stage != 2) {
                currentItem.nextStage();
                progress = 10;
            }
        }
    }

    public boolean isProgressing() {
        if (progress > 0) {
            if (currentItem.stage != 2) {
                return true;
            }
        }
        return false;
    }

    public void drawProgress(Batch batch, OrthographicCamera cam) { // draws progress bar on screen
        DrawProgressBar(cam, progress);

        batch.begin();
        batch.draw(currentItem.getItemSprite(), bounds.getX(), bounds.getY());
        batch.end();
    }

    // checks if item argument is in the items which can be cut
    public boolean acceptableItem(String item) {
        if (Arrays.asList(AcceptableItems).contains(item)) {
            return true;
        }

        return false;
    }
}
