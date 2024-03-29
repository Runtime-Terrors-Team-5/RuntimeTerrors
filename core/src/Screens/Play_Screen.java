package Screens;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import Scenes.Recipe_Hud;
import Tools.World_contact_listener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Bin;
import com.mygdx.game.Bun_dispenser;
import com.mygdx.game.Cooking_station;
import com.mygdx.game.Counters;
import com.mygdx.game.Cutting_Counter;
import com.mygdx.game.InteractivetileObject;
import com.mygdx.game.Lettuce_dispenser;
import com.mygdx.game.MyGame;
import com.mygdx.game.Onion_dispenser;
import com.mygdx.game.Patty_dispenser;
import com.mygdx.game.Player;
import com.mygdx.game.Service_Counter;
import com.mygdx.game.Tomato_dispenser;
import com.mygdx.game.customer;
import com.mygdx.game.foodItems;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import com.badlogic.gdx.graphics.Texture;

/**
 * Creates the game scenario screen and instatiates all the objects in the game
 */
public class Play_Screen implements Screen {

    public static Player[] chefSelection; // contains the chef objects
    public static int chefPointer;  // points to current chef index
    public static HashSet<InteractivetileObject> activeStations;  // active cooking stations
    public static HashMap recipes; // recipe items, can be used for random order generation
    private final MyGame game;
    // sets screen size
    private final Viewport game_port;
    private final OrthographicCamera gamecam;
    // imports the kitchen map
    private final TmxMapLoader maploader;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    // world  generation
    private final World world;
    private final Box2DDebugRenderer b2dr;
    // display orders
    private final Recipe_Hud hud;
    // orders queue to be popped
    //public Queue<String> Orders = new LinkedList<>();
    public Queue<customer> Orders = new LinkedList<>();
    // completed game scenario boolean
    public boolean Completed_scenario;
    public long timeStart;
    TextureAtlas atlas;
    Texture helpIcon;
    Texture controlHelp;
    Texture ingredientsHelp;
    boolean helpScreen;
    // game audio for counters
    Sound soundChop = Gdx.audio.newSound(Gdx.files.internal("Sounds/Chopping.mp3"));
    long chopping = soundChop.play(0.5f);

    Sound soundFry =  Gdx.audio.newSound(Gdx.files.internal("Sounds/Frying.mp3"));
    long frying = soundFry.play(0.5f);

    /**
     * Instantiates the Play_screen class
     * @param game
     * @param scenarioCount
     */

    public Play_Screen(MyGame game, int scenarioCount) {
        this.game = game;
        soundChop.stop(chopping);
        soundFry.stop(frying);
        atlas = new TextureAtlas(Gdx.files.internal("ENG1_Assets_V2.atlas"));
        helpIcon = new Texture("helpIcon.png");
        controlHelp = new Texture("ControlHelp.png");
        ingredientsHelp = new Texture("IngredientsHelp.png");
        helpScreen = false;
        timeStart = System.currentTimeMillis();
        // setting up the recipe contents in a hashmap
        recipes = new HashMap<>();
        recipes.put("E_Salad",
            new Triplet<>(new Pair<>("E_Lettuce", 2), new Pair<>("E_Tomato", 2),
                new Pair<>("E_Onion", 2)));

        recipes.put("E_Burger",
            new Triplet<>(new Pair<>("E_Lettuce", 2), new Pair<>("E_Patty", 2),
                new Pair<>("E_Bun", 1)));
        // sets up adding the orders to be made in the scenario
        Set recipeItems = recipes.keySet();
        for (int i = 0; i < scenarioCount; i++) {
            String temp = (String) recipeItems.toArray()[ThreadLocalRandom.current()
                .nextInt(0, recipeItems.size())];
            Orders.add(new customer(new foodItems(temp), atlas));
        }

        //Orders.add((String) recipeItems.toArray()[ThreadLocalRandom.current().nextInt(0,recipeItems.size())]);
        //Orders.add((String) recipeItems.toArray()[ThreadLocalRandom.current().nextInt(0,recipeItems.size())]);

        // displaying recipes Hud
        hud = new Recipe_Hud(game.batch, Orders);

        activeStations = new HashSet<>();
        // game camera
        gamecam = new OrthographicCamera();
        game_port = new FitViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, gamecam);
        // loads kitchen map
        maploader = new TmxMapLoader();
        map = maploader.load("kitchen_map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        // map camera
        gamecam.position.set(game_port.getWorldWidth(), game_port.getWorldHeight(), 0);
        // sets no gravity and creates the world
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();
        // identifies what the sprite has come into contact with
        world.setContactListener(new World_contact_listener());
       // chef creation in game
        chefSelection = new Player[]{
            new Player(atlas.findRegion("C_Blue_N"), atlas.findRegion("M_Blue_N"),
                atlas.findRegion("M_Blue_C123"), world),

            new Player(atlas.findRegion("C_Green_N"), atlas.findRegion("M_Green_N"),
                atlas.findRegion("M_Green_C123"), world)
        };

        chefPointer = 0;
        // created object body which each MapObject extends from
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        Body body;
        FixtureDef fdef = new FixtureDef();
        // Creates all types of counter objects (all in game kitchen objects)

        for (MapObject object : map.getLayers().get(9).getObjects()
            .getByType(RectangleMapObject.class)) {
            new Counters(world, map, object);
        }

        // creates service counter
        for (MapObject object : map.getLayers().get(2).getObjects()
            .getByType(RectangleMapObject.class)) {
            new Service_Counter(world, map, object);

        }

        // creates bin
        for (MapObject object : map.getLayers().get(8).getObjects()
            .getByType(RectangleMapObject.class)) {
            new Bin(world, map, object);
        }

        // creates cutting stations
        for (MapObject object : map.getLayers().get(1).getObjects()
            .getByType(RectangleMapObject.class)) {
            new Cutting_Counter(world, map, object);
        }

        // creates stoves
        for (MapObject object : map.getLayers().get(10).getObjects()
            .getByType(RectangleMapObject.class)) {
            new Cooking_station(world, map, object);
        }

        // creates tomato dispenser
        for (MapObject object : map.getLayers().get(4).getObjects()
            .getByType(RectangleMapObject.class)) {
            new Tomato_dispenser(world, map, object);
        }

        // creates lettuce dispenser
        for (MapObject object : map.getLayers().get(6).getObjects()
            .getByType(RectangleMapObject.class)) {
            new Lettuce_dispenser(world, map, object);
        }

        // creates buns dispenser
        for (MapObject object : map.getLayers().get(5).getObjects()
            .getByType(RectangleMapObject.class)) {
            new Bun_dispenser(world, map, object);
        }

        // creates patty dispenser
        for (MapObject object : map.getLayers().get(3).getObjects()
            .getByType(RectangleMapObject.class)) {
            new Patty_dispenser(world, map, object);
        }

        // creates onion dispenser
        for (MapObject object : map.getLayers().get(7).getObjects()
            .getByType(RectangleMapObject.class)) {
            new Onion_dispenser(world, map, object);
        }

    }

    /**
     *  returns the game world
     * @return the game world
     */
    public World getWorld() {
        return world;
    }


    @Override
    public void show() {
    }

    /**
     * Renders the game screen
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        update(delta);  // updates game screen
        ScreenUtils.clear(1, 1, 1, 1); // background colour
        gamecam.update();
        game.batch.setProjectionMatrix(gamecam.combined);
        //game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        game.batch.begin();

        // renderer the box2d lines
        b2dr.render(world, gamecam.combined);

        // game map
        renderer.render();

        //draw customer with order
        for (int i = 0; i < Orders.size(); i++) {
            customer temp = (customer) Orders.toArray()[i];
            game.batch.draw(temp.getOrderSprite(), 1000 + (i * 100), 770);
            game.batch.draw(temp.getSprite(), 1000 + (i * 100), 720);
        }

        // draws chefs
        for (Player chefs : chefSelection) {
            chefs.Draw(game.batch);
        }
        // game  scenario over condition, changes screen to score board or menu
        if (Completed_scenario) {
            long timeend = System.currentTimeMillis();
            long timeElapsed = timeend - timeStart;
            System.out.println(timeElapsed);
            game.setScreen(new Result_screen(game, timeElapsed));
            dispose();
        }
        // if the help button is selected it shows the help methods
        if(helpScreen == true){
            game.batch.draw(controlHelp,gamecam.position.x+480,gamecam.position.y-440,
                    400,202);
            game.batch.draw(ingredientsHelp,gamecam.position.x-900,gamecam.position.y+250,
                    900,215);
        }

        game.batch.draw(helpIcon, gamecam.position.x-800,gamecam.position.y-400, 80, 80);

        game.batch.end();
        // draws stations
        for (InteractivetileObject obj : activeStations) {
            obj.drawProgress(game.batch, gamecam);
        }

        hud.stage.draw();


    }



    /**
     * Adjusts game screen to new window size
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        game_port.update(width, height);
        gamecam.setToOrtho(false, width, height);

    }

    /**
     * Updates the game screen so it registers changes
     * @param dt
     */

    public void update(float dt) {
        handleInput(dt);
        for (InteractivetileObject obj :
            activeStations) {
            if (obj.isProgressing()) {
                obj.progress(dt);
            }
        }
        // game cam follows player movement
        gamecam.position.x = chefSelection[chefPointer].position.x;
        gamecam.position.y = chefSelection[chefPointer].position.y;
        gamecam.update();
        renderer.setView(gamecam);
    }

    /**
     * Handles game user input
     * @param dt
     */
    public void handleInput(float dt) {
        if (Gdx.input.isTouched()) {
            gamecam.position.y += 100 * dt;
        }
        // changes chef selection
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            chefPointer += 1;
            if (chefPointer > chefSelection.length - 1) {
                chefPointer = 0;
            }
        }  // unused function
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            System.out.print(Gdx.input.getX());
            System.out.print(" ");
            System.out.println(Gdx.input.getY());
        }
        // identifies player & object collisions and their specific actions. Then handles the input
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Array<Body> bodies = new Array<Body>();
            world.getBodies(bodies);
            for (Body b : bodies) {
                // loops through the counters
                if (sqrt(pow(b.getPosition().x - chefSelection[chefPointer].position.x, 2) +
                    pow(b.getPosition().y - chefSelection[chefPointer].position.y, 2)) < 200) {
                    if (b.getFixtureList().get(0).getUserData() == null) {
                        continue;
                    }
                    if (b.getFixtureList().get(0).getUserData().getClass().equals(Player.class)) {
                        continue;
                    }

                    InteractivetileObject temp = (InteractivetileObject) b.getFixtureList().get(0)
                        .getUserData();

                    Vector3 mouse = gamecam.unproject(
                        new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

                    System.out.println("mouse x: "+(mouse.x - gamecam.position.x)+" mouse y: "+(mouse.y - gamecam.position.y));

                    if(mouse.x - gamecam.position.x > -800 && mouse.x - gamecam.position.x < -724
                            && mouse.y - gamecam.position.y < -310 && mouse.y - gamecam.position.y > -385){
                        helpScreen = !helpScreen;
                    }

                    if (temp.getRect().getX() < mouse.x
                        & mouse.x < temp.getRect().getX() + temp.getRect()
                        .getWidth()) {
                        if (temp.getRect().getY() < mouse.y
                            & mouse.y < temp.getRect().getY() + temp.getRect()
                            .getHeight()) {
                            // identifies collided object and calls its associated method
                            if (temp.getClass().equals(Bin.class)) {
                                temp.DisposeTrash(chefSelection[chefPointer]);
                            } else if (temp.getClass().equals(Onion_dispenser.class)) {
                                temp.DispenseItem(chefSelection[chefPointer]);
                            } else if (temp.getClass().equals(Patty_dispenser.class)) {
                                temp.DispenseItem(chefSelection[chefPointer]);
                            } else if (temp.getClass().equals(Bun_dispenser.class)) {
                                temp.DispenseItem(chefSelection[chefPointer]);
                            } else if (temp.getClass().equals(Tomato_dispenser.class)) {
                                temp.DispenseItem(chefSelection[chefPointer]);
                            } else if (temp.getClass().equals(Lettuce_dispenser.class)) {
                                temp.DispenseItem(chefSelection[chefPointer]);
                            } else if (temp.getClass().equals(Cutting_Counter.class)) {
                                if (temp.getCurrentItem() == null) {
                                    foodItems tempItem = chefSelection[chefPointer].inventory.checkHead();
                                    if (tempItem == null) {
                                        continue;
                                    }  // checks if given item is acceptable for that counter for chopping
                                    if (temp.acceptableItem(tempItem.getItemName())) {
                                        ((Cutting_Counter) temp).takeItem(
                                            chefSelection[chefPointer].inventory.returnHead());
                                        chefSelection[chefPointer].setActionTrue();
                                        chopping = soundChop.play(0.5f);
                                        soundChop.setLooping(chopping, true);
                                    }
                                } else {  // stops chopping sound when finished
                                    ((Cutting_Counter) temp).removeItem(chefSelection[chefPointer]);
                                    chefSelection[chefPointer].setActionFalse();
                                    soundChop.stop(chopping);
                                }
                            } else if (temp.getClass().equals(Cooking_station.class)) {
                                if (temp.getCurrentItem() == null) {
                                    foodItems tempItem = chefSelection[chefPointer].inventory.checkHead();
                                    if (tempItem == null) {
                                        continue;
                                    } // checks if item is acceptable to be frying
                                    if (temp.acceptableItem(tempItem.getItemName())) {
                                        ((Cooking_station) temp).takeItem(
                                            chefSelection[chefPointer].inventory.returnHead());
                                        chefSelection[chefPointer].setActionTrue();
                                        frying = soundFry.play(0.5f);
                                        soundFry.setLooping(frying, true);
                                    }
                                } else {
                                    if (temp.getCurrentItem().isProgressable()) {
                                        temp.nextStage();
                                    } else { // stops frying sound when finished
                                        ((Cooking_station) temp).removeItem(
                                            chefSelection[chefPointer]);
                                        chefSelection[chefPointer].setActionFalse();
                                        soundFry.stop(frying);
                                    }
                                } // adds items to normal counters and removes then ( goes to chef stack)
                            } else if (temp.getClass().equals(Counters.class)) {
                                if (temp.getCurrentItem() == null) {
                                    ((Counters) temp).takeItem(
                                        chefSelection[chefPointer].inventory.returnHead());

                                } else {
                                    ((Counters) temp).removeItem(chefSelection[chefPointer]);
                                }
                            }

                            // Scenario ending condition , and calculating if orders are right
                            else if (temp.getClass().equals(Service_Counter.class)) {
                                if (chefSelection[chefPointer].inventory.checkHead() == null) {
                                    continue;
                                }
                                if (Orders.isEmpty()) {
                                    Completed_scenario = true;
                                    // checks if orders are correct, then updates it
                                } else if (((Service_Counter) temp).CheckOrders(
                                    chefSelection[chefPointer].inventory.checkHead(),
                                    this.Orders)) {
                                    this.Orders.remove();
                                    chefSelection[chefPointer].inventory.returnHead();
                                    hud.updateHUB(Orders);
                                    // checks to see if its now empty after removal, automatically ending the game if so
                                    if (Orders.isEmpty()) {
                                        Completed_scenario = true;
                                    }


                                }

                            }

                        }
                    }
                }
            }
        }
        // checks if items are craftable
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            if (chefSelection[chefPointer].inventory.isCraftable()) {
                chefSelection[chefPointer].inventory.craft();
            }
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        atlas.dispose();
    }

}
