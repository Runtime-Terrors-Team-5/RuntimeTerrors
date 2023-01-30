package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Input.Keys;
import Screens.Play_Screen;


import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import static java.lang.Math.round;


public class Player {

    private final World world;
    // attributes
    public Vector2 position;
    public Sprite sprite;
    public Sprite inventorySprite;
    public inventory inventory;
    private boolean action;

    public float speed = 300; // player movement speed

    // rectangle for collisions
    public Rectangle Rectangle;

    // methods
    public Player(TextureRegion img, TextureRegion img2, TextureRegion img3, World world){
        action = false;
        sprite = new Sprite(img);
        inventory = new inventory(img2, img3);  // creates player inventory sprite
        inventorySprite = new Sprite(img2);
        sprite.setScale(2);
        position = new Vector2(Gdx.graphics.getWidth()/2-300,sprite.getScaleY()*sprite.getHeight()/2+500);
        this.world = world;

        BodyDef bdef = new BodyDef();   // creates player object
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        Body b2body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getX()/2, sprite.getY()/2);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        this.Rectangle = new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    // maps keys to actions
    public void Update(float deltaTime)                                                                                              {
        //runs a check to see if any recipes are craftable
        inventory.craftableCheck();
        // WASD movement input processing
        // only allow this if the chef pointer is pointing to this chef
        // checks for collisions by rnnning a loop
        this.Rectangle = new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (Body b:bodies){

            if (b.getFixtureList().get(0).getUserData()==null){continue;}

            if (b.getFixtureList().get(0).getUserData().getClass().equals(Player.class)){continue;}

            InteractivetileObject temp = (InteractivetileObject) b.getFixtureList().get(0).getUserData();

            if(Play_Screen.chefSelection[Play_Screen.chefPointer]!=this){continue;}

            if (!Intersector.overlaps(temp.getRect(), Rectangle)) {
                if (action){continue;}
                   // player movement input
                if (Gdx.input.isKeyPressed(Keys.A)) {position.x -= 0.2;}
                if (Gdx.input.isKeyPressed(Keys.D)) {position.x += 0.2;}
                if (Gdx.input.isKeyPressed(Keys.S)) {position.y -= 0.2;}
                if (Gdx.input.isKeyPressed(Keys.W)) {position.y += 0.2;}
            }  // if player collides with objects it blocks further movement
            else{
                if (position.x - (((InteractivetileObject) b.getFixtureList().get(0).getUserData()).bounds.x)<= 0){position.x -= 5;}
                if (position.y - (((InteractivetileObject) b.getFixtureList().get(0).getUserData()).bounds.y)<= 0){position.y -= 5;}
                if (position.x + (((InteractivetileObject) b.getFixtureList().get(0).getUserData()).bounds.x)>= 0){position.x += 5;}
                if (position.y + (((InteractivetileObject) b.getFixtureList().get(0).getUserData()).bounds.y)>= 0){position.y += 5;}
            }


    }}

    public void Draw(SpriteBatch batch){
        Update(Gdx.graphics.getDeltaTime());
        sprite.setPosition(position.x, position.y);

        sprite.setSize(50,50);

        //inventorySprite.setPosition(, );
        if(Play_Screen.chefSelection[Play_Screen.chefPointer]==this){
            inventory.drawInventory(batch,position.x+300,position.y);
        }

        sprite.draw(batch);

    }

    public com.badlogic.gdx.math.Rectangle getRectangle() {
        return this.Rectangle;
    }

    public void setActionTrue(){action=true;}

    public void setActionFalse(){action=false;}
}
