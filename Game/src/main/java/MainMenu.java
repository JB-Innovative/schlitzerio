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

public class MainMenu extends FXGLMenu {

    public MainMenu() {
        super(MenuType.MAIN_MENU);

        Rectangle bg = new Rectangle(Config.WIDTH, Config.HEIGHT, Color.rgb(10, 10, 20));
        getContentRoot().getChildren().add(bg);

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPrefSize(Config.WIDTH, Config.HEIGHT);

        Text title = new Text(Config.TITLE);
        title.setFill(Color.LIMEGREEN);
        title.setStyle("-fx-font-size: 64px; -fx-font-weight: bold;");

        Button btnPlay = new Button("SPIEL STARTEN");
        btnPlay.setStyle("-fx-font-size: 24px; -fx-base: #32CD32; -fx-text-fill: white;");
        btnPlay.setOnAction(e -> fireNewGame());

        Button btnExit = new Button("BEENDEN");
        btnExit.setStyle("-fx-font-size: 24px; -fx-base: #CD5C5C; -fx-text-fill: white;");
        btnExit.setOnAction(e -> fireExit());

        vbox.getChildren().addAll(title, btnPlay, btnExit);
        getContentRoot().getChildren().add(vbox);
    }
}
