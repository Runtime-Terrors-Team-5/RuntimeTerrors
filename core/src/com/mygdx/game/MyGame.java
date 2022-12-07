package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;

public class MyGame extends ApplicationAdapter {
	SpriteBatch batch; // holds all images
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
		img = new Texture("Owlet_Monster.png");
		player = new Player(img); // instantiation of player class

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		player.Draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
