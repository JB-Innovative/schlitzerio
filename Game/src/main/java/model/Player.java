package model;
import config.Config;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;

public class Player {
   //objects:
    private Snake snake;
    private int score = 0;
    private String name;

    private boolean sprinting = false;
    public void toggleSprinting(){ //TODO Noch_nicht_bekannt_ob_richtig
        this.sprinting = !this.sprinting;
    }
    //TODO Methode

    public void setVecsFromMousePos{
        ;
    }
//score:
public void addScore(int amount){
    score = score + amount;
}
public void substractScore(int amount){
    score = (Math.max(0,score - amount));
}

//*/ Return_STH /*//
public boolean isSprinting(){
    return sprinting;
}

public int getScore(){
    return score;
}