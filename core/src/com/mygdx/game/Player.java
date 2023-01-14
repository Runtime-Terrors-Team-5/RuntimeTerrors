package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Input.Keys;
import Screens.Play_Screen;

public class Player {

    // attributes
    public Vector2 position;
    public Sprite sprite;

    public float speed = 300; // player movement speed

    // methods
    public Player(Texture img){
        sprite = new Sprite(img);
        sprite.setScale(2);
        position = new Vector2(Gdx.graphics.getWidth()/2,sprite.getScaleY()*sprite.getHeight()/2);
    }
    public Player(TextureRegion img){
        sprite = new Sprite(img);
        sprite.setScale(2);
        position = new Vector2(Gdx.graphics.getWidth()/2,sprite.getScaleY()*sprite.getHeight()/2);
    }

    // maps keys to actions
    public void Update(float deltaTime){
        // WASD movement input processing
        // only allow this if the chef pointer is pointing to this chef
        if(Play_Screen.chefSelection[Play_Screen.chefPointer]==this){
        if(Gdx.input.isKeyPressed(Keys.A)) position.x-=deltaTime*speed;
        if(Gdx.input.isKeyPressed(Keys.D)) position.x+=deltaTime*speed;
        if(Gdx.input.isKeyPressed(Keys.S)) position.y-=deltaTime*speed;
        if(Gdx.input.isKeyPressed(Keys.W)) position.y+=deltaTime*speed;
        // stops player from moving out of the window boundaries
        if(position.x-(sprite.getWidth()*sprite.getScaleX()/2)<=0) position.x = (sprite.getWidth()*sprite.getScaleX()/2);
        if(position.x+(sprite.getWidth()*sprite.getScaleX()/2)>=Gdx.graphics.getWidth()) position.x = Gdx.graphics.getWidth()-(sprite.getWidth()*sprite.getScaleX()/2);
        if(position.y-(sprite.getHeight()*sprite.getScaleY()/2)<=0) position.y = (sprite.getHeight()*sprite.getScaleY()/2);
        if(position.y+(sprite.getHeight()*sprite.getScaleY()/2)>=Gdx.graphics.getHeight()) position.y = Gdx.graphics.getHeight()-(sprite.getHeight()*sprite.getScaleY()/2);
    }}

    public void Draw(SpriteBatch batch){
        Update(Gdx.graphics.getDeltaTime());
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }
}
