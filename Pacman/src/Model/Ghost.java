/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Ghost extends Entity {
    private int numGhost;
    private boolean moveable;
    private int isScared;

    public boolean isMoveable() {
        return moveable;
    }
    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }
    public boolean isScared() {
        return isScared > 0;
    }
    public boolean isSoonNotScared(){
        return (isScared > 0 && isScared <= 2000);
    }
    public void setScared() {
        isScared = 9000;
    }
    public void setNotScared() {
        isScared = 0;
    }
    public int getScared() {
        return this.isScared;
    }
    public void reduceTimeScared(int time) {
        isScared = isScared - time;
    }

    private ArrayList<GhostStep> possibleStep;
    public Ghost(int x, int y, int numGhost, Grid grille, CyclicBarrier cyclicBarrier) {
        super(x,y, grille,cyclicBarrier);
        this.numGhost = numGhost;
        this.possibleStep=new ArrayList<GhostStep>();
    }


    public int getNumGhost() {
        return numGhost;
    }

    public void setNumGhost(int numGhost) {
        this.numGhost = numGhost;
    }

    public void start() {
        new Thread(this).start();
    }




    public void updatePossibleStep(int x,int y,Direction d){
        if( !((grille.getElement(x,y)) instanceof  Wall) && !( (this.direction==Direction.NORTH && d==Direction.SOUTH) || (this.direction==Direction.WEST && d==Direction.EAST)) ) {
            int heuristicDistance= Math.abs(grille.getPacman().getX()-x)+Math.abs(grille.getPacman().getY()-y);
            this.possibleStep.add(new GhostStep(x,y,heuristicDistance,d));
        }
    }

    public Direction parrallelAlgorithm(){
        int heuristicDistance= Math.abs(grille.getPacman().getX()-x)+Math.abs(grille.getPacman().getY()-y);

        if(heuristicDistance<8){
            if(grille.getPacman().getX() > this.x){
                if(grille.getPacman().getDirection()==Direction.WEST){
                    return Direction.EAST;
                }
            }
            if(grille.getPacman().getY() > this.y){
                if(grille.getPacman().getDirection()==Direction.NORTH) {
                    return Direction.SOUTH;
                }
            }

            return grille.getPacman().getDirection();
        }else{
            return manhattanAlgorithm();
        }
    }
    public Direction manhattanAlgorithm(){

        this.possibleStep.clear();
        if(x==0){
            x=18;
        }
        else if(x==18){
            x=0;
        }

        if(direction==Direction.NORTH){
            if (grille.isValideMove(x, y-1,this) && !(  ((grille.getElement(x+1,y)) instanceof  Ground) || ((grille.getElement(x-1,y)) instanceof  Ground)  ) ) {
                return this.direction;
            }
        }else if(direction==Direction.EAST){
            if (grille.isValideMove(x+1, y,this) && !(  ((grille.getElement(x,y+1)) instanceof  Ground) || ((grille.getElement(x,y-1)) instanceof  Ground)  ) ) {
                return this.direction;
            }
        }else if(direction==Direction.SOUTH){
            if (grille.isValideMove(x, y+1,this) && !(  ((grille.getElement(x+1,y)) instanceof  Ground) || ((grille.getElement(x-1,y)) instanceof  Ground)  ) ) {
                return this.direction;
            }
        }else if(direction==Direction.WEST){
            if (grille.isValideMove(x-1, y,this) && !(  ((grille.getElement(x,y+1)) instanceof  Ground) || ((grille.getElement(x,y-1)) instanceof  Ground)  ) )  {
                return this.direction;
            }
        }

        if(grille.isValideMove(this.x-1,this.y-1,this) ){
            //updatePossibleStep(this.x-1,this.y-1);
            if( (grille.getElement(x-1,y)) instanceof  Ground ){
                updatePossibleStep(x-1,y,Direction.WEST);
            }else if( (grille.getElement(x,y-1))instanceof  Ground ){
                updatePossibleStep(x,y-1,Direction.NORTH);
            }

        }
        if(grille.isValideMove(this.x,this.y-1,this)){
            updatePossibleStep(this.x,this.y-1,Direction.NORTH);
        }
        if(grille.isValideMove(this.x+1,this.y-1,this)){
            if( (grille.getElement(x,y-1)) instanceof  Ground ){
                updatePossibleStep(x,y-1,Direction.NORTH);
            }else if( (grille.getElement(x+1,y)) instanceof  Ground ){
                updatePossibleStep(x+1,y,Direction.EAST);
            }
        }

        if(grille.isValideMove(this.x+1,this.y,this)){
            updatePossibleStep(this.x+1,this.y,Direction.EAST);
        }
        if(grille.isValideMove(this.x+1,this.y+1,this)){
            if( (grille.getElement(x+1,y)) instanceof  Ground ){
                updatePossibleStep(x+1,y,Direction.EAST);
            }else if( (grille.getElement(x,y+1)) instanceof  Ground ){
                updatePossibleStep(x,y+1,Direction.SOUTH);
            }
        }

        if(grille.isValideMove(this.x,this.y+1,this)){
            updatePossibleStep(this.x,this.y+1,Direction.SOUTH);
        }
        if(grille.isValideMove(this.x-1,this.y+1,this)){
            if( (grille.getElement(x-1,y)) instanceof  Ground ){
                updatePossibleStep(x-1,y,Direction.SOUTH);
            }else if( (grille.getElement(x+1,y)) instanceof  Ground ){
                updatePossibleStep(x+1,y,Direction.WEST);
            }
        }
        if(grille.isValideMove(this.x-1,this.y,this)){
            updatePossibleStep(this.x-1,this.y,Direction.WEST);
        }



        double min=99;
        GhostStep bestStep;
        if(possibleStep.size()>0){
            bestStep=possibleStep.get(0);
        }else{
            return Direction.getRandomDirection();
        }

        for(int i=0; i< possibleStep.size();i++){
            if(possibleStep.get(i).getProbability()<=min && !(grille.getElement(possibleStep.get(i).getX(),possibleStep.get(i).getY()) instanceof Wall)){
                min=possibleStep.get(i).getProbability();
                bestStep=possibleStep.get(i);
            }
            //System.out.println("Possible step "+i+" : "+possibleStep.get(i).getProbability()+ " D: "+possibleStep.get(i).getDirection().toString());
        }
        //System.out.println("Best GhostStep =  X: "+bestStep.getX()+"  Y: "+bestStep.getY()+ "  - P: "+bestStep.getProbability());
        return bestStep.getDirection();
        //return direction=Direction.getRandomDirection();
    }
    @Override
    public void run() {
        Dijkstra dijkstra = new Dijkstra();
        while(true) { // spm descent dans la grille à chaque pas de temps


            //Le ghost 1 utilise une heuristique simple Manhattan
            if(moveable){
                if(numGhost==1){
                    // System.out.println("Dijkstra: "+dijkstra.getDistanceToTheClosestDot(x,y,grille));
                    direction = Direction.getRandomDirection();
                }else if(numGhost==2){
                    direction = parrallelAlgorithm();
                }
                else{
                    //direction = manhattanAlgorithm();
                    direction=Direction.getRandomDirection();
                }

                if(direction==Direction.NORTH){
                    if (grille.isValideMove(x, y-1)) {
                        y--;
                    }
                }else if(direction==Direction.EAST){
                    if (grille.isValideMove(x+1, y)) {
                        x++;
                    }
                }else if(direction==Direction.SOUTH){
                    if (grille.isValideMove(x, y+1)) {
                        y++;
                    }
                }else if(direction==Direction.WEST){
                    if (grille.isValideMove(x-1, y)) {
                        x--;
                    }
                }
            }
            if (isScared()) {
                reduceTimeScared(175);
            }

            setChanged();
            notifyObservers(); // notification de l'observer

            try {
                Thread.sleep(175); // pause
                this.getCyclicBarrier().await();
            } catch (InterruptedException ex) {
                Logger.getLogger(Ghost.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }

    }


}
