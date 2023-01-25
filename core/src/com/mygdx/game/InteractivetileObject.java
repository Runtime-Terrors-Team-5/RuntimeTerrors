package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

import java.awt.*;

public class InteractivetileObject {

    private final MapObject object;
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
    protected String Item;


    public InteractivetileObject(World world, TiledMap map, MapObject object){
        this.world = world;
        this.map = map;
        this.object = object;
        this.bounds = ((RectangleMapObject) object).getRectangle();

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        bdef.type = BodyDef.BodyType.StaticBody;
        // can add / Mygame.PPM
        bdef.position.set((float) (bounds.getX() + bounds.getWidth()/2), (float) (bounds.getY()+bounds.getHeight()/10));

        body = world.createBody(bdef);
        shape.setAsBox(bounds.getX()/2/3, bounds.getY()/2/10);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);

    }


    public Rectangle getRect(){
        return bounds;
    }
    public void DisposeTrash(Player chef) {
    }


    public void DispenseItem(Player player) {
    }
}
