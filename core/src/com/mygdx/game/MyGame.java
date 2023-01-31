package com.mygdx.game;

import Screens.Menu_Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * creates game and passes the rendering to current screen selection
 */
public class MyGame extends Game {

    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 200;
    public SpriteBatch batch; // holds all images, public so all screens can access it
    public BitmapFont font;
    Texture img;


    // load the drop sound effect and the rain background "music"
    //dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
    //rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

    // start the playback of the background music immediately
    //  rainMusic.setLooping(true);
    //  rainMusic.play();

    /**
     * instantiates new batch of sprites, passes to menu
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        // sets the first screen to be loaded
        this.setScreen(new Menu_Screen(this));

    }

    /**
     * delegates render to active screen
     */
    @Override
    public void render() {
        super.render();

    }

    /**
     * disposes of fonts and sprite batches
     */
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
