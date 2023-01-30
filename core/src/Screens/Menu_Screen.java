package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
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
    Texture menuBG;
    Texture scenarioCounter;
    Texture scenarioCounterUp;
    Texture scenarioCounterDown;
    Texture creditsButton;
    Texture creditsButtonSelected;
    Texture logo;
    Texture piazzaPanic;
    Boolean help;
    Integer scenarioCount;
    BitmapFont font;

    int xMouse;
    int yMouse;

    public Menu_Screen(MyGame game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        game_port = new FitViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, gamecam);
        menu = new Texture("Menu_Screen.png");
        endlessButton = new Texture("EndlessBut.png");
        endlessButtonSelected = new Texture("EndlessButS.png");
        helpButton = new Texture("HelpBut.png");
        helpButtonSelected = new Texture("HelpButS.png");
        scenarioButton = new Texture("ScenarioBut.png");
        scenarioButtonSelected = new Texture("ScenarioButS.png");
        menuBG = new Texture("MenuPlain.png");
        scenarioCounter = new Texture("scenarioCount.png");
        scenarioCounterUp = new Texture("scenarioCountUp.png");
        scenarioCounterDown = new Texture("scenarioCountDown.png");
        creditsButton = new Texture("CreditsBut.png");
        creditsButtonSelected = new Texture("CreditsButS.png");
        logo = new Texture("Runtime_Terrors_Logo.png");
        piazzaPanic = new Texture("PiazzaPanic.png");
        scenarioCount = 5;
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(3, 3);
        help = false;

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        gamecam.update();
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        xMouse = Gdx.input.getX();
        yMouse = Gdx.input.getY();
        game.batch.draw(menuBG, 0, 0);

        if (help == true) {
            game.batch.draw(menu, 0, 0);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                help = false;
            }
        } else {
            //scenario mode button
            if (xMouse > 800 & xMouse < 1190 & yMouse > 485 & yMouse < 595) {
                game.batch.draw(scenarioButtonSelected, 800, 400, 390, 115);
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    game.setScreen(new Play_Screen(game, scenarioCount));
                    dispose();
                }
            } else {
                game.batch.draw(scenarioButton, 800, 400, 390, 115);
            }

            //endless mode button
            if (xMouse > 800 & xMouse < 1190 & yMouse > 630 & yMouse < 740) {
                game.batch.draw(endlessButtonSelected, 800, 250, 390, 115);
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    game.setScreen(new Play_Screen(game, scenarioCount));
                    dispose();
                }
            } else {
                game.batch.draw(endlessButton, 800, 250, 390, 115);
            }

            //help button
            if (xMouse > 800 & xMouse < 1190 & yMouse > 775 & yMouse < 885) {
                game.batch.draw(helpButtonSelected, 800, 100, 390, 115);
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    help = true;
                }
            } else {
                game.batch.draw(helpButton, 800, 100, 390, 115);
            }

            //scenario counter button
            if (xMouse > 1200 & xMouse < 1320 & yMouse > 425 & yMouse < 490) {
                game.batch.draw(scenarioCounterUp, 1200, 340, 120, 240);
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) & scenarioCount < 25) {
                    scenarioCount += 1;
                }

            } else if (xMouse > 1200 & xMouse < 1320 & yMouse > 590 & yMouse < 650) {
                game.batch.draw(scenarioCounterDown, 1200, 340, 120, 240);
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) & scenarioCount > 5) {
                    scenarioCount -= 1;
                }
            } else {
                game.batch.draw(scenarioCounter, 1200, 340, 120, 240);
            }
            font.draw(game.batch, scenarioCount.toString(), 1230, 480, 50, 100, true);

            //credits button
            if (xMouse > 100 & xMouse < 400 & yMouse > 790 & yMouse < 883) {
                game.batch.draw(creditsButtonSelected, 100, 100, 300, 100);
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    game.setScreen(new Credit_Screen(game));
                    dispose();
                }
            } else {
                game.batch.draw(creditsButton, 100, 100, 300, 100);
            }

            //logo
            game.batch.draw(logo, 320, 640, 300, 300);
            game.batch.draw(piazzaPanic, 680, 700);
        }

        game.batch.end();

        xMouse = Gdx.input.getX();
        yMouse = Gdx.input.getY();
    }

    @Override
    public void resize(int width, int height) {
        game_port.update(width, height);
        gamecam.setToOrtho(false, width, height);

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
