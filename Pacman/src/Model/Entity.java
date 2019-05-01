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


public abstract class Entity extends Observable implements Runnable,GridElement {

    int  x,y;

    Direction direction;



    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
        direction=Direction.EAST;
    }

    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x=x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y=y;
    }

    public void setDirection(Direction d) {
        this.direction=d;
    }

    public Direction getDirection(){
        return this.direction;
    }

    public void start() {
        new Thread(this).start();
    }

    public void initXY() {
        x = 0;
        y = 0;
    }

    @Override
    public void run() {
        while(true) { // pacman descent dans la grille à chaque pas de temps

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
                Logger.getLogger(PacMan.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }





}
