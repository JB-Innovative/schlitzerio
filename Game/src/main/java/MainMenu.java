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

    public Menu()
    {
        super(MenuType.MAIN_MENU);
        Rectangle bg=new Rectangle(Config.WIDTH; Config.Color; Config.HEIGHT)
        getContentRoot().getChildren().add(bg);

        Vbox vbox= new VBox(20);
        vbox.setAlignement(Pos.CENTER);
        vbox.setPrefSize(Config.WIDTH; Config.HEIGHT);

        Text title = new Text(Config.TITLE);
        title.setfill(Color.LIMEGREEN);
        title.setStyle("-fx-font-size: 64px; -fx-font-weight: bold;");

        Button play = new Button("SPIEL STARTEN");
        play.setStyle("-fx-font-size: 64px; -fx-font-weight: bold;");
        play.setOnAction(e->fireNewGame());
    }
}
