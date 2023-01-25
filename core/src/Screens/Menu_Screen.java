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

    Texture menu;
    Texture endlessButton;
    Texture endlessButtonSelected;
    Texture scenarioButton;
    Texture scenarioButtonSelected;
    Texture helpButton;
    Texture helpButtonSelected;

    int xMouse;
    int yMouse;

    public Menu_Screen(MyGame game){
        this.game = game;
        gamecam = new OrthographicCamera();
        game_port = new FitViewport(MyGame.V_WIDTH,MyGame.V_HEIGHT ,gamecam);
        menu = new Texture("Menu_Screen.png");
        endlessButton = new Texture ("EndlessBut.png");
        endlessButtonSelected = new Texture ("EndlessButS.png");
        helpButton = new Texture ("HelpBut.png");
        helpButtonSelected = new Texture ("HelpButS.png");
        scenarioButton = new Texture ("ScenarioBut.png");
        scenarioButtonSelected = new Texture ("ScenarioButS.png");
    }
    @Override
    public void show(){

    }

    @Override
    public void render (float delta) {
        ScreenUtils.clear(0, 0, 1, 1);
        gamecam.update();
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        xMouse = Gdx.input.getX();
        yMouse = Gdx.input.getY();

        if (xMouse > 800 & xMouse < 1190 & yMouse > 485 & yMouse < 595){
            game.batch.draw(scenarioButtonSelected,800,400,390,115);
            if (Gdx.input.isTouched() ) {
                game.setScreen(new Play_Screen(game));
                dispose();
            }
        }
        else{
            game.batch.draw(scenarioButton,800,400,390,115);
        }
        if(xMouse > 800 & xMouse < 1190 & yMouse > 630 & yMouse < 740){
            game.batch.draw(endlessButtonSelected,800,250,390,115);
            if (Gdx.input.isTouched() ) {
                game.setScreen(new Play_Screen(game));
                dispose();
            }
        }
        else{
            game.batch.draw(endlessButton,800,250,390,115);
        }
        if(xMouse > 800 & xMouse < 1190 & yMouse > 775 & yMouse < 885){
            game.batch.draw(helpButtonSelected,800,100,390,115);
        }
        else{
            game.batch.draw(helpButton,800,100,390,115);
        }
        game.batch.end();

        xMouse = Gdx.input.getX();
        yMouse = Gdx.input.getY();

        System.out.println(xMouse);
        System.out.println(yMouse);

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
