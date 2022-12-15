package com.mygdx.game;

import Screens.Menu_Screen;
import Screens.Play_Screen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;

public class MyGame extends Game {
	public SpriteBatch batch; // holds all images, public so all screens can access it
	Texture img;

	Player player;

	
	@Override
	// format for loading assets into game
	// dropImage = new Texture(Gdx.files.internal("droplet.png"));

	// load the drop sound effect and the rain background "music"
	//dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
	//rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

	// start the playback of the background music immediately
    //  rainMusic.setLooping(true);
    //  rainMusic.play();
	public void create () {
		batch = new SpriteBatch();
		//img = new Texture("Owlet_Monster.png");
		//player = new Player(img); // instantiation of player class
		setScreen(new Play_Screen(this));

	}

	@Override
	public void render () {
		//ScreenUtils.clear(1, 0, 0, 1);
		//batch.begin();
		//player.Draw(batch);
		//batch.end();
		// delegates render to active screen
		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}
}
