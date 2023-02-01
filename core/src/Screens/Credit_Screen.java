package Screens;

import Scenes.Hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;

/**
 * GAME CREDITS SCREEN
 */

public class Credit_Screen implements Screen {

    private MyGame game;

    private Viewport game_port;
    private OrthographicCamera gamecam;
    //Generate the text for the credit screen
    private Hud hud;

    /**
     * constuctor for game screen
     * @param game
     */
    public Credit_Screen(MyGame game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        game_port = new FitViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, gamecam);
        hud = new Hud(game.batch);
    }

    @Override
    public void show() {

    }

    /**
     * renders screen contents
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            game.setScreen(new Menu_Screen(game));
            dispose();
        }
    }

    /**
     * resizes window
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {

        game_port.update(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
