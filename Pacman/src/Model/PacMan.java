/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.logging.Level;
import java.util.logging.Logger;


public class PacMan extends Entity {


    public PacMan(int x, int y, Grid grille) {
        super(x,y,grille);
    }

    public void start() {
        new Thread(this).start();
    }

    public void run() {
        while(true) {
            if(direction==Direction.NORTH){
                if (grille.isValideMove(x, y-1)) {
                    grille.setElement(x, y, new Ground());
                    y--;
                }
            }else if(direction==Direction.EAST){
                if (grille.isValideMove(x+1, y)) {
                    grille.setElement(x, y, new Ground());
                    x++;
                }
            }else if(direction==Direction.SOUTH){
                if (grille.isValideMove(x, y+1)) {
                    grille.setElement(x, y, new Ground());
                    y++;
                }
            }else if(direction==Direction.WEST){
                if (grille.isValideMove(x-1, y)) {
                    grille.setElement(x, y, new Ground());
                    x--;
                }
            }
            setChanged();
            notifyObservers(); // notification de l'observer

            try {
                Thread.sleep(250); // pause
            } catch (InterruptedException ex) {
                Logger.getLogger(PacMan.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
