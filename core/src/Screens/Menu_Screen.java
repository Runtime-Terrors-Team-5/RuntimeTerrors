package Screens;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Screen;
import com.mygdx.game.MyGame;

public class Menu_Screen implements Screen {
    private MyGame game;

    Texture texture; // temp

    public Menu_Screen(MyGame game){
        this.game = game;
        texture = new Texture("Owlet_Monster.png");
    }
    @Override
    public void show(){

    }

    @Override
    public void render (float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        game.batch.begin();
        game.batch.draw(texture,0,0);
        game.batch.end();
    }
    @Override
    public void resize(int width, int height){

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

    }
}
