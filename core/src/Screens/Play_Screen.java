package Screens;

import Scenes.Recipe_Hud;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.*;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Play_Screen implements Screen {
    private MyGame game;

    public static Player[] chefSelection;
    public static int chefPointer;

    public static HashSet<InteractivetileObject> activeStations;
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

    // orders queue
    public Queue<Object> Orders = new LinkedList<>();

    // display orders
    private Recipe_Hud hud;




    public Play_Screen(MyGame game){
        this.game = game;

        recipes = new HashMap<>();
        recipes.put("E_Salad",
                new Triplet<>(new Pair<>("E_Lettuce", 2), new Pair<>("E_Tomato", 2), new Pair<>("E_Onion", 2)));

        recipes.put("E_Burger",
                new Triplet<>(new Pair<>("E_Lettuce", 2), new Pair<>("E_Patty", 2), new Pair<>("E_Bun", 1)));
        // sets up adding the orders to be made in the scenario
        Orders.add(recipes.get("E_Salad"));
        Orders.add(recipes.get("E_Burger"));
        Orders.add(recipes.get("E_Salad"));

        // displaying recipes Hud
        hud = new Recipe_Hud(game.batch);
        


        atlas = new TextureAtlas(Gdx.files.internal("ENG1_Assets_V2.atlas"));

        activeStations = new HashSet<>();

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

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        Body body;
        FixtureDef fdef = new FixtureDef();
        // Creates counters objects

        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            new Counters(world,map,object);
        }

        // creates service counter
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            new Service_Counter(world,map,object);

        }

        // creates bin
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            new Bin(world,map,object);
        }


        // creates cutting stations
        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            new Cutting_Counter(world,map,object);
        }


        // creates stoves
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)){
            new Cooking_station(world,map,object);
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
        //game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        game.batch.begin();

        // renderer the box2d lines
        b2dr.render(world, gamecam.combined);

        // game map
        renderer.render();

        for (Player chefs: chefSelection) {
            chefs.Draw(game.batch);
        }
        game.batch.end();

        for (InteractivetileObject obj : activeStations) {
                obj.drawProgress(game.batch, gamecam);
        }


    }
    @Override
    public void resize(int width, int height){
        game_port.update(width, height);
        gamecam.setToOrtho(false, width, height);

    }
    // handles inputs
    public void update(float dt){
        handleInput(dt);
        for (InteractivetileObject obj :
                activeStations) {
            if (obj.isProgressable()){
                obj.progress(dt);
            }
        }

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
            System.out.print(Gdx.input.getX());
            System.out.print(" ");
            System.out.println(Gdx.input.getY());
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            Array<Body> bodies = new Array<Body>();
            world.getBodies(bodies);
            for (Body b:bodies){

                if (sqrt(pow(b.getPosition().x-chefSelection[chefPointer].position.x,2) +
                        pow(b.getPosition().y-chefSelection[chefPointer].position.y,2)) < 100){
                    if (b.getFixtureList().get(0).getUserData() == null){continue;}
                    if (b.getFixtureList().get(0).getUserData().getClass().equals(Player.class)){continue;}

                    InteractivetileObject temp = (InteractivetileObject) b.getFixtureList().get(0).getUserData();

                    Vector3 mouse = gamecam.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));

                    if (temp.getRect().getX() < mouse.x & mouse.x < temp.getRect().getX() + temp.getRect().getWidth()){
                    if (temp.getRect().getY() < mouse.y & mouse.y < temp.getRect().getY() + temp.getRect().getHeight()){

                    if (temp.getClass().equals(Bin.class)) {
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

                    else if (temp.getClass().equals(Cutting_Counter.class)) {
                        if (temp.getCurrentItem()==null){
                            foodItems tempItem = chefSelection[chefPointer].inventory.checkHead();
                            if (temp.acceptableItem(tempItem.getItemName())) {
                                ((Cutting_Counter) temp).takeItem(chefSelection[chefPointer].inventory.returnHead());
                            }
                        }
                        else{((Cutting_Counter) temp).removeItem(chefSelection[chefPointer]);}
                    }

                    else if (temp.getClass().equals(Cooking_station.class)) {
                        if (temp.getCurrentItem()==null){
                            foodItems tempItem = chefSelection[chefPointer].inventory.checkHead();
                            if (temp.acceptableItem(tempItem.getItemName())) {
                                ((Cooking_station) temp).takeItem(chefSelection[chefPointer].inventory.returnHead());
                            }
                        }
                        else{((Cooking_station) temp).removeItem(chefSelection[chefPointer]);}
                    }

                    else if (temp.getClass().equals(Counters.class)) {
                        if (temp.getCurrentItem()==null){
                            ((Counters) temp).takeItem(chefSelection[chefPointer].inventory.returnHead());
                        }
                        else{((Counters) temp).removeItem(chefSelection[chefPointer]);}
                    }

                    }}
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
