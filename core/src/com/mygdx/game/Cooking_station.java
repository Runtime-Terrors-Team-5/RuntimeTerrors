package com.mygdx.game;

import Screens.Play_Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import java.util.Arrays;

public class Cooking_station extends InteractivetileObject {

    private float progress;

    public Cooking_station(World world, TiledMap map, MapObject object) {
        super(world, map, object);
        fixture.setUserData(this);
        AcceptableItems = new String[]{"E_Patty", "E_Bun"};
        progress = 10;
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
        progress = 10;
    }

    public void progress(float dt) {
        progress -= dt;
        if (progress > 0) {
            currentItem.setProgressableFalse();
        } else {
            currentItem.setProgressableTrue();
        }
    }

    @Override
    public void nextStage() {
        if (currentItem.isProgressable()) {

            currentItem.nextStage();
            progress = 10;
        }
    }

    public boolean isProgressing() {
        if (progress > 0) {
            return currentItem.stage != 2;
        }
        return false;
    }

    public void drawProgress(Batch batch, OrthographicCamera cam) {
        DrawProgressBar(cam, progress);

        batch.begin();
        batch.draw(currentItem.getItemSprite(), bounds.getX(), bounds.getY());
        batch.end();
    }

    public boolean acceptableItem(String item) {
        return Arrays.asList(AcceptableItems).contains(item);
    }
}
