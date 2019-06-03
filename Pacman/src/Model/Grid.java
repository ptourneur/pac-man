package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Grid {
    private GridElement[][] grille;
    File file;
    private int columnCount;
    private int rowCount;


    private int internalTimer=0;

    private PacMan pacman;
    private ArrayList<Ghost> ghosts;

    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    public void setGhosts(ArrayList<Ghost> ghosts) {
        this.ghosts = ghosts;
    }

    public Grid() {
        this.file = new File("niveaux/level2.txt");
        this.initMapSize();
        this.grille = new GridElement[columnCount][rowCount];
        this.initMap();
        this.ghosts = new ArrayList<Ghost>();
    }

    public void setPacman(PacMan pacman){
        this.pacman=pacman;
    }
    public PacMan getPacman(){
        return this.pacman;
    }

    public void scareGhots(boolean scare){
        for (int i = 0; i < ghosts.size(); i++) {
            ghosts.get(i).setScared(scare);
        }


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
        this.internalTimer=internalTimer++;
    }


    public GridElement getElement(int col, int row) {
        return this.grille[col][row];
    }

    public boolean isValideMove(int newCol, int newRow) {
        if(newCol < 0 || newRow < 0) return false;
        if(newRow >= 21 || newCol >= 19) return false;

            return !(this.grille[newCol][newRow] instanceof Wall);
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

    public Position[] getNeighbors(int x, int y){
        Position[] pos = null;
        if((getElement(x,y) instanceof Wall)) return pos;
        Position[] aux = new Position[4];
        aux[0] = getNeighbor(x, y-1);
        aux[1] = getNeighbor(x, y+1);
        aux[2] = getNeighbor(x-1, y);
        aux[3] = getNeighbor(x+1, y);
        int count = 0;
        for (int i = 0; i < aux.length; i++) {
            if(aux[i]!=null) count++;
        }
        pos = new Position[count];
        for (int i = 0; i < aux.length; i++) {
            if(aux[i]!=null){
                pos[count-1] = aux[i];
                count--;
            }
        }
        return pos;
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
                        break;
                    case "B":
                        this.setElement(column, row, new Ground(new SuperPacGum()));
                        break;
                    case "E":
                        this.setElement(column, row, new Ground());
                        break;
                    case "G":
                        //GhostSpawn
                        this.setElement(column, row, new Ground(new GhostSpawn()));
                        break;
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
