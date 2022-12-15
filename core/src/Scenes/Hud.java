package Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import javax.swing.text.View;
import java.awt.*;
// class creates labels in the for the Screens Credit_Screen class
public class Hud {
    public Stage stage;
    private Viewport viewport;

    Label credits_label;
    Label name1;
    Label name2;
    Label name3;
    Label name4;
    Label name5;
    Label name6;

    public Hud(SpriteBatch sb){
        viewport = new FitViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        Table table = new Table();
        table.setFillParent(true);

        credits_label = new Label("CREDITS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        name1 = new Label("Callum", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        name2 = new Label("Chase", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        name3 = new Label("Jack", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        name4 = new Label("Kamrul", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        name5 = new Label("Helene", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        name6 = new Label("Craig", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(credits_label).expandX().padTop(10);
        table.row();
        table.add(name1).expandX();
        table.row();
        table.add(name2).expandX();
        table.row();
        table.add(name3).expandX();
        table.row();
        table.add(name4).expandX();
        table.row();
        table.add(name5).expandX();
        table.row();
        table.add(name6).expandX();
        stage.addActor(table);
    }
}
