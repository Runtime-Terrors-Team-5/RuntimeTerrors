package com.mygdx.game;

import Screens.Credit_Screen;
import Screens.Menu_Screen;
import Screens.Play_Screen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;

public class MyGame extends Game {
	public SpriteBatch batch; // holds all images, public so all screens can access it
	public BitmapFont font;
	Texture img;
	public static final int V_WIDTH = 400;

	public static final int V_HEIGHT = 200;
	// load the drop sound effect and the rain background "music"
	//dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
	//rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

	// start the playback of the background music immediately
    //  rainMusic.setLooping(true);
    //  rainMusic.play();
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		// sets the first screen to be loaded
		this.setScreen(new Menu_Screen(this));

	}

	@Override
	public void render () {
		// delegates render to active screen
		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
