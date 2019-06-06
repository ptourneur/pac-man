/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PacMan extends Entity {
    private int nbEatenApple=0;
    private int nbEatenPacgum=0;
    private int lives=3;
    private int nbEatenGhost=0;
    private int nbEatenSuperPacgum=0;

    public int getNbEatenPacgum() {
        return nbEatenPacgum;
    }

    public void setNbEatenPacgum(int nbEatenPacgum) {
        this.nbEatenPacgum = nbEatenPacgum;
    }

    public int getNbEatenSuperPacgum() {
        return nbEatenSuperPacgum;
    }

    public void setNbEatenSuperPacgum(int nbEatenSuperPacgum) {
        this.nbEatenSuperPacgum = nbEatenSuperPacgum;
    }
    public int getScore(){
        int score=0;

        score+= nbEatenPacgum*10;
        score+= nbEatenApple*25;
        score+= nbEatenGhost*20;
        score-= nbEatenSuperPacgum*15;

        return score;
    }

    public int getLives() {
        return lives;
    }
    public int reduceOneLife() {
        lives--;
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getNbEatenGhost() {
        return nbEatenGhost;
    }

    public void setNbEatenGhost(int nbEatenGhost) {
        this.nbEatenGhost = nbEatenGhost;
    }

    public int getNbEatenApple() {
        return nbEatenApple;
    }

    public void setNbEatenApple(int nbEatenApple) {
        this.nbEatenApple = nbEatenApple;
    }

    public PacMan(int x, int y, Grid grille) {
        super(x,y,grille);
    }

    public void start() {
        new Thread(this).start();
    }

    public void eatPacgum(){
        if(grille.checkGameOver()){

        }
        else if (((Ground) grille.getElement(x,y)).getItem() instanceof  PacGum) {
            nbEatenPacgum++;
        }else if (((Ground) grille.getElement(x,y)).getItem() instanceof  Apple) {
            nbEatenApple++;
        }
        else if( ((Ground) grille.getElement(x,y)).getItem() instanceof  SuperPacGum){
            nbEatenSuperPacgum++;
            grille.scareGhots(true);
        }
    }

    public void run() {
        while(true) {
            if(x==0){
                x=18;
            }
            else if(x==18){
                x=0;
            }

            if(direction==Direction.NORTH){
                if (grille.isValideMove(x, y-1)) {
                    grille.setElement(x, y, new Ground());
                    y--;
                    eatPacgum();
                }
            }else if(direction==Direction.EAST){
                if (grille.isValideMove(x+1, y)) {
                    grille.setElement(x, y, new Ground());
                    x++;
                    eatPacgum();
                }
            }else if(direction==Direction.SOUTH){
                if (grille.isValideMove(x, y+1)) {
                    grille.setElement(x, y, new Ground());
                    y++;
                    eatPacgum();
                }
            }else if(direction==Direction.WEST){
                if (grille.isValideMove(x-1, y)) {
                    grille.setElement(x, y, new Ground());
                    x--;
                    eatPacgum();


                }
            }
            //grille.checkGameOver();
            setChanged();
            notifyObservers(); // notification de l'observer

            try {
                Thread.sleep(175); // pause

                this.getCyclicBarrier().await();
            } catch (InterruptedException ex) {
                Logger.getLogger(PacMan.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
