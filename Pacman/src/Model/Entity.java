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
public abstract class Entity extends Observable implements Runnable,GridElement {

    int  x,y,sizeX, sizeY;

    Direction direction;



    public Entity(int _sizeX, int _sizeY) {
        x = 0; y = 0;

        sizeX = _sizeX;
        sizeY = _sizeY;
        direction=Direction.SOUTH;
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





}
