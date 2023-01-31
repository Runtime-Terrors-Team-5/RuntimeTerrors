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

/**
 *handles player attributes and methods (actions)
 */
public class Player {
    private final World world;
    // attributes
    public Vector2 position;
    public Sprite sprite;
    public Sprite inventorySprite;
    public inventory inventory;
    private boolean action;

    public float speed = 12; // player movement speed

    // rectangle for collisions
    public Rectangle Rectangle;
    public boolean overlap;

    /**
     * instantiates chef, inventory , position and its world
     * @param img actual character chef
     * @param img2 inventory
     * @param img3 position
     * @param world game world
     */
    // methods
    public Player(TextureRegion img, TextureRegion img2, TextureRegion img3, World world){
        action = false;
        sprite = new Sprite(img);
        inventory = new inventory(img2, img3);
        inventorySprite = new Sprite(img2);
        sprite.setScale(2);
        position = new Vector2(Gdx.graphics.getWidth()/2-300,sprite.getScaleY()*sprite.getHeight()/2+500);
        this.world = world;

        BodyDef bdef = new BodyDef();
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

    /**
     * maps keys to actions and object collision
     * @param deltaTime game time
     */

    public void Update(float deltaTime)                                                                                              {
        //runs a check to see if any recipes are craftable
        inventory.craftableCheck();
        // WASD movement input processing
        // only allow this if the chef pointer is pointing to this chef
        // checks for collisions by running a loop
        Rectangle = new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        overlap = false;

        for (Body b:bodies) {

            if (b.getFixtureList().get(0).getUserData() == null) {
                continue;
            }

            if (b.getFixtureList().get(0).getUserData().getClass().equals(Player.class)) {
                continue;
            }

            InteractivetileObject temp = (InteractivetileObject) b.getFixtureList().get(0).getUserData();


            if (action) {
                continue;
            }
            InteractivetileObject tempInt = (InteractivetileObject) b.getFixtureList().get(0).getUserData();
            // player movement input

            if (Gdx.input.isKeyPressed(Keys.A)) {
                Rectangle = new Rectangle(sprite.getX()+(sprite.getWidth()/4) - 60, sprite.getY()+(sprite.getWidth()/4), sprite.getWidth()/2, sprite.getHeight()/2);
                if ((Intersector.overlaps(temp.getRect(), Rectangle))) {overlap = true;}
            }
            if (Gdx.input.isKeyPressed(Keys.D)) {
                Rectangle = new Rectangle(sprite.getX()+(sprite.getWidth()/4) + 10 , sprite.getY()+(sprite.getWidth()/4), sprite.getWidth()/2, sprite.getHeight()/2);
                if ((Intersector.overlaps(temp.getRect(), Rectangle))) {overlap = true;}
            }
            if (Gdx.input.isKeyPressed(Keys.S)) {
                Rectangle = new Rectangle(sprite.getX()+(sprite.getWidth()/4), sprite.getY()+(sprite.getWidth()/4) - 60, sprite.getWidth()/2, sprite.getHeight()/2);
                if ((Intersector.overlaps(temp.getRect(), Rectangle))) {overlap = true;}
            }
            if (Gdx.input.isKeyPressed(Keys.W)) {
                Rectangle = new Rectangle(sprite.getX()+(sprite.getWidth()/4), sprite.getY()+(sprite.getWidth()/4) + 20, sprite.getWidth()/2, sprite.getHeight()/2);
                if ((Intersector.overlaps(temp.getRect(), Rectangle))) {overlap = true;}
            }
        }
        for (Body b:bodies) {
            if (Play_Screen.chefSelection[Play_Screen.chefPointer] != this) {
                continue;
            }
            if (action) {
                continue;
            }
            if(overlap == false) {
                if (Gdx.input.isKeyPressed(Keys.A)) {
                    position.x -= speed * Gdx.graphics.getDeltaTime();
                }
                if (Gdx.input.isKeyPressed(Keys.D)) {
                    position.x += speed * Gdx.graphics.getDeltaTime();
                }
                if (Gdx.input.isKeyPressed(Keys.S)) {
                    position.y -= speed * Gdx.graphics.getDeltaTime();
                }
                if (Gdx.input.isKeyPressed(Keys.W)) {
                    position.y += speed * Gdx.graphics.getDeltaTime();
                }
            }
        }
    }

    /**
     * Draws chefs and relates sprites
     * @param batch sprites
     */
    public void Draw(SpriteBatch batch){
        Update(Gdx.graphics.getDeltaTime());
        sprite.setPosition(position.x, position.y);

        sprite.setSize(50,50);

        // inventorySprite.setPosition(, );
        if(Play_Screen.chefSelection[Play_Screen.chefPointer]==this){
            inventory.drawInventory(batch,position.x+300,position.y);
        }

        sprite.draw(batch);

    }

    /**
     *
     * @return Rectangle
     */
    public com.badlogic.gdx.math.Rectangle getRectangle() {
        return this.Rectangle;
    }

    /**
     * sets action to true (boolean) , which stops chef movement
     */
    public void setActionTrue(){action=true;}

    /**
     *sets action to false (boolean) , which stops chef movement
     */
    public void setActionFalse(){action=false;}
}
