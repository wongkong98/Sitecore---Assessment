package Q3;

import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.Math.abs;

public class Q3 {
    static Minefield minefield;
    static ArrayList<Spot> path;

    public static void question3() {

        Scanner intScanner = new Scanner(System.in);
        System.out.print("Enter number of columns: ");
        int cols = intScanner.nextInt();
        System.out.print("Enter the number of rows: ");
        int rows = intScanner.nextInt();

        minefield = new Minefield(rows, cols);

        pathGenerator();
        bombGenerator();
        PathFinder();
        Ally(path);
    }

    private static void Ally(ArrayList<Spot> path) {
        int count = minefield.rows - 1;
        boolean quit = false;
        Spot totoshkaCurrent, totoshkaExSpot = null;

        while (!quit) {
            // First time, Ally should wait outside
            if(count == minefield.rows - 1) {
                totoshkaCurrent = new Spot(path.get(count).i, path.get(count).j);
                totoshkaExSpot = new Spot(path.get(count).i, path.get(count).j);

                // Let Totoshka enters the minefield first
                minefield.grid[totoshkaCurrent.i][totoshkaCurrent.j].totoshka = true;

                minefield.print();
                System.out.println("(^_^)");

                minefield.grid[totoshkaCurrent.i][totoshkaCurrent.j].totoshka = false;
            }
            // Last step, Totoshka should wait outside
            else if (count == -1) {
                assert totoshkaExSpot != null;
                minefield.grid[totoshkaExSpot.i][totoshkaExSpot.j].ally = true;

                System.out.println("\nヽ(°ᴥ°)ﾉ");
                minefield.print();

                minefield.grid[totoshkaExSpot.i][totoshkaExSpot.j].ally = false;

                System.out.println();

                System.out.println("ヽ(°ᴥ°)ﾉ     (^_^)");
                minefield.print();

                quit = true;
            }
            // Not the first time, Ally should go inside
            else {
                totoshkaCurrent = new Spot(path.get(count).i, path.get(count).j);

                assert totoshkaExSpot != null;

                // Set the correct position for Totoshka and Ally
                minefield.grid[totoshkaExSpot.i][totoshkaExSpot.j].ally = true;
                minefield.grid[totoshkaCurrent.i][totoshkaCurrent.j].totoshka = true;

                System.out.println();
                minefield.print();

                minefield.grid[totoshkaExSpot.i][totoshkaExSpot.j].ally = false;
                totoshkaExSpot = totoshkaCurrent;
                minefield.grid[totoshkaCurrent.i][totoshkaCurrent.j].totoshka = false;
            }
            count--;
        }
    }

    // Used to find the path to the other side
    private static void PathFinder() {
        // The j of end position, increment by one if the current is not the solution
        int endPosition = 0;

        // Loop through all the j
        while(endPosition < minefield.cols) {
            // Check if the current end position has a route or not
            boolean solutions = PathFinding(endPosition);

            // If yes, the puzzle has been solved
            if(solutions) {
                return;
            }
            // Check the next j
            else {
                endPosition++;
            }
        }
        // If all the j is checked and there is no route, the puzzle is unsolvable
        System.out.println("No possible solutions");
    }

    private static boolean PathFinding(int endPosition) {
        // Start from the left of the starting position
        int startPosition = 0;

        // Loop though all the j
        while(startPosition < minefield.cols) {
            // Check if the starting position is bomb or not, skip if it is true
            if(!minefield.grid[minefield.rows - 1][startPosition].bomb){
                // If the Totoshka returns true, it means the puzzle has been solved
                if (Totoshka(minefield.grid[minefield.rows - 1][startPosition], new Spot(0, endPosition))){
                    return true;
                }
            }
            startPosition ++;
        }
        // The current end position is unsolvable
        return false;
    }

    private static boolean Totoshka(Spot startSpot, Spot endSpot) {
        // openSet is for the blocks waiting to be evaluated
        // closeSet is for visited blocks
        ArrayList<Spot> openSet = new ArrayList<>(), closeSet = new ArrayList<>();

        openSet.add(startSpot);

        while(true) {
            // Loop until the openSet is empty
            if(openSet.size() > 0) {
                int winner = 0;

                // Scan through the openSet to see which path has the least f
                for (int i = 0; i < openSet.size(); i++) {
                    if(openSet.get(i).f < openSet.get(winner).f) {
                        winner = i;
                    }
                }

                // Move the position to the one with least f
                Spot current = openSet.get(winner);

                // If the current position is equal to end position, it has been solved
                if(current.i == 0) {
                    // Array of path to help visualize it
                    path = new ArrayList<>();
                    Spot temp = current;
                    // Add the current position to the path, this path travels backwards back to the starting position
                    // Set the path to true for display purpose
                    //minefield.grid[temp.i][temp.j].path = true;

                    // Reset the path
                    for (int i = 0; i < minefield.rows; i ++) {
                        for (int j = 0; j < minefield.cols; j ++) {
                            if(minefield.grid[i][j].previousSpot != null) {
                                minefield.grid[i][j].previousSpot = null;
                            }
                        }
                    }

                    // Keep looping until there is no previous block
                    while (temp.previousSpot != null) {
                        //minefield.grid[temp.previousSpot.i][temp.previousSpot.j].path = true;
                        path.add(temp);
                        temp = temp.previousSpot;
                    }

                    path.add(startSpot);

                    System.out.println();
                    return true;
                }

                // Remove the current position from openSet since it has been evaluated
                openSet.remove(current);
                // Add the current position to closeSet, so we won't accidentally evaluate it again in the future
                closeSet.add(current);

                ArrayList<Spot> neighbours = minefield.grid[current.i][current.j].neighbors;

                // Loop through the current block's neighbors
                for (int i = 0; i < neighbours.size(); i++) {
                    // If the closeSet does not contain neighbors, proceed. Otherwise, do not do anything.
                    Spot neighbor = neighbours.get(i);

                    if(!closeSet.contains(neighbor) && !minefield.grid[neighbor.i][neighbor.j].bomb) {
                        // g is the distance traveled, the distance from 1 block to another is 1, so we add 1
                        int tempG = current.g + 1;

                        // Check if openSet contains current block's neighbors
                        if(openSet.contains(neighbor)) {
                            // If the neighbor is already inside the openSet, check if the current is better than the old one
                            if(tempG < neighbor.g) {
                                // Replace if the current is better
                                neighbor.g = tempG;
                            }
                        }
                        // If the neighbor is not in the openSet, add it
                        else {
                            neighbor.g = tempG;
                            openSet.add(neighbor);
                        }

                        int[] start = {neighbor.i, neighbor.j};
                        int[] finish = {endSpot.i, endSpot.j};

                        // Update the f value for the current spot, will be used for future winner calculations
                        minefield.grid[current.i][current.j].neighbors.get(i).h = heuristic(start, finish);
                        minefield.grid[current.i][current.j].neighbors.get(i).f = minefield.grid[current.i][current.j].neighbors.get(i).h + minefield.grid[current.i][current.j].neighbors.get(i).g;

                        // Set all the neighbors of current spot to itself
                        minefield.grid[current.i][current.j].neighbors.get(i).previousSpot = current;
                    }
                }
            }
            else {
                // All the possible spot has been evaluated, puzzle not solvable
                return false;
            }
        }
    }

    // Used to calculate the length between 2 points
    private static double heuristic(int[] start, int[] end) {
        return abs(start[0] - end[0]) + abs(start[1] - end[1]);
    }

    // Used to generate a safe path so the minefield is always solvable
    private static void pathGenerator() {
        // The first block is located at the bottom, with a random column
        int[] currentBlock = {minefield.rows - 1 , (int) (Math.random() * ((minefield.cols - 1) + 1) + 0)};
        // Each loop will increase row by 1, the loop is done when the row reaches to the top
        while (currentBlock[0] >= 0) {
            // Set the current block to path
            minefield.grid[currentBlock[0]][currentBlock[1]].path = true;

            // Use random number generator to generate between 3 numbers (1 in 3 chance)
            int directionChance = (int) (Math.random() * (2 + 1) + 0);

            switch (directionChance) {
                // If the number is 0, turn left
                case 0 -> {
                    // Check if the current block is first on the row
                    if(currentBlock[1] > 0) {
                        currentBlock[0] -= 1;
                        currentBlock[1] -= 1;
                    }
                    // If it is first on the row, turn right
                    else {
                        currentBlock[0] -= 1;
                        currentBlock[1] += 1;
                    }
                }

                // Go straight if the number is 1
                case 1 -> currentBlock[0] -= 1;
                // If the number is 2, turn right
                case 2 -> {
                    if(currentBlock[1] < minefield.cols - 1) {
                        currentBlock[0] -= 1;
                        currentBlock[1] += 1;
                    }
                    // If the currentBlock is the last on the row, turn left
                    else {
                        currentBlock[0] -= 1;
                        currentBlock[1] -= 1;
                    }
                }
            }
        }
    }

    // Used to generate bomb, each block has 50% chance of having a bomb
    private static void bombGenerator() {
        // For loop to get each block on the grid
        for (int i = 0; i < minefield.rows; i++) {
            for (int j = 0; j < minefield.cols; j++) {
                // If it is a path, skip this block
                if(minefield.grid[i][j].path) {
                    // Set the path to false so no cheating
                    minefield.grid[i][j].path = false;
                }
                // All other non-path block has 1 in 2 chance of becoming a bomb
                else {
                    int bombChance = (int) (Math.random() * (2 - 1 + 1) + 1);

                    if(bombChance == 1) {
                        minefield.grid[i][j].bomb = true;
                    }
                }
            }
        }
    }
}
