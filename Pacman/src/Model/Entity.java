/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Observable;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class Entity extends Observable implements Runnable,GridElement {

    protected int  x,y;
    protected Grid grille;
    private CyclicBarrier cyclicBarrier;
    Direction direction;


    public CyclicBarrier getCyclicBarrier() {
        return cyclicBarrier;
    }

    public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public boolean isInGrid(int x, int y) {

        if(x < 0 || x < 0) return false;
        if(x >= 21 || y >= 19) return false;

        //Check if a position is valid in the grid
        if ((grille.getElement(x,y)) instanceof  Wall) {
            return false;
        }
        return true;
    }


    public Entity(int x, int y, Grid grille, CyclicBarrier cyclicBarrier) {
        this.x = x;
        this.y = y;
        this.grille = grille;
        this.cyclicBarrier = cyclicBarrier;
        direction=Direction.EAST;
    }

    public synchronized int getX() {
        return x;
    }

    @Override
    public synchronized void setX(int x) {
        this.x=x;
    }

    public synchronized int getY() {
        return y;
    }

    @Override
    public synchronized void setY(int y) {
        this.y=y;
    }

    public synchronized void setDirection(Direction d) {
        this.direction=d;
    }

    public synchronized Direction getDirection(){
        return this.direction;
    }

    public void start() {
        new Thread(this).start();
    }
}
