package Q3;

public class Minefield {
    int cols;
    int rows;
    Spot[][] grid;

    public Minefield(int rows, int cols) {
        this.cols = cols;
        this.rows = rows;
        this.grid = new Spot[rows][cols];

        // Making a 2D array to represent the minefield
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Spot(i, j);
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j].addNeighbours(cols, rows);
            }
        }
    }

    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(this.grid[i][j].totoshka) {
                    System.out.print("T ");
                }
                else if(this.grid[i][j].ally) {
                    System.out.print("A ");
                }
                else if(this.grid[i][j].bomb) {
                    System.out.print("X ");
                }
                else if(this.grid[i][j].path) {
                    System.out.print("1 ");
                }
                else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }
}
