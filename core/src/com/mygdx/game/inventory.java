package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import static Screens.Play_Screen.recipes;

public class inventory {

    public foodItems[] stack;
    private TextureRegion notCraftableImg;
    private TextureRegion craftableImg;
    private boolean craftable;
    private String craftableItem;
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

    public boolean isSpace(){
        if (stack[2] == null){return true;}

        else{return false;}
    }

    public void drawInventory(SpriteBatch batch, float x, float y){
        //10,100
        if (craftable){
            batch.draw(craftableImg,x,y);
        }
        else{
            batch.draw(notCraftableImg,x,y);
        }

        for (int i = 0; i < stack.length; i++) {
            if (stack[i]!= null){
                batch.draw(stack[i].getItemSprite(),x+55,y+(i*70)+(i+1*10)+150);
            }
        }

    }



    public void craftableCheck(){
        craftable = false;
        Iterator<Map.Entry<String, Triplet>> recipeIterator = recipes.entrySet().iterator();
        // Iterates through the recipe list of key value pair
        while (recipeIterator.hasNext()){
            //truthList is to keep count of the number of ingredients that match the recipe
            HashSet truthList = new HashSet<>(3);
            Map.Entry<String, Triplet> ingredients = recipeIterator.next();
            for (Object obj : ingredients.getValue()) {
                /*
                System.out.println(newMap.getValue());
                System.out.println(newMap.getKey());
                 */
                boolean cont = false;

                for (int i = 0; i < stack.length; i++) {

                    if (stack[i] == null){break;}
                    if (obj.equals(new Pair<>(stack[i].getItemName(), stack[i].getStage()))) {
/*
                        System.out.println(obj);
                        System.out.println(new Pair<>(stack[i].getItemName(), stack[i].getStage()));
                        System.out.println(obj.equals(new Pair<>(stack[i].getItemName(), stack[i].getStage())));
*/
                        //one or more valid ingredient for this recipe cont is true to continue the search
                        cont = true;
                        //the ingredient is added to the list to track how many valid ingredients there are
                        truthList.add(obj);
                        //go to check the next ingredient
                        break;
                    }
                }
                //recipes need 3 ingredients if one is missing then that is not valid recipe so check next recipe
                if (!cont) {break;}
            }
            //checks if there is 3 ingredients which match the recipe and then variables enter a craftable state
            if (truthList.size()==3){
                craftableItem = ingredients.getKey();
                craftable = true;}

        }
    }

    public boolean isCraftable() {
        return craftable;
    }

    //empties the inventory and inserts the crafted item
    public void craft(){
        stack[0]=null;
        stack[1]=null;
        stack[2]=null;
        stack[0]= new foodItems(craftableItem);
    }
}
