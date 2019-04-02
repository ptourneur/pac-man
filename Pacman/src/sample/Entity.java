package sample;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Entity implements Runnable{
    /**
     * BaseColor enum.
     */

    Grille grille;

    private int x;
    private int y;
    private Direction direction;

    public Entity(){
        this.x=4;
        this.y=4;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Entity(Grille g){
        this.grille=g;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void run() {
        while(true){
            boolean is_move_valid= false;
            Random r = new Random();
            while(!is_move_valid){
                try {
                    Thread.currentThread().sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Grille.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.direction = Direction.getRandomDirection();
            }
        }
    }
}
