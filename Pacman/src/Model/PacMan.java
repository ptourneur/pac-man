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

    private int nbEatenPacgum=0,nbEatenSuperPacgum=0;
    public PacMan(int x, int y, Grid grille,CyclicBarrier cyclicBarrier) {
        super(x,y,grille,cyclicBarrier);

    }

    public void start() {
        new Thread(this).start();
    }

    public void eatPacgum(){
        if (((Ground) grille.getElement(x,y)).getItem() instanceof  PacGum) {
            nbEatenPacgum++;
            //System.out.println(nbEatenPacgum);
        }else if( ((Ground) grille.getElement(x,y)).getItem() instanceof  SuperPacGum){
            nbEatenSuperPacgum++;
            grille.scareGhots();
        }
    }

    public Position getOneGhostSpawn(){
        for(int i=0;i<grille.getRowCount();i++) {
            for(int j=0;j<grille.getColumnCount();j++) {
                if((this.grille.getElement(i,j) instanceof Ground)){
                    if ( ((Ground) grille.getElement(i,j)).getItem() instanceof  GhostSpawn ){
                        if( this.grille.getElement(i,j) instanceof Ground && ((Ground) grille.getElement(i,j)).getItem() instanceof GhostSpawn){
                            return new Position(i,j);
                        }
                    }
                }
            }
        }

        return new Position(10,9);
    }

    public boolean checkGameOver(){
        for(int i=0;i<grille.getGhosts().size();i++){
            if(grille.getGhosts().get(i).getX()==x && grille.getGhosts().get(i).getY()==y){
                if((grille.getGhosts().get(i).isScared()) ){
                    Position p = getOneGhostSpawn();
                    grille.getGhosts().get(i).setX(p.getX());
                    grille.getGhosts().get(i).setY(p.getY());
                    grille.getGhosts().get(i).setNotScared();
                }else{
                    return true;
                }

            }
        }
        return false;
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
