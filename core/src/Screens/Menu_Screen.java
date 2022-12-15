package Screens;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;

public class Menu_Screen implements Screen {
    private MyGame game;

    private Viewport game_port;
    private OrthographicCamera gamecam;

    Texture texture; // temp

    public Menu_Screen(MyGame game){
        this.game = game;
        gamecam = new OrthographicCamera();
        game_port = new FitViewport(MyGame.V_WIDTH,MyGame.V_HEIGHT ,gamecam);
    }
    @Override
    public void show(){

    }

    @Override
    public void render (float delta) {
        ScreenUtils.clear(1, 0, 0, 1);
        game.batch.begin();
        game.batch.draw(texture,0,0);
        game.batch.end();
        game.batch.setProjectionMatrix(gamecam.combined);
    }
    @Override
    public void resize(int width, int height){
        game_port.update(width, height);

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
