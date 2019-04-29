/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Observable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author fred
 */
public class SimplePacMan extends Entity {


    public SimplePacMan(int _sizeX, int _sizeY) {
        super(_sizeX,_sizeY);
        x = 0; y = 0;
        
        sizeX = _sizeX;
        sizeY = _sizeY;
       
    }

    public void start() {
        new Thread(this).start();
    }


    @Override
    public void run() {
        while(true) { // spm descent dans la grille à chaque pas de temps

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
                Logger.getLogger(SimplePacMan.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
    
    }
    
    
}
