package sample;

import java.util.Observable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Grille extends Observable implements Runnable{
    private int x;
    private int y;
    private Entity player;
    private boolean[][] tab;


    public Entity getPlayer() {
        return player;
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }

    public Grille(int x, int y){
        this.x=x;
        this.y=y;
        this.player=player;
        tab= new boolean[x][y];
        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                tab[i][j]=Math.random() > 0.2;
            }
        }
    }
    public void addEntity(Entity e){
        this.player=e;
    }
    public boolean isValidMove(int x,int y,Direction d,Entity e){
        if(d==Direction.NORTH){
            if(  (y-1)< 0 || !tab[x][y-1]  ){
                return false;
            }
            player.setX(x);
            player.setY(y-1);
        }
        else if(d==Direction.EAST){
            if((x+1)>=this.x || !tab[x+1][y] ){
                return false;
            }
            player.setX(x+1);
            player.setY(y);
        }
        else if(d==Direction.SOUTH){
            if((y+1)>=this.y || !tab[x][y+1] ){
                return false;
            }
            player.setX(x);
            player.setY(y+1);
        }
        else if(d==Direction.WEST){
            if( (x-1)< 0 || !tab[x-1][y]   ){
                return false;
            }
            player.setX(x-1);
            player.setY(y);
        }

        return true;
    }
    public boolean move(Direction d, Entity e){
        if(isValidMove(e.getX(),e.getY(),d,e) ){
            return true;
        }
        return false;
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

    public boolean[][] getTab() {
        return tab;
    }

    public void setTab(boolean[][] tab) {
        this.tab = tab;
    }

    @Override
    public void run() {
        while(true){
            this.setChanged();
            this.notifyObservers();
            try {

                Thread.currentThread().sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Grille.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
