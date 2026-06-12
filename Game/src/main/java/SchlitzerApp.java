import config.Config;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Player;
import model.Snake;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SchlitzerApp extends GameApplication {

    private final Random random = new Random();

    private final List<Food> foods = new ArrayList<>();
    private Player localPlayer;

    private Group worldGroup;
    private Group foodGroup;

    private Text scoreText;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(Config.WIDTH);
        settings.setHeight(Config.HEIGHT);
        settings.setTitle(Config.TITLE);
        settings.setVersion(Config.VERSION);
        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                return new MainMenu();
            }
        });
    }

    @Override
    protected void initGame() {
        FXGL.getGameScene().setBackgroundColor(Config.BACKGROUND_COLOR);

        worldGroup = new Group();
        foodGroup = new Group();

        worldGroup.getChildren().addAll(foodGroup);
        FXGL.getGameScene().addUINode(worldGroup);

        createBackgroundGrid();

        Snake snake = new Snake(new Point2D(Config.WORLD_SIZE / 2, Config.WORLD_SIZE / 2), Color.LIMEGREEN);
        localPlayer = new Player("Player 1", snake);

        worldGroup.getChildren().add(snake.getSnakeGroup());

        spawnFood();

        scoreText = new Text("Punkte: 0");
        scoreText.setFill(Config.SCORE_TEXT_COLOR);
        scoreText.setStyle(Config.SCORE_TEXT_STYLE);
        scoreText.setTranslateX(20);
        scoreText.setTranslateY(40);
        FXGL.getGameScene().addUINode(scoreText);
    }

    private boolean spacePressed = false;

    @Override
    protected void initInput() {
        FXGL.onKeyDown(KeyCode.ESCAPE, () -> FXGL.getGameController().exit());
        FXGL.onKey(KeyCode.SPACE, () -> spacePressed = true);
    }

    @Override
    protected void onUpdate(double tpf) {
        localPlayer.update(tpf, Config.WORLD_SIZE, Config.WIDTH, Config.HEIGHT, spacePressed);
        checkFoodCollision();
        handleSprintFood();
        renderWorld();

        spacePressed = false;
    }

    private void handleSprintFood() {
        Snake snake = localPlayer.getSnake();
        if (snake.shouldSpawnSprintFood()) {
            localPlayer.subtractScore(Config.SPRINT_SCORE_LOSS);
            scoreText.setText("Punkte: " + localPlayer.getScore());
            snake.shrink(Config.SPRINT_SHRINK_LENGTH, Config.SPRINT_SHRINK_RADIUS, Config.SPRINT_SHRINK_SPEED);
            addFoodAt(snake.getLastSprintFoodPos(), FoodType.SMALL);
        }
    }

    private void addFoodAt(Point2D pos, @NotNull FoodType type) {
        double radius;
        switch (type) {
            case SMALL: radius = Config.FOOD_SMALL_RADIUS; break;
            case LARGE: radius = Config.FOOD_LARGE_RADIUS; break;
            default: radius = Config.FOOD_MEDIUM_RADIUS; break;
        }

        Circle view = new Circle(radius);
        view.setFill(Color.hsb(random.nextDouble() * 360, 0.85, 1.0));
        view.setStroke(Color.WHITE);
        view.setStrokeWidth(1);

        Food food = new Food(pos, radius, view, type);
        foods.add(food);
        foodGroup.getChildren().add(view);
    }

    private void createBackgroundGrid() {
        Group grid = new Group();
        double gridSize = Config.GRID_SIZE;

        for (int x = 0; x <= Config.WORLD_SIZE; x += gridSize) {
            Rectangle line = new Rectangle(x, 0, 1, Config.WORLD_SIZE);
            line.setFill(Config.GRID_COLOR);
            grid.getChildren().add(line);
        }

        for (int y = 0; y <= Config.WORLD_SIZE; y += gridSize) {
            Rectangle line = new Rectangle(0, y, Config.WORLD_SIZE, 1);
            line.setFill(Config.GRID_COLOR);
            grid.getChildren().add(line);
        }

        worldGroup.getChildren().addFirst(grid);
    }

    private void spawnFood() {
        foods.clear();
        foodGroup.getChildren().clear();
        for (int i = 0; i < Config.FOOD_COUNT; i++) {
            addFood();
        }
    }

    private void addFood() {
        double x = random.nextDouble() * Config.WORLD_SIZE;
        double y = random.nextDouble() * Config.WORLD_SIZE;
        addFoodAt(new Point2D(x, y), FoodType.MEDIUM);
    }

    private void checkFoodCollision() {
        Snake snake = localPlayer.getSnake();
        Point2D headPos = snake.getHeadWorldPosition();
        double snakeRadius = snake.getSnakeRadius();

        Iterator<Food> iterator = foods.iterator();
        int foodToSpawn = 0;

        while (iterator.hasNext()) {
            Food food = iterator.next();
            double distance = headPos.distance(food.position);

            if (distance < snakeRadius + food.radius) {
                iterator.remove();
                foodGroup.getChildren().remove(food.view);

                int scoreGain;
                double growthMult;

                switch (food.type) {
                    case SMALL:
                        scoreGain = Config.FOOD_SMALL_SCORE;
                        growthMult = Config.FOOD_SMALL_GROWTH_MULT;
                        break;
                    case LARGE:
                        scoreGain = Config.FOOD_LARGE_SCORE;
                        growthMult = Config.FOOD_LARGE_GROWTH_MULT;
                        break;
                    default:
                        scoreGain = Config.FOOD_MEDIUM_SCORE;
                        growthMult = Config.FOOD_MEDIUM_GROWTH_MULT;
                        break;
                }

                localPlayer.addScore(scoreGain);
                scoreText.setText("Punkte: " + localPlayer.getScore());

                snake.grow(Config.GROWTH_LENGTH, Config.GROWTH_RADIUS, Config.GROWTH_SPEED, growthMult);

                if (food.type == FoodType.MEDIUM) {
                    foodToSpawn++;
                }
            }
        }

        for (int i = 0; i < foodToSpawn; i++) {
            addFood();
        }
    }

    private void renderWorld() {
        Point2D headPos = localPlayer.getSnake().getHeadWorldPosition();

        double cameraX = headPos.getX() - Config.WIDTH / 2.0;
        double cameraY = headPos.getY() - Config.HEIGHT / 2.0;

        worldGroup.setTranslateX(-cameraX);
        worldGroup.setTranslateY(-cameraY);

        localPlayer.getSnake().render(localPlayer.getSnake().getSnakeRadius() * Config.SNAKE_SPACING_FACTOR);

        for (Food food : foods) {
            food.view.setCenterX(food.position.getX());
            food.view.setCenterY(food.position.getY());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private enum FoodType {
        SMALL, MEDIUM, LARGE
    }

    private static class Food {
        private final Point2D position;
        private final double radius;
        private final Circle view;
        private final FoodType type;

        private Food(Point2D position, double radius, Circle view, FoodType type) {
            this.position = position;
            this.radius = radius;
            this.view = view;
            this.type = type;
        }
    }
}
