using System;

namespace main {
    class main {
        static void Main(string[] args)
        {
            Minefield minefield;
            List<Spot> path = new List<Spot>();

            System.Console.WriteLine("Enter number of columns: ");
            int cols = Convert.ToInt32(Console.ReadLine());
            System.Console.WriteLine("Enter the number of rows: ");
            int rows = Convert.ToInt32(Console.ReadLine());

            minefield = new Minefield(rows, cols);

            pathGenerator(ref minefield);
            bombGenerator(ref minefield);
            PathFinder(ref minefield,ref path);
            Ally(ref minefield, ref path);
        }

        static bool Totoshka(Spot startSpot, Spot endSpot, ref List<Spot> path, ref Minefield minefield) {
            // openSet is for the blocks waiting to be evaluated
            // closeSet is for visited blocks
            List<Spot> openSet = new List<Spot>(), closeSet = new List<Spot>();

            openSet.Add(startSpot);

            while(true) {
                // Loop until the openSet is empty
                if(openSet.Count > 0) {
                    int winner = 0;

                    // Scan through the openSet to see which path has the least f
                    for (int i = 0; i < openSet.Count; i++) {
                        if(openSet[i].f < openSet[winner].f) {
                            winner = i;
                        }
                    }

                    // Move the position to the one with least f
                    Spot current = openSet[winner];

                    // If the current position is equal to end position, it has been solved
                    if(current.i == 0) {
                        // Array of path to help visualize it
                        path = new List<Spot>();
                        Spot temp = current;
                        // Add the current position to the path, this path travels backwards back to the starting position
                        // Set the path to true for display purpose
                        //minefield.grid[temp.i][temp.j].path = true;

                        // Reset the path
                        for (int i = 0; i < minefield.rows; i ++) {
                            for (int j = 0; j < minefield.cols; j ++) {
                                if(minefield.grid[i, j].previousSpot != null) {
                                    minefield.grid[i, j].previousSpot = null;
                                }
                            }
                        }

                        // Keep looping until there is no previous block
                        while (temp.previousSpot != null) {
                            //minefield.grid[temp.previousSpot.i][temp.previousSpot.j].path = true;
                            path.Add(temp);
                            temp = temp.previousSpot;
                        }

                        path.Add(startSpot);

                        System.Console.WriteLine();
                        return true;
                    }

                    // Remove the current position from openSet since it has been evaluated
                    openSet.Remove(current);
                    // Add the current position to closeSet, so we won't accidentally evaluate it again in the future
                    closeSet.Add(current);

                    List<Spot> neighbours = minefield.grid[current.i, current.j].neighbors;

                    // Loop through the current block's neighbors
                    for (int i = 0; i < neighbours.Count; i++) {
                        // If the closeSet does not contain neighbors, proceed. Otherwise, do not do anything.
                        Spot neighbor = neighbours[i];

                        if(!closeSet.Contains(neighbor) && !minefield.grid[neighbor.i, neighbor.j].bomb) {
                            // g is the distance traveled, the distance from 1 block to another is 1, so we add 1
                            int tempG = current.g + 1;

                            // Check if openSet contains current block's neighbors
                            if(openSet.Contains(neighbor)) {
                                // If the neighbor is already inside the openSet, check if the current is better than the old one
                                if(tempG < neighbor.g) {
                                    // Replace if the current is better
                                    neighbor.g = tempG;
                                }
                            }
                            // If the neighbor is not in the openSet, add it
                            else {
                                neighbor.g = tempG;
                                openSet.Add(neighbor);
                            }

                            int[] start = {neighbor.i, neighbor.j};
                            int[] finish = {endSpot.i, endSpot.j};

                            // Update the f value for the current spot, will be used for future winner calculations
                            minefield.grid[current.i, current.j].neighbors[i].h = heuristic(start, finish);
                            minefield.grid[current.i, current.j].neighbors[i].f = minefield.grid[current.i, current.j].neighbors[i].h + minefield.grid[current.i, current.j].neighbors[i].g;

                            // Set all the neighbors of current spot to itself
                            minefield.grid[current.i, current.j].neighbors[i].previousSpot = current;
                        }
                    }
                }
                else {
                    // All the possible spot has been evaluated, puzzle not solvable
                    return false;
                }
            }
        }

        static void Ally(ref Minefield minefield, ref List<Spot> path) {
            int count = minefield.rows - 1;
            bool quit = false;
            Spot? totoshkaCurrent, totoshkaExSpot = null;

            while (!quit) {
                // First time, Ally should wait outside
                if(count == minefield.rows - 1) {
                    totoshkaCurrent = new Spot(path[count].i, path[count].j);
                    totoshkaExSpot = new Spot(path[count].i, path[count].j);

                    // Let Totoshka enters the minefield first
                    minefield.grid[totoshkaCurrent.i, totoshkaCurrent.j].totoshka = true;

                    minefield.print();
                    System.Console.WriteLine("(^_^)");

                    minefield.grid[totoshkaCurrent.i, totoshkaCurrent.j].totoshka = false;
                }
                // Last step, Totoshka should wait outside
                else if (count == -1) {
                    if(totoshkaExSpot != null) {
                        minefield.grid[totoshkaExSpot.i, totoshkaExSpot.j].ally = true;
                    }
                    
                    System.Console.WriteLine("\nヽ(°ᴥ°)ﾉ");
                    minefield.print();

                    if(totoshkaExSpot != null) {
                        minefield.grid[totoshkaExSpot.i, totoshkaExSpot.j].ally = false;
                    }

                    System.Console.WriteLine();

                    System.Console.WriteLine("ヽ(°ᴥ°)ﾉ     (^_^)");
                    minefield.print();

                    quit = true;
                }
                // Not the first time, Ally should go inside
                else {
                    totoshkaCurrent = new Spot(path[count].i, path[count].j);

                    // Set the correct position for Totoshka and Ally
                    if(totoshkaExSpot != null) {
                        minefield.grid[totoshkaExSpot.i, totoshkaExSpot.j].ally = true;
                    }
                    minefield.grid[totoshkaCurrent.i, totoshkaCurrent.j].totoshka = true;

                    System.Console.WriteLine();
                    minefield.print();

                    if(totoshkaExSpot != null) {
                        minefield.grid[totoshkaExSpot.i, totoshkaExSpot.j].ally = false;
                    }
                    totoshkaExSpot = totoshkaCurrent;
                    minefield.grid[totoshkaCurrent.i, totoshkaCurrent.j].totoshka = false;
                }
                count--;
            }
        }

        static double heuristic(int[] start, int[] end) {
            return Math.Abs(start[0] - end[0]) + Math.Abs(start[1] - end[1]);
        }

        static void PathFinder(ref Minefield minefield, ref List<Spot> path) {
            int endPosition = 0;

            // Loop through all the j
            while(endPosition < minefield.cols) {
                // Check if the current end position has a route or not
                bool solutions = PathFinding(ref minefield, endPosition, ref path);

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
            System.Console.WriteLine("No possible solutions");
        }

        static bool PathFinding(ref Minefield minefield, int endPosition, ref List<Spot> path) {
            // Start from the left of the starting position
            int startPosition = 0;

            // Loop though all the j
            while(startPosition < minefield.cols) {
                // Check if the starting position is bomb or not, skip if it is true
                if(!minefield.grid[minefield.rows - 1, startPosition].bomb){
                    // If the Totoshka returns true, it means the puzzle has been solved
                    if (Totoshka(minefield.grid[minefield.rows - 1, startPosition], new Spot(0, endPosition), ref path, ref minefield)){
                        return true;
                    }
                }
                startPosition ++;
            }
            // The current end position is unsolvable
            return false;
        }

        static void bombGenerator(ref Minefield minefield) {
            for (int i = 0; i < minefield.rows; i++) {
                for (int j = 0; j < minefield.cols; j++) {
                    // If it is a path, skip this block
                    if(minefield.grid[i, j].path) {
                        // Set the path to false so no cheating
                        minefield.grid[i, j].path = false;
                    }
                    // All other non-path block has 1 in 2 chance of becoming a bomb
                    else {
                        int bombChance = new Random().Next(1, 3);

                        if(bombChance == 1) {
                            minefield.grid[i, j].bomb = true;
                        }
                    }
                }
            }
        }

        static void pathGenerator(ref Minefield  minefield) {
            int[] currentBlock = {minefield.rows - 1 , new Random().Next(1, minefield.cols)};

            while (currentBlock[0] >= 0) {
            // Set the current block to path
            minefield.grid[currentBlock[0], currentBlock[1]].path = true;

            // Use random number generator to generate between 3 numbers (1 in 3 chance)
            int directionChance = new Random().Next(1, 3);

            switch (directionChance) {
                // If the number is 0, turn left
                case 0 : 
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
                    break;

                // Go straight if the number is 1
                case 1:
                    currentBlock[0] -= 1;
                    break;

                // If the number is 2, turn right
                case 2: 
                    if(currentBlock[1] < minefield.cols - 1) {
                        currentBlock[0] -= 1;
                        currentBlock[1] += 1;
                    }
                    // If the currentBlock is the last on the row, turn left
                    else {
                        currentBlock[0] -= 1;
                        currentBlock[1] -= 1;
                    }
                    break;
                
            }
        }
        }
    }

    public class Minefield {
        public int cols;
        public int rows;
        public Spot[,] grid;

        public Minefield(int rows, int cols) {
            this.cols = cols;
            this.rows = rows;
            this.grid = new Spot[rows, cols];

            // Making a 2D array to represent the minefield
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    grid[i, j] = new Spot(i, j);
                }
            }

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    grid[i, j].addNeighbours(cols, rows);
                }
            }
        }

        public void print() {
            for (int i = 0; i < rows; i++) {
                string? output = null;
                for (int j = 0; j < cols; j++) {
                    if(this.grid[i, j].totoshka) {
                        output += "T ";
                    }
                    else if(this.grid[i, j].ally) {
                        output += "A ";
                    }
                    else if(this.grid[i, j].bomb) {
                        output += "X ";
                    }
                    else if(this.grid[i, j].path) {
                        output += "1 ";
                    }
                    else {
                        output += "0 ";
                    }
                }
                System.Console.WriteLine(output);
            }
        }
    }

    public class Spot {
        public int i;
        public int j;
        public double f;
        public int g = 0;
        public double h;
        public List<Spot> neighbors = new List<Spot>();
        public Spot? previousSpot = null;
        public bool bomb;
        public bool path;
        public bool totoshka;
        public bool ally;

        public Spot(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public void addNeighbours(int rows, int cols) {
            int i = this.i;
            int j = this.j;

            // Move down
            if(i < cols - 1) {
                this.neighbors.Add(new Spot(i + 1, j));
            }

            // Move up
            if(i > 0) {
                this.neighbors.Add(new Spot(i - 1, j));
            }

            // Move right
            if(j < rows - 1) {
                this.neighbors.Add(new Spot(i, j + 1));
            }

            // Move left
            if(j > 0) {
                this.neighbors.Add(new Spot(i, j - 1));
            }

            // Move to top left
            if(i > 0 && j > 0) {
                this.neighbors.Add(new Spot(i - 1, j - 1));
            }

            // Move to top right
            if(i < cols - 1 && j > 0) {
                this.neighbors.Add(new Spot(i + 1, j - 1));
            }

            // Move to bottom left
            if(i > 0 && j < rows - 1) {
                this.neighbors.Add(new Spot(i - 1, j + 1));
            }

            // Move to bottom right
            if(i < cols - 1 && j < rows - 1) {
                this.neighbors.Add(new Spot(i + 1, j + 1));
            }
        }
    }
}
