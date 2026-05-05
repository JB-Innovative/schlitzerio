import com.almasb.fxgl.entity.Entity;

import java.util.ArrayList;
import java.util.LinkedList;

public class Snake {
    public int id;
    public int size;
    public int points;
    public LinkedList<int[]> path;
    public ArrayList<Entity> segments;

    public boolean checkCollision(Entity e) {
        return false;
    }
    private void addPoints(int points) {
        this.points += points;
    }
    private void calcSize() {
        //TODO real calculation
        this.size = this.segments.size();
    }
    @Override  //TODO Fehler
    public void onUpdate{

    }

}
