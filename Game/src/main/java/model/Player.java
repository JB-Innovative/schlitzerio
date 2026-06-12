package model;

import config.Config;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;

public class Player {
    private Snake schlange;
    private int score = 0;
    private String name;

    public Player(String name, Snake schlange) {
        this.name = name;
        this.schlange = schlange;
    }

    private boolean sprinting = false;

    public void update(double tpf, double worldSize, int width, int height, boolean isSprinting) {
        Point2D mouse = FXGL.getInput().getMousePositionUI();
        Point2D screenCenter = new Point2D(width / 2.0, height / 2.0);
        Point2D direction = mouse.subtract(screenCenter);

        sprinting = isSprinting && score > Config.SPRINT_MIN_SCORE;

        if (direction.magnitude() > 4) {
            schlange.move(direction, tpf, worldSize, sprinting);
        }

        schlange.updateTrail();
    }

    public void subtractScore(int amount) {
        score = Math.max(0, score - amount);
    }

    public boolean isSprinting() {
        return sprinting;
    }

    public void addScore(int amount) {
        score += amount;
    }

    public int getScore() {
        return score;
    }

    public Snake getSnake() {
        return schlange;
    }

    public String getName() {
        return name;
    }
}
