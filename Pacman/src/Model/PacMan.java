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

}
