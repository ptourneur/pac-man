package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Grid {
    private GridElement[][] grille;
    File file;
    private int columnCount;
    private int rowCount;

    public Grid() {
        this.file = new File("niveaux/level1.txt");
        this.initMapSize();
        this.grille = new GridElement[columnCount][rowCount];
        this.initMap();
    }

    public void setElement(int col, int row, GridElement element) {
        this.grille[col][row] = element;
    }

    public GridElement getElement(int col, int row) {
        return this.grille[col][row];
    }

    public boolean isValideMove(int newCol, int newRow) {
        if (newCol >= 0 && newCol < columnCount && newRow >= 0 && newRow < rowCount) {
            return !(this.grille[newCol][newRow] instanceof Wall);
        } else {
            return false;
        }
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
