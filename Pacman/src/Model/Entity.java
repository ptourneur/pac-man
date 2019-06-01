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

    protected int  x,y;
    protected Grid grille;
    protected Direction direction;
    protected boolean isDead;



    public Entity(int x, int y, Grid grille) {
        this.x = x;
        this.y = y;
        this.grille = grille;
        this.direction = Direction.EAST;
        this.isDead = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x=x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y=y;
    }

    public void setDirection(Direction d) {
        this.direction=d;
    }

    public Direction getDirection(){
        return this.direction;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void start() {
        new Thread(this).start();
    }
}
