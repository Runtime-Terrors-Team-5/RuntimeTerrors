package Screens;

import Tools.World_contact_listener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.*;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.HashMap;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Play_Screen implements Screen {
    private MyGame game;

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




    public Play_Screen(MyGame game){
        this.game = game;

        recipes = new HashMap<>();
        recipes.put("E_Salad",
                new Triplet<>(new Pair<>("E_Lettuce", 2), new Pair<>("E_Tomato", 2), new Pair<>("E_Onion", 2)));

        recipes.put("E_Burger",
                new Triplet<>(new Pair<>("E_Lettuce", 2), new Pair<>("E_Patty", 2), new Pair<>("E_Bun", 1)));


        atlas = new TextureAtlas(Gdx.files.internal("ENG1_Assets_V2.atlas"));



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

        chefSelection = new Player[]{
                new Player(atlas.findRegion("C_Blue_N"),atlas.findRegion("M_Blue_N"),
                        atlas.findRegion("M_Blue_C123"), world),

                new Player(atlas.findRegion("C_Green_N"),atlas.findRegion("M_Green_N"),
                        atlas.findRegion("M_Green_C123"), world)
        };

        chefPointer = 0;
        // test only
        foodItems item = new foodItems("E_Onion");
        chefSelection[chefPointer].inventory.addItem(item);

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
            if (Intersector.overlaps(rect, chefSelection[chefPointer].getRectangle())) {
                System.out.println("Collision Occured");
                // collision happened
            }
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
            new Bin(world,map,object);
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
            new Tomato_dispenser(world,map,object);
        }


        // creates lettuce dispenser
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            new Lettuce_dispenser(world,map,object);
        }



        // creates buns dispenser
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            new Bun_dispenser(world,map,object);
        }


        // creates patty dispenser
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            new Patty_dispenser(world,map,object);
        }


        // creates onion dispenser
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            new Onion_dispenser(world,map,object);
        }



    }

    public World getWorld() {
        return world;
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

        game.font.draw(game.batch, "The Main game screen", 100, 400);

        for (Player chefs:
             chefSelection) {
            chefs.Draw(game.batch);
        }
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
        // test only to be removed
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)){
            Array<Body> bodies = new Array<Body>();
            world.getBodies(bodies);
            for (Body b:bodies){
                //System.out.println(b.getWorldCenter());
                //System.out.println(b.getUserData());
                if (sqrt(pow(b.getPosition().x-chefSelection[chefPointer].position.x,2) +
                        pow(b.getPosition().y-chefSelection[chefPointer].position.y,2)) < 100){
                    //System.out.println(b.getFixtureList().get(0).getUserData().getClass());
                    if (b.getFixtureList().get(0).getUserData() == null){continue;}
                    if (b.getFixtureList().get(0).getUserData().getClass().equals(Player.class)){continue;}

                    InteractivetileObject temp = (InteractivetileObject) b.getFixtureList().get(0).getUserData();

                    if (b.getFixtureList().get(0).getUserData().getClass().equals(Bin.class)) {
                        temp.DisposeTrash(chefSelection[chefPointer]);
                    }
                    else if (temp.getClass().equals(Onion_dispenser.class)) {
                        System.out.println("onion");
                        temp.DispenseItem(chefSelection[chefPointer]);
                    }
                    else if (temp.getClass().equals(Patty_dispenser.class)) {
                        System.out.println("patty");
                        temp.DispenseItem(chefSelection[chefPointer]);
                    }
                    else if (temp.getClass().equals(Bun_dispenser.class)) {
                        System.out.println("bun");
                        temp.DispenseItem(chefSelection[chefPointer]);
                    }
                    else if (temp.getClass().equals(Tomato_dispenser.class)) {
                        temp.DispenseItem(chefSelection[chefPointer]);
                    }
                    else if (temp.getClass().equals(Lettuce_dispenser.class)) {
                        temp.DispenseItem(chefSelection[chefPointer]);
                    }
                }
            }
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
