/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Ghost extends Entity {
    private int numGhost;

    public Ghost(int x, int y, int numGhost, Grid grille, CyclicBarrier cyclicBarrier) {
        super(x,y, grille,cyclicBarrier);
        this.numGhost = numGhost;
    }


    public int getNumGhost() {
        return numGhost;
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
                if (grille.isValideMove(x, y-1)) {
                    y--;
                }
            }else if(direction==Direction.EAST){
                if (grille.isValideMove(x+1, y)) {
                    x++;
                }
            }else if(direction==Direction.SOUTH){
                if (grille.isValideMove(x, y+1)) {
                    y++;
                }
            }else if(direction==Direction.WEST){
                if (grille.isValideMove(x-1, y)) {
                    x--;
                }
            }

            setChanged();
            notifyObservers(); // notification de l'observer

            try {
                Thread.sleep(250); // pause
                this.getCyclicBarrier().await();
            } catch (InterruptedException ex) {
                Logger.getLogger(Ghost.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }

    }


}
