package com.mygdx.game;

import Screens.Play_Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class Cutting_Counter extends InteractivetileObject{

    private float progress;
    public Cutting_Counter(World world, TiledMap map, MapObject object) {
        super(world, map, object);
        fixture.setUserData(this);
        progress = 10;
    }

    public void takeItem(foodItems item){
        if (item!=null){
            currentItem = item;
            System.out.println("cutting");
            Play_Screen.activeStations.add(this);
        }
    }

    public void removeItem(Player chef){
        chef.inventory.addItem(currentItem);
        Play_Screen.activeStations.remove(this);
        currentItem = null;
    }

    public void progress(float dt){
        progress -= dt;
        if (progress<0){
            if (currentItem.stage!=2){
                currentItem.nextStage();
                progress = 10;
            }
        }
        System.out.println(progress);
    }

    public boolean isProgressable(){
        if (progress>0){
            if (currentItem.stage != 2){
                return true;
            }
        }
        return false;
    }

    public void drawProgress(Batch batch){
        batch.draw(currentItem.getItemSprite(),bounds.getX(),bounds.getY());
    }
}
