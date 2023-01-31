package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 *  interface / parent class for all interactable objects on screen
 */

public class InteractivetileObject {

    private final MapObject object;
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected foodItems currentItem;
    protected Body body;
    protected Fixture fixture;
    protected String Item;

    protected String[] AcceptableItems;

    /**
     *  base blue print for interactable objects
     * @param world
     * @param map
     * @param object
     */
    public InteractivetileObject(World world, TiledMap map, MapObject object) {
        this.world = world;
        this.map = map;
        this.object = object;
        this.bounds = ((RectangleMapObject) object).getRectangle();
        // instantiates object on map
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        bdef.type = BodyDef.BodyType.StaticBody;
        // can add / Mygame.PPM
        bdef.position.set((float) (bounds.getX() + 50), (float) (bounds.getY() + 50));
        System.out.println(String.format("X:%s Y:%s", bounds.getWidth(), bounds.getHeight()));
        body = world.createBody(bdef);
        shape.setAsBox(bounds.getWidth() / 2, bounds.getHeight() / 2);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }

    /**
     *
     * @return bounds
     */
    public Rectangle getRect() {
        return bounds;
    }

    /**
     * progress method
     * @param dt
     */
    public void progress(float dt) {
    }

    /**
     *
     * @return
     */
    public foodItems getCurrentItem() {
        return currentItem;
    }

    /**
     * if item is progressing
     * @return
     */
    public boolean isProgressing() {
        return false;
    }

    /**
     * disposes stack head
     * @param chef
     */
    public void DisposeTrash(Player chef) {
    }

    /**
     *
     * @param batch
     * @param cam
     */
    public void drawProgress(Batch batch, OrthographicCamera cam) {
    }

    /**
     *
     * @param player
     */
    public void DispenseItem(Player player) {
    }

    /**
     *
     * @param item
     * @return boolean
     */
    public boolean acceptableItem(String item) {
        return false;
    }

    /**
     * draws the progress bar , sets its object shape, color and attributes
     * @param cam game cam for object
     * @param progress amount of progress
     */
    protected void DrawProgressBar(OrthographicCamera cam, float progress) {
        ShapeRenderer shape = new ShapeRenderer();
        //Draws the red background
        shape.setProjectionMatrix(cam.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.RED);
        shape.rect(bounds.getX(), bounds.getY(), 95, 20);
        shape.end();
        //Draws the green box progress being filled
        shape.setProjectionMatrix(cam.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.GREEN);
        shape.rect(bounds.getX(), bounds.getY(), 95 - (95 * (progress / 10)), 20);
        shape.end();
        shape.dispose();
    }

    /**
     *
     */
    public void nextStage() {
        currentItem.nextStage();
    }


}
