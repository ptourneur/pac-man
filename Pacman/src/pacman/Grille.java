package pacman;


import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Epulapp
 */
public class Grille extends Observable implements Runnable{
    private int x;
    private int y;
    private boolean[][] tab;
    
    public Grille(int x,int y){
        this.x=x;
        this.y=y;
       tab= new boolean[x][y]; 
       for(int i=0;i<10;i++){
           for(int j=0;j<10;j++){
               tab[i][j]=Math.random() < 0.5;
            }
       }
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
