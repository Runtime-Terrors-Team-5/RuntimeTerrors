package Screens;
import Tools.World_contact_listener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.Player;
import com.mygdx.game.foodItems;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.javatuples.Tuple;

import java.util.HashMap;

public class Play_Screen implements Screen {
    private MyGame game;
    Player chef1;
    Player chef2;
    Player chef3;
    public static Player[] chefSelection;
    public static int chefPointer;

    public static HashMap recipes;
    // sets screen size
    private Viewport game_port;
    private OrthographicCamera gamecam;
    TextureAtlas atlas;

    // imports the kitchen map
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    // world  generation
    private World world;
    private Box2DDebugRenderer b2dr;



    Texture texture; // temp

    public Play_Screen(MyGame game){
        this.game = game;

        recipes = new HashMap<>();
        recipes.put("E_Salad",new Triplet<>(new Pair<String, Integer>("E_Lettuce",2),
                new Pair<String, Integer>("E_Tomato",2),
                new Pair<String, Integer>("E_Onion",2)));
        recipes.put("E_Burger",new Triplet<>(new Pair<String, Integer>("E_Lettuce",2),
                new Pair<String, Integer>("E_Patty",2),
                new Pair<String, Integer>("E_Bun",1)));


        atlas = new TextureAtlas(Gdx.files.internal("ENG1_Assets_V2.atlas"));
        chef1 = new Player(atlas.findRegion("C_Blue_N"),atlas.findRegion("M_Blue_N"),atlas.findRegion("M_Blue_C123"));
        chef2 = new Player(atlas.findRegion("C_Green_N"),atlas.findRegion("M_Green_N"),atlas.findRegion("M_Green_C123"));
        chef3 = new Player(atlas.findRegion("C_Red_N"),atlas.findRegion("M_Red_N"),atlas.findRegion("M_Red_C123"));
        chefSelection = new Player[]{chef1, chef2, chef3};
        chefPointer = 0;

/*
        foodItems temp1 = new foodItems("E_Lettuce");
        foodItems temp2 = new foodItems("E_Patty");
        foodItems temp3 = new foodItems("E_Bun");
        temp1.nextStage();
        temp2.nextStage();
        temp2.nextStage();
        temp3.nextStage();
        chef1.inventory.addItem(temp1);
        chef1.inventory.addItem(temp2);
        chef1.inventory.addItem(temp3);
*/


        gamecam = new OrthographicCamera();
        game_port = new FitViewport(MyGame.V_WIDTH,MyGame.V_HEIGHT ,gamecam);
        // loads kitchen map
        maploader = new TmxMapLoader();
        map = maploader.load("kitchen_map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        // map camera
        gamecam.position.set(game_port.getWorldWidth(), game_port.getWorldHeight(), 0);
        // sets no gravity and creates the world
        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();
        world.setContactListener(new World_contact_listener());
        // identifies what the sprite has come into contact with

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        Body body;
        FixtureDef fdef = new FixtureDef();
        // Creates counters objects
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);
            
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        // creates service counter
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);

            fdef.shape = shape;
            body.createFixture(fdef);
        }

        // creates bin
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);

            fdef.shape = shape;
            body.createFixture(fdef);
        }


        // creates cutting stations
        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);

            fdef.shape = shape;
            body.createFixture(fdef);
        }


        // creates stoves
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);

            fdef.shape = shape;
            body.createFixture(fdef);
        }

        // creates tomato dispenser
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);

            fdef.shape = shape;
            body.createFixture(fdef);
        }


        // creates lettuce dispenser
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);

            fdef.shape = shape;
            body.createFixture(fdef);
        }



        // creates buns dispenser
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);

            fdef.shape = shape;
            body.createFixture(fdef);
        }


        // creates patty dispenser
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);

            fdef.shape = shape;
            body.createFixture(fdef);
        }


        // creates onion dispenser
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);

            fdef.shape = shape;
            body.createFixture(fdef);
        }


    }
    @Override
    public void show(){

    }

    @Override
    public void render (float delta) {
        update(delta);
        ScreenUtils.clear(0, 0, 0, 1);
        gamecam.update();
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();

        game.batch.draw(atlas.findRegion("E_Onion",0),0,600);
        game.batch.draw(atlas.findRegion("E_Onion",1),0,700);
        game.batch.draw(atlas.findRegion("E_Onion",2),0,800);
        game.batch.draw(atlas.findRegion("E_Onion",3),0,900);

        game.font.draw(game.batch, "The Main game screen", 100, 400);
        chef1.Draw(game.batch);
        chef2.Draw(game.batch);
        chef3.Draw(game.batch);
        game.batch.draw(chefSelection[chefPointer].sprite,0,400);
        // renderer the box2d lines
        b2dr.render(world, gamecam.combined);

        // game map
        renderer.render();
        game.batch.end();
    }
    @Override
    public void resize(int width, int height){
        game_port.update(width, height);
        gamecam.setToOrtho(false, width, height);

    }
    // handles inputs
    public void update(float dt){
        handleInput(dt);
        gamecam.position.x = chefSelection[chefPointer].position.x;
        gamecam.position.y = chefSelection[chefPointer].position.y;
        gamecam.update();
        renderer.setView(gamecam);

    }

    public void handleInput(float dt){
        if(Gdx.input.isTouched()) {
            gamecam.position.y += 100 * dt;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            chefPointer += 1;
            if (chefPointer>chefSelection.length-1){chefPointer=0;}
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)){
            chef1.inventory.stack[2].nextStage();

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)){
            if (chefSelection[chefPointer].inventory.isCraftable()){
                chefSelection[chefPointer].inventory.craft();
            }

        }

    }
    @Override
    public void pause(){

    }
    @Override
    public void resume(){

    }
    @Override
    public void hide(){

    }

    @Override
    public void dispose(){
        atlas.dispose();
    }

}
