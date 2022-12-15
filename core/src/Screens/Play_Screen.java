package Screens;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    Player player;
    // sets screen size
    private Viewport game_port;
    private OrthographicCamera gamecam;

    Texture texture; // temp

    public Play_Screen(MyGame game){
        this.game = game;
        Texture img = new Texture("Owlet_Monster.png");
        player = new Player(img);
        gamecam = new OrthographicCamera();
        game_port = new FitViewport(MyGame.V_WIDTH,MyGame.V_HEIGHT ,gamecam);
    }
    @Override
    public void show(){

    }

    @Override
    public void render (float delta) {
        ScreenUtils.clear(1, 0, 0, 1);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.Draw(game.batch);
        game.batch.end();
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
