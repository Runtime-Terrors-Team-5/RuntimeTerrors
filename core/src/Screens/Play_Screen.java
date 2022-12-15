package Screens;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGame;
import com.mygdx.game.Player;

public class Play_Screen implements Screen {
    private MyGame game;
    Player player;

    Texture texture; // temp

    public Play_Screen(MyGame game){
        this.game = game;
        Texture img = new Texture("Owlet_Monster.png");
        player = new Player(img);
    }
    @Override
    public void show(){

    }

    @Override
    public void render (float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        game.batch.begin();
        player.Draw(game.batch);
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
