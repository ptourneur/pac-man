package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

public class Grid {
    private GridElement[][] grille;
    File file;
    private int columnCount;
    private int rowCount;
    private boolean gameOver;
    private CyclicBarrier cyclicBarrier;
    private int internalTimer=0;
    private int nbPacgum;
    private PacMan pacman;
    private ArrayList<Ghost> ghosts;

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    public Grid() {
        this.file = new File("niveaux/level2.txt");
        this.initMapSize();
        this.grille = new GridElement[columnCount][rowCount];
        this.ghosts = new ArrayList<>();
        this.nbPacgum=0;
        this.cyclicBarrier = new CyclicBarrier(5 );
        this.initMap();
    }

    public void setPacman(PacMan pacman){
        this.pacman=pacman;
    }
    public PacMan getPacman(){
        return this.pacman;
    }

    public void scareGhots(boolean scare){
            ghosts.forEach(ghost -> ghost.setScared(scare));
    }
    public void setElement(int col, int row, GridElement element) {
        this.grille[col][row] = element;
    }

    public int getInternalTimer() {
        return internalTimer;
    }

    public void setInternalTimer(int internalTimer) {
        this.internalTimer = internalTimer;
    }

    public void incrementInternalTimer() {
        this.internalTimer++;
        if(internalTimer>=12){
            ghosts.get(3).setMoveable(true);
        }else if(internalTimer>=10){
            ghosts.get(1).setMoveable(true);
        }else if(internalTimer>=5){
            ghosts.get(2).setMoveable(true);
        }if(internalTimer>=3){
            ghosts.get(0).setMoveable(true);
        }
    }

    public GridElement getElement(int col, int row) {
        return this.grille[col][row];
    }


    public boolean isValideMove(int newCol, int newRow, Entity e) {

        if(newCol < 0 || newRow < 0) return false;
        if(newRow >= 21 || newCol >= 19) return false;

        if((this.grille[newCol][newRow] instanceof Ground)){
            if ( ((Ground) grille[newCol][newRow]).getItem() instanceof  GhostSpawn ){
                int entityX=e.getX();
                int entityY=e.getY();
                if( this.grille[entityX][entityY] instanceof Ground && ((Ground) grille[entityX][entityY]).getItem() instanceof GhostSpawn){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return !(this.grille[newCol][newRow] instanceof Wall);
    }


    public Position getOneGhostSpawn(){
        for(int i=0;i<getRowCount();i++) {
            for(int j=0;j<getColumnCount();j++) {
                if((getElement(i,j) instanceof Ground)){
                    if ( ((Ground) getElement(i,j)).getItem() instanceof  GhostSpawn ){
                        if( getElement(i,j) instanceof Ground && ((Ground) getElement(i,j)).getItem() instanceof GhostSpawn){
                            return new Position(i,j);
                        }
                    }
                }
            }
        }
        return new Position(10,9);
    }

    public synchronized boolean checkGameOver(){
        for(int i=0;i<ghosts.size();i++){
            if(ghosts.get(i).getX()==getPacman().getX() && ghosts.get(i).getY()==getPacman().getY()){
                if((ghosts.get(i).isScared()) ){
                    Position p = getOneGhostSpawn();
                    ghosts.get(i).setX(p.getX());
                    ghosts.get(i).setY(p.getY());
                    ghosts.get(i).setScared(false);
                    pacman.setNbEatenGhost(pacman.getNbEatenGhost()+1);
                }else{
                    getPacman().reduceOneLife();
                    if(getPacman().getLives()>0){
                        getPacman().setX(getPacman().getSpawnX());
                        getPacman().setY(getPacman().getSpawnY());
                        return false;
                    }else{
                        setGameOver(true);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Position getNeighbor(int x, int y){
        if(!(getElement(x,y) instanceof Wall))
            return new Position(x, y);
        return null;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    private void initMapSize() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        rowCount = 0;
        columnCount = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            while (lineScanner.hasNext()) {
                lineScanner.next();
                columnCount++;
            }
            rowCount++;
        }
        columnCount = columnCount/rowCount;
        scanner.close();
    }

    private void initMap() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int row = 0;
        int numGhost = 1;
        while(scanner.hasNextLine()) {
            int column = 0;
            String line = scanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            while (lineScanner.hasNext()) {
                String value = lineScanner.next();
                switch (value) {
                    case "W":
                        this.setElement(column, row, new Wall());
                        break;
                    case "S":
                        this.setElement(column, row, new Ground(new PacGum()));
                        nbPacgum++;
                        break;
                    case "B":
                        this.setElement(column, row, new Ground(new SuperPacGum()));
                        break;
                    case "A":
                        this.setElement(column, row, new Ground(new Apple()));
                        break;
                    case "E":
                        this.setElement(column, row, new Ground());
                        break;
                    case "G":
                        //GhostSpawn
                        this.setElement(column, row, new Ground(new GhostSpawn()));
                        ghosts.add(new Ghost(column, row, numGhost, this, cyclicBarrier));
                        numGhost++;
                        break;
                    case "P":
                        this.setElement(column, row, new Ground());
                        pacman = new PacMan(column, row, this, cyclicBarrier);
                        pacman.setSpawnX(column);
                        pacman.setSpawnY(row);
                    default:
                        this.setElement(column, row, new Wall());
                }
                column++;
            }
            row++;
        }
        scanner.close();
    }
}
