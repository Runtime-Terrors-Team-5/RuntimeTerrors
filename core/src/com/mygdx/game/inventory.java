package com.mygdx.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashSet;

public class inventory {

    private foodItems[] stack;
    private TextureRegion notCraftableImg;
    private TextureRegion craftableImg;
    private boolean craftable;
    public inventory(TextureRegion img1, TextureRegion img2){
        notCraftableImg =  img1;
        craftableImg = img2;
        craftable = false;
        stack = new foodItems[3];
    }

    public foodItems getIndex(int Index){
        return (foodItems) stack[Index];
    }

    public boolean addItem(foodItems item){
        if (stack[2]==null){
            stack[2] = stack[1];
            stack[1] = stack[0];
            stack[0] = item;
            return true;
        } else if (stack[1]==null) {
            stack[1] = stack[0];
            stack[0] = item;
            return true;
        } else if (stack[0]==null) {
            stack[0] = item;
            return true;
        }
        return false;
    }

    public foodItems returnHead(){
        foodItems temp = (foodItems) stack[0];
        stack[0] = stack[1];
        stack[1] = stack[2];
        stack[2] = null;
        return temp;
    }

    public void drawInventory(SpriteBatch batch, float x, float y){
        //10,100
        Pixmap background;
        if (craftable){
            batch.draw(craftableImg,x,y);
        }
        else{
            batch.draw(notCraftableImg,x,y);
        }

        for (int i = 0; i < stack.length; i++) {
            if (stack[i]!= null){
                batch.draw(stack[i].getItemSprite(),x+35,y+(i*70)+(i+1*10)+150);
            }
        }

    }





}
