/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PacMan extends Entity {

    private List<Ghost> ghostList;

    public PacMan(int x, int y, Grid grille, List<Ghost> ghostList) {
        super(x,y,grille);
        this.ghostList = ghostList;
    }

    public void start() {
        new Thread(this).start();
    }

    public void run() {
        boolean pacmanHasMoved = false;
        while(true) {
            if(direction==Direction.NORTH){
                if (grille.isValideMove(x, y-1)) {
                    grille.setElement(x, y, new Ground());
                    y--;
                    pacmanHasMoved = true;
                }
            }else if(direction==Direction.EAST){
                if (grille.isValideMove(x+1, y)) {
                    grille.setElement(x, y, new Ground());
                    x++;
                    pacmanHasMoved = true;
                }
            }else if(direction==Direction.SOUTH){
                if (grille.isValideMove(x, y+1)) {
                    grille.setElement(x, y, new Ground());
                    y++;
                    pacmanHasMoved = true;
                }
            }else if(direction==Direction.WEST){
                if (grille.isValideMove(x-1, y)) {
                    grille.setElement(x, y, new Ground());
                    x--;
                    pacmanHasMoved = true;
                }
            }
            if (pacmanHasMoved) {
                if (((Ground)grille.getElement(x, y)).getItem() instanceof SuperPacGum) {
                    ghostList.forEach(ghost -> ghost.setVulnerable());
                }
                setChanged();
                notifyObservers(); // notification de l'observer
            }
            try {
                Thread.sleep(250); // pause
            } catch (InterruptedException ex) {
                Logger.getLogger(PacMan.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
