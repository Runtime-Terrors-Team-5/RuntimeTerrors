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
import com.sun.org.apache.xpath.internal.operations.Or;

public class Recipe_Hud {
    public Stage stage;
    private Viewport viewport;

    Label Orders;
    Label Recipe1;
    Label Recipe2;
    Label Recipe3;

    public Recipe_Hud(SpriteBatch sb){
        String Burger = "Lettuce, Patty, Bun";
        String Lettuce = "Lettuce, Tomato, Onion";
        viewport = new FitViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        Table table = new Table();
        table.setFillParent(true);

        Orders = new Label("ORDERS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Recipe1 = new Label(("1:"+Lettuce), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Recipe2 = new Label(("2:"+Burger), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Recipe3 = new Label(("3:"+Lettuce), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Orders.setScale(2,2);
        Recipe1.setScale(10,10);
        Recipe3.setScale(10,10);
        Recipe2.setScale(10,10);

        table.add(Orders).expandX();
        table.row();
        table.add(Recipe1).expandX();
        table.row();
        table.add(Recipe2).expandX();
        table.row();
        table.add(Recipe3).expandX();
        table.row();
        stage.addActor(table);
        }


}
