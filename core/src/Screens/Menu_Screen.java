package Screens;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;

public class Menu_Screen implements Screen {
    private MyGame game;

    private Viewport game_port;
    private OrthographicCamera gamecam;

    Texture menu; // temp

    public Menu_Screen(MyGame game){
        this.game = game;
        gamecam = new OrthographicCamera();
        game_port = new FitViewport(MyGame.V_WIDTH,MyGame.V_HEIGHT ,gamecam);
        menu = new Texture("Menu_Screen.png");
    }
    @Override
    public void show(){

    }

    @Override
    public void render (float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        gamecam.update();
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        game.batch.draw(menu,0,0);
        game.font.draw(game.batch, "Welcome to Drop!!! ", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);

        game.batch.end();


        if (Gdx.input.isTouched() ) {
            game.setScreen(new Play_Screen(game));
            dispose();
        }
    }
    @Override
    public void resize(int width, int height){
        game_port.update(width, height);
        gamecam.setToOrtho(false, width, height);

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
