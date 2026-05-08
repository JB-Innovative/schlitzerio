package config;

import javafx.scene.paint.Color;

public class Config {
    // Window Settings
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    public static final String TITLE = "Slither IO - Java FXGL";
    public static final String VERSION = "1.2";

    // World Settings
    public static final double WORLD_SIZE = 5000;
    public static final int FOOD_COUNT = 250;
    public static final double GRID_SIZE = 100;
    public static final Color BACKGROUND_COLOR = Color.rgb(18, 18, 28);
    public static final Color GRID_COLOR = Color.rgb(40, 40, 55);

    // model.Snake Settings
    public static final double SNAKE_START_RADIUS = 8;
    public static final double SNAKE_MIN_RADIUS = 8;
    public static final double SNAKE_MAX_RADIUS = 26;

    public static final double SNAKE_START_LENGTH = 10.0;
    public static final double SNAKE_MIN_LENGTH = 10.0;

    public static final double SNAKE_NORMAL_SPEED = 140;
    public static final double SNAKE_MIN_SPEED = 120;
    public static final double SNAKE_MAX_SPEED = 250;
    public static final double SPRINT_SPEED_MULTIPLIER = 2.2;
    public static final double SNAKE_TURNING_SPEED = 5.0; // Radians per second

    public static final double SNAKE_SPACING_FACTOR = 0.85;

    public static final double PART_STROKE_WITH = 2.0;

    // Sprint Settings
    public static final int SPRINT_MIN_SCORE = 10;
    public static final double SPRINT_FOOD_SPAWN_INTERVAL = 0.15;
    public static final int SPRINT_SCORE_LOSS = 1;
    public static final double SPRINT_SHRINK_LENGTH = 1.0;
    public static final double SPRINT_SHRINK_RADIUS = 0.05;
    public static final double SPRINT_SHRINK_SPEED = 0.5;

    // Growth Settings
    public static final double GROWTH_LENGTH = 1.0;
    public static final double GROWTH_RADIUS = 0.05;
    public static final double GROWTH_SPEED = 0.5;

    // Food Settings
    public static final int FOOD_SMALL_SCORE = 1;
    public static final double FOOD_SMALL_RADIUS = 3.5;
    public static final double FOOD_SMALL_GROWTH_MULT = 0.1;

    public static final int FOOD_MEDIUM_SCORE = 10;
    public static final double FOOD_MEDIUM_RADIUS = 6.0;
    public static final double FOOD_MEDIUM_GROWTH_MULT = 1.0;

    public static final int FOOD_LARGE_SCORE = 100;
    public static final double FOOD_LARGE_RADIUS = 12.0;
    public static final double FOOD_LARGE_GROWTH_MULT = 8.0;

    // UI Settings
    public static final Color SCORE_TEXT_COLOR = Color.WHITE;
    public static final String SCORE_TEXT_STYLE = "-fx-font-size: 24px; -fx-font-weight: bold;";
}
