package com.mygdx.game;

import java.util.HashSet;

public class inventory {

    public Object[] stack;
    public inventory(){
        stack = new Object[3];
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





}
