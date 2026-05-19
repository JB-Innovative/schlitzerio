package model;

import com.almasb.fxgl.entity.Entity;
import config.Config;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Snake {
    private LinkedList<Point2D> trail = new LinkedList<>();
    private List<Circle> snakeParts = new ArrayList<>();
    private Group snakeGroup = new Group();

    private Point2D headWorldPosition;
    private double normalSpeed = Config.SNAKE_NORMAL_SPEED;
    private double sprintSpeed = Config.SNAKE_NORMAL_SPEED * Config.SPRINT_SPEED_MULTIPLIER;
    private double currentSpeed = Config.SNAKE_NORMAL_SPEED;
    private double snakeRadius = Config.SNAKE_START_RADIUS;
    private double targetLength = Config.SNAKE_START_LENGTH;
    private Color color;
    private Point2D currentDirection = new Point2D(1, 0);

    private double sprintTimer = 0;
    private boolean requestSpawnFood = false;
    private Point2D lastSprintFoodPos;

    public boolean checkCollision(Entity e) {
        return false;
    }

    public Snake(Point2D startPos, Color color) {
        this.headWorldPosition = startPos;
        this.color = color;
        createInitialSnake();
    }

    private void createInitialSnake() {
        for (int i = 0; i < (int) targetLength; i++) {
            Point2D point = headWorldPosition.subtract(i * snakeRadius * 1.2, 0);
            trail.add(point);

            Circle part = createPart(i == 0);
            snakeParts.add(part);
            snakeGroup.getChildren().add(part);
        }
    }

    private Circle createPart(boolean isHead) {
        Circle part = new Circle(snakeRadius);
        part.setFill(isHead ? color : color.darker());
        part.setStroke(color.darker().darker());
        part.setStrokeWidth(Config.PART_STROKE_WITH);
        return part;
    }

    public void move(Point2D targetDirection, double tpf, double worldSize, boolean isSprinting) {
        if (targetDirection.magnitude() > 0) {
            currentSpeed = isSprinting ? sprintSpeed : normalSpeed;

            targetDirection = targetDirection.normalize();

            double targetAngle = Math.atan2(targetDirection.getY(), targetDirection.getX());
            double currentAngle = Math.atan2(currentDirection.getY(), currentDirection.getX());

            double angleDiff = targetAngle - currentAngle;

            // Normalize angle difference to [-PI, PI]
            while (angleDiff > Math.PI) angleDiff -= 2 * Math.PI;
            while (angleDiff < -Math.PI) angleDiff += 2 * Math.PI;

            double maxTurn = Config.SNAKE_TURNING_SPEED * tpf;
            double actualTurn = Math.max(-maxTurn, Math.min(maxTurn, angleDiff));

            double newAngle = currentAngle + actualTurn;
            currentDirection = new Point2D(Math.cos(newAngle), Math.sin(newAngle));

            headWorldPosition = headWorldPosition.add(currentDirection.multiply(currentSpeed * tpf));

            double clampedX = Math.max(0, Math.min(worldSize, headWorldPosition.getX()));
            double clampedY = Math.max(0, Math.min(worldSize, headWorldPosition.getY()));
            headWorldPosition = new Point2D(clampedX, clampedY);

            if (isSprinting) {
                sprintTimer += tpf;
                if (sprintTimer >= Config.SPRINT_FOOD_SPAWN_INTERVAL) {
                    requestSpawnFood = true;
                    lastSprintFoodPos = getTailPosition();
                    sprintTimer = 0;
                }
            } else {
                sprintTimer = 0;
            }
        }
    }

    private Point2D getTailPosition() {
        if (snakeParts.size() > 2) return headWorldPosition;
        Circle lastPart = snakeParts.get(snakeParts.size() - 1);
        return new Point2D(lastPart.getCenterX(), lastPart.getCenterY());
    }

    public boolean shouldSpawnSprintFood() {
        boolean res = requestSpawnFood;
        requestSpawnFood = false;
        return res;
    }

    public Point2D getLastSprintFoodPos() {
        return lastSprintFoodPos;
    }

   public void shrink(double lengthAmount, double radiusAmount, double speedAmount) {
        targetLength = Math.max(Config.SNAKE_MIN_LENGTH, targetLength - lengthAmount);
        snakeRadius = Math.max(Config.SNAKE_MIN_RADIUS, snakeRadius - radiusAmount);
        normalSpeed = Math.max(Config.SNAKE_MIN_SPEED, normalSpeed - speedAmount);
        sprintSpeed = normalSpeed * Config.SPRINT_SPEED_MULTIPLIER;
    }

    public void updateTrail() {
        trail.addFirst(headWorldPosition);

        if (trail.size() > (int) targetLength * 100) {
            trail.removeLast();
        }

        while (snakeParts.size() < (int) targetLength) {
            Circle part = createPart(false);
            snakeParts.add(part);
            snakeGroup.getChildren().add(part);
        }

        while (snakeParts.size() > (int) targetLength) {
            Circle removed = snakeParts.removeLast();
            snakeGroup.getChildren().remove(removed);
        }

        for (Circle part : snakeParts) {
            part.setRadius(snakeRadius);
        }

        if (!snakeParts.isEmpty()) {
            snakeParts.getFirst().setFill(color);
        }
    }

    public void render(double spacing) {
        Point2D lastPartPos = headWorldPosition;
        int trailIndex = 0;

        for (int i = 0; i < snakeParts.size(); i++) {
            Circle part = snakeParts.get(i);

            if (i == 0) {
                part.setCenterX(headWorldPosition.getX());
                part.setCenterY(headWorldPosition.getY());
                part.setVisible(true);
            } else {
                boolean found = false;
                while (trailIndex < trail.size()) {
                    Point2D p = trail.get(trailIndex);
                    if (p.distance(lastPartPos) >= spacing) {
                        part.setCenterX(p.getX());
                        part.setCenterY(p.getY());
                        lastPartPos = p;
                        found = true;
                        break;
                    }
                    trailIndex++;
                }
                part.setVisible(found);
            }

            if (part.isVisible()) {
                double scale = 1.0 - i / (double) snakeParts.size() * 0.35;
                part.setRadius(Math.max(5, snakeRadius * scale));
            }
        }
    }

    public void grow(double lengthAmount, double radiusAmount, double speedAmount, double multiplier) {
        targetLength += lengthAmount * multiplier;
        snakeRadius = Math.min(Config.SNAKE_MAX_RADIUS, snakeRadius + radiusAmount * multiplier);
        normalSpeed = Math.min(Config.SNAKE_MAX_SPEED, normalSpeed + speedAmount * multiplier);
        sprintSpeed = normalSpeed * Config.SPRINT_SPEED_MULTIPLIER;
    }

    public Point2D getHeadWorldPosition() {
        return headWorldPosition;
    }

    public double getSnakeRadius() {
        return snakeRadius;
    }

    public Group getSnakeGroup() {
        return snakeGroup;
    }
}
