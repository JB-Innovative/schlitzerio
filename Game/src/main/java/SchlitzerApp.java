import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class SchlitzerApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Schlitzer IO");
    }

    @Override
    protected void initGame() {
    }

    @Override
    protected void initInput() {
    }

    public static void main(String[] args) {
        launch(args);
    }
}