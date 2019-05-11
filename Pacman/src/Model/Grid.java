package Model;

public class Grid {
    private GridElement[][] grille;

    public Grid(int columnCount, int rowCount) {
        this.grille = new GridElement[columnCount][rowCount];
    }

    public void setElement(int col, int row, GridElement element) {
        this.grille[col][row] = element;
    }

    public GridElement getElement(int col, int row) {
        return this.grille[col][row];
    }

    public boolean isValideMove(int newCol, int newRow) {
            return !(this.grille[newCol][newRow] instanceof Wall);

    }
}
