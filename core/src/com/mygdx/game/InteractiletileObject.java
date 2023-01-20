package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.*;

import java.awt.*;

public class InteractiletileObject {

    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public InteractiletileObject(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        bdef.type = BodyDef.BodyType.StaticBody;
        // can add / Mygame.PPM
        bdef.position.set((float) (bounds.getX() + bounds.getWidth()/2), (float) (bounds.getY()+bounds.getHeight()/2));


        body = world.createBody(bdef);

        fdef.shape = shape;
        fixture = body.createFixture(fdef);



    }



}
