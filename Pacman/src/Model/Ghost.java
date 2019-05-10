/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Ghost extends Entity {
    private int numGhost;

    public Ghost(int x, int y, int numGhost) {
        super(x,y);
        this.numGhost = numGhost;
    }


    public int getNumGhost() {
        return numGhost;
    }

    public Ghost(int x, int y) {
        super(0, 0);
        this.setX(x);
        this.setY(y);
    }


    public void setNumGhost(int numGhost) {
        this.numGhost = numGhost;
    }

    public void start() {
        new Thread(this).start();
    }



    @Override
    public void run() {
        while(true) { // spm descent dans la grille à chaque pas de temps

            direction=Direction.getRandomDirection();

            if(direction==Direction.NORTH){
                y--;
            }else if(direction==Direction.EAST){
                x++;
            }else if(direction==Direction.SOUTH){
                y++;
            }else if(direction==Direction.WEST){
                x--;
            }

            setChanged();
            notifyObservers(); // notification de l'observer

            try {
                Thread.sleep(250); // pause
            } catch (InterruptedException ex) {
                Logger.getLogger(Ghost.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }


}
