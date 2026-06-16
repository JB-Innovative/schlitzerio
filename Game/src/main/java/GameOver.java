import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import config.Config;
public class GameOver extends FXGLMenu {

    public GameOver()
    {
        super(MenuType.MAIN_MENU);
        Rectangle bg=new Rectangle(Config.WIDTH, Config.HEIGHT, Color.rgb(10, 10, 20));
        getContentRoot().getChildren().add(bg);

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPrefSize(Config.WIDTH, Config.HEIGHT);

        Text title = new Text(Config.TITLE);
        title.setFill(Color.LIMEGREEN);
        title.setStyle("-fx-font-size: 64px; -fx-font-weight: bold;");

        Button playAgain = new Button("NEU STARTEN");
        playAgain.setStyle("-fx-font-size: 24px; -fx-base:#32CD32; -fx-text-fill: white;");
        playAgain.setOnAction(e->fireNewGame());

        Button menu = new Button("ZUM HAUPTMENU");
        menu.setStyle("-fx-font-size: 24px; -fx-base: #CD5C5C; -fx-text-fill: white;");
        menu.setOnAction( e-> fireExitToMainMenu());

        Button exit = new Button("Spiel beenden");
        exit.setStyle("-fx-font-size: 24px; -fx-base: #CD5C5C; -fx-text-fill: white;");
        exit.setOnAction( e-> fireExit());

        vbox.getChildren().addAll(title, playAgain, exit, menu);
        getContentRoot().getChildren().add(vbox);
    }
}
