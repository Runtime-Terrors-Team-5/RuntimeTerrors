package Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.customer;
import java.util.Queue;

public class Recipe_Hud {

    public Stage stage;
    private Viewport viewport;

    Label OrdersLabel;
    Label Recipe1;
    Label Recipe2;
    Label Recipe3;

    public Recipe_Hud(SpriteBatch sb, Queue<customer> Orders) {
        // creating the orders on screen
        String Burger = "Lettuce, Patty, Bun";
        String Lettuce = "Lettuce, Tomato, Onion";
        viewport = new FitViewport(MyGame.V_WIDTH * 2, MyGame.V_HEIGHT * 2,
            new OrthographicCamera());
        stage = new Stage(viewport, sb);
        Table table = new Table();
        table.setFillParent(true);

        OrdersLabel = new Label("ORDERS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        /*
        Recipe1 = new Label(("1:"+Lettuce), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Recipe2 = new Label(("2:"+Burger), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Recipe3 = new Label(("3:"+Lettuce), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
*/

        table.add(OrdersLabel).left();
        table.row();
        for (int i = 0; i < Orders.size(); i++) {
            customer temp = (customer) Orders.toArray()[i];
            String name = temp.getItemName().substring(2);
            Recipe1 = new Label((i + 1) + ": " + name,
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            table.add(Recipe1).left();
            table.row();
        }


        /*
        table.add(Recipe2).left();
        table.row();
        table.add(Recipe3).left();
        table.row();*/

        table.left().left();

        table.setOrigin(0, 0);
        stage.addActor(table);


    }

    // updates order list once order completed
    public void updateHUB(Queue<customer> Orders) {
        stage.clear();
        Table table = new Table();
        table.setFillParent(true);
        table.add(OrdersLabel).left();
        table.row();
        for (int i = 0; i < Orders.size(); i++) {
            customer temp = (customer) Orders.toArray()[i];
            String name = temp.getItemName().substring(2);
            Recipe1 = new Label((i + 1) + ": " + name,
                new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            table.add(Recipe1).left();
            table.row();
        }

        table.left().left();

        table.setOrigin(0, 0);
        stage.addActor(table);

    }


}
