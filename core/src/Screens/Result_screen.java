package Screens;

import Scenes.Hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;

// GAME CREDITS SCREEN
public class Result_screen implements Screen {

    private MyGame game;

    private Viewport game_port;
    private OrthographicCamera gamecam;
    private Hud hud;
    private Stage stage;


    public Result_screen(MyGame game, long time) {
        this.game = game;
        gamecam = new OrthographicCamera();
        game_port = new FitViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, gamecam);
        Label text = new Label("Time Taken: " + (time / 1000) + " seconds",
            new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label text2 = new Label("Press anywhere to continue",
            new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        FitViewport viewport = new FitViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT,
            new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        Table table = new Table();
        table.setFillParent(true);
        table.add(text).expandX();
        table.row();
        table.add(text2).expandX();
        table.row();
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        game.batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            game.setScreen(new Menu_Screen(game));
            dispose();
        }
    }

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
