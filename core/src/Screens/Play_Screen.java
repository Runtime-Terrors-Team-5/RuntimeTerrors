package Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.Player;

public class Play_Screen implements Screen {
    private MyGame game;
    Player chef1;
    // sets screen size
    private Viewport game_port;
    private OrthographicCamera gamecam;
    TextureAtlas atlas;

    Texture texture; // temp

    public Play_Screen(MyGame game){
        this.game = game;
        //Texture img = new Texture("Owlet_Monster.png");
        atlas = new TextureAtlas(Gdx.files.internal("ENG1_Assets_V1.atlas"));
        chef1 = new Player(atlas.findRegion("C_Blue_N"));
        gamecam = new OrthographicCamera();
        game_port = new FitViewport(MyGame.V_WIDTH,MyGame.V_HEIGHT ,gamecam);
    }
    @Override
    public void show(){

    }

    @Override
    public void render (float delta) {
        ScreenUtils.clear(0, 1, 0, 1);
        gamecam.update();
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        game.font.draw(game.batch, "The Main game screen", 100, 100);
        chef1.Draw(game.batch);
        game.batch.end();
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
        atlas.dispose();
    }

}
