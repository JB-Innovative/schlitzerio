package model;
import config.Config;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;

public class Player {
    //objects:
    private Snake snake;
    private int score;
    private String name;

    public Player(String name, Snake snake) {

    }

    public void subtractScore(int amount) {
        score = Math.max(0, score - amount);
    }

    private boolean sprinting = false;

    public void toggleSprinting() { //TODO Noch_nicht_bekannt_ob_richtig
        this.sprinting = !this.sprinting;
    }
    //TODO Methode

    public void setVecsFromMousePos() {
        ;
    }
    public void update(double tpf, double worldSize, int width, int height, boolean isSprining){
     Point2D mouse = FXGL.getInput().getMousePositionUI();
     Point2D screenCenter = new Point2D(width / 2.0, height /2.0);
     Point2D direction = mouse.subtract(screenCenter);
    }


    //score:
    public void addScore(int amount) {
        score = score + amount;
    }

    public void substractScore(int amount) {
        score = (Math.max(0, score - amount));
    }

    //*/ Return_STH /*//
    public boolean isSprinting() {
        return sprinting;
    }

    public int getScore() {
        return score;
    }
    public Snake getSnake(){
        return snake;
    }
}