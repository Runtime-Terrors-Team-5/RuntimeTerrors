package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;


// interface / parent class for all interactable objects on screen
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



    public InteractivetileObject(World world, TiledMap map, MapObject object){
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
        bdef.position.set((float) (bounds.getX() + 50 ), (float) (bounds.getY()+50));
        System.out.println(String.format("X:%s Y:%s",bounds.getWidth(),bounds.getHeight()));
        body = world.createBody(bdef);
        shape.setAsBox(bounds.getWidth()/2, bounds.getHeight()/2);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }

    public Rectangle getRect(){
        return bounds;
    }

    public void progress(float dt){}

    public foodItems getCurrentItem(){return currentItem;}
    public boolean isProgressable(){
        return false;
    }
    public void DisposeTrash(Player chef) {
    }

    public void drawProgress(Batch batch, OrthographicCamera cam){}

    public void DispenseItem(Player player) {
    }

    public boolean acceptableItem(String item){return false;}

    protected void DrawProgressBar(OrthographicCamera cam, float progress){
        ShapeRenderer shape = new ShapeRenderer();
        //Draws the red background
        shape.setProjectionMatrix(cam.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.RED);
        shape.rect(bounds.getX(), bounds.getY(), 95,20);
        shape.end();
        //Draws the green box progress being filled
        shape.setProjectionMatrix(cam.combined);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.GREEN);
        shape.rect(bounds.getX(), bounds.getY(), 95-(95*(progress/10)),20);
        shape.end();
        shape.dispose();
    }


}
