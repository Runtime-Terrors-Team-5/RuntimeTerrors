package Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;
import Sprites.Player;


public class  Play_Screen implements Screen {
    private MyGame game;
    Player chef1;
    Player chef2;
    Player chef3;
    public static Player[] chefSelection;
    public static int chefPointer;
    // sets screen size
    private Viewport game_port;
    private OrthographicCamera gamecam;
    TextureAtlas atlas;

    // imports the kitchen map
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    /// box2d variables
    //private World world;
    //private Box2DDebugRenderer b2dr;


    Texture texture; // temp

    public Play_Screen(MyGame game){
        this.game = game;

        //Texture img = new Texture("Owlet_Monster.png");
        atlas = new TextureAtlas(Gdx.files.internal("ENG1_Assets_V1.atlas"));
        chef1 = new Player(atlas.findRegion("C_Blue_N"));
        chef2 = new Player(atlas.findRegion("C_Green_N"));
        chef3 = new Player(atlas.findRegion("C_Red_N"));
        chefSelection = new Player[]{chef1, chef2, chef3};
        chefPointer = 0;

        gamecam = new OrthographicCamera();
        game_port = new FitViewport(MyGame.V_WIDTH,MyGame.V_HEIGHT ,gamecam);
        maploader = new TmxMapLoader();
        map = maploader.load("kitchen_map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        //world = new World(new Vector2(0,0), true);
        gamecam.position.set(game_port.getWorldWidth() / 2, ((game_port.getWorldHeight() / 2)-300), 0);
    }
    @Override
    public void show(){

    }

    @Override
    public void render (float delta) {
        update(delta);
        ScreenUtils.clear(0, 1, 0, 1);
        gamecam.update();
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        game.font.draw(game.batch, "The Main game screen", 100, 100);
        chef1.Draw(game.batch);
        chef2.Draw(game.batch);
        chef3.Draw(game.batch);
        game.batch.draw(chefSelection[chefPointer].sprite,0,0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            chefPointer += 1;
            if (chefPointer>chefSelection.length-1){chefPointer=0;}
        }

        renderer.setView(gamecam);
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

        gamecam.update();
        renderer.setView(gamecam);

    }

    public void handleInput(float dt){
        if(Gdx.input.isTouched()) {
            gamecam.position.x += 100 * dt;
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
