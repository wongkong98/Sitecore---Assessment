package Q3;
import java.util.ArrayList;

public class Spot {
    int i;
    int j;
    double f;
    int g = 0;
    double h;
    ArrayList<Spot> neighbors = new ArrayList<>();
    Spot previousSpot = null;
    boolean bomb;
    boolean path;
    boolean totoshka;
    boolean ally;

    public Spot(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public void addNeighbours(int rows, int cols) {
        int i = this.i;
        int j = this.j;

        // Move down
        if(i < cols - 1) {
            this.neighbors.add(new Spot(i + 1, j));
        }

        // Move up
        if(i > 0) {
            this.neighbors.add(new Spot(i - 1, j));
        }

        // Move right
        if(j < rows - 1) {
            this.neighbors.add(new Spot(i, j + 1));
        }

        // Move left
        if(j > 0) {
            this.neighbors.add(new Spot(i, j - 1));
        }

        // Move to top left
        if(i > 0 && j > 0) {
            this.neighbors.add(new Spot(i - 1, j - 1));
        }

        // Move to top right
        if(i < cols - 1 && j > 0) {
            this.neighbors.add(new Spot(i + 1, j - 1));
        }

        // Move to bottom left
        if(i > 0 && j < rows - 1) {
            this.neighbors.add(new Spot(i - 1, j + 1));
        }

        // Move to bottom right
        if(i < cols - 1 && j < rows - 1) {
            this.neighbors.add(new Spot(i + 1, j + 1));
        }
    }
}
