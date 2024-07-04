import java.io.*;
import java.util.*;

/**
 * Class generates a game of life using text files.
 * To compile: javac -d bin Life.java
 * To run:     java -cp bin Life <input_file> <# of generations>
 * 
 * @author Sharon Gilman (smgilman)
 */
public class Life {
    /** Holds the number of rows the initial grid has. */
    private static int M;
    /** Holds the number of columns the initial grid has. */
    private static int N;
    /** The initial grid and each successive grid per generation. */
    private static int[][] initGrid;
    /** The  */
    private static int[][] nextGrid;

    /**
     * Begins program execution. 
     * @param args command line arguments to be used for game creation
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Life <input_file> <# of generations>");
            System.exit(1);
        }

        String fileName = args[0];
        int numGenerations = Integer.parseInt(args[1]);

        try {
            initializeGrid(fileName);
        } catch (Exception e) {
            System.out.println("Error reading input file: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Initial Grid:");
        displayGrid(initGrid);

        for (int generation = 0; generation < numGenerations; generation++) {
            nextGrid = new int[M][N];

            Thread[][] threads = new Thread[M][N];
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    threads[i][j] = new LifeThread(i, j);
                    threads[i][j].start();
                }
            }

            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    try {
                        threads[i][j].join();
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
            }

            initGrid = nextGrid;
            System.out.println("Generation " + (generation + 1) + ":");
            displayGrid(initGrid);
        }
    }

    /**
     * Inner class that executes on each thread.
     */
    private static class LifeThread extends Thread {
        /** Holds the index of a row. */
        private int i;
        /** Holds the index of a column. */
        private int j;

        /**
         * Constructs the variables needed to compute the value for this cell
         * in the next generation. 
         * @param i row variable
         * @param j column variable
         */
        public LifeThread(int i, int j) {
            this.i = i;
            this.j = j;
        }

        /**
         * Code to execute on each thread. 
         */
        public void run() {
            int count = countNeighbors(i, j);

            if (initGrid[i][j] == 1) {
                if (count < 2 || count > 3) {
                    nextGrid[i][j] = 0;
                } else {
                    nextGrid[i][j] = 1;
                }
            } else {
                if (count == 3) {
                    nextGrid[i][j] = 1;
                } else {
                    nextGrid[i][j] = 0;
                }
            }
        }
        
        /**
         * Computes the value for the cell using the parameters given.
         * @param i row variable
         * @param j column variable
         * @return the count of 1s surrounding the cell at the indicies
         *         provided 
         */
        private int countNeighbors(int i, int j) {
            int count = 0;

            // 3 above (row - 1), 2 same row, 3 below (row + 1)
            int[] row = {-1, -1, -1, 0, 0, 1, 1, 1};

            // 3 before (col - 1), 2 same column, 3 after (col + 1)
            int[] col = {-1, 0, 1, -1, 1, -1, 0, 1}; 

            for (int k = 0; k < 8; k++) {
                int neighborI = (i + row[k] + M) % M;
                int neighborJ = (j + col[k] + N) % N;
                count += initGrid[neighborI][neighborJ];
            }

            return count;
        }
    }

    /**
     * Initializes the first grid. Reads from the file and creates a matrix 
     * of size M x N.
     * @param fileName the name of the file to read from
     * @throws IOException if the file cannot be read
     */
    private static void initializeGrid(String fileName) throws IOException {
        Scanner fileReader = new Scanner(new File(fileName));
        String dimensions = fileReader.nextLine();
            
        String[] matrixSize = dimensions.split(" ");
        for (int i = 0; i < matrixSize.length; i++) {
            int check = 0;
            try {
                check = Integer.parseInt(matrixSize[i]);
            } catch (Exception e) {
                continue;
            }

            if (i == 0) 
                M = check;
            else
                N = check;
        }
        
        initGrid = new int[M][N];
        for (int i = 0; i < M; i++) {
            String line = fileReader.nextLine();
            String[] lineSplit = line.split(" ");

            for (int j = 0; j < N; j++) {
                initGrid[i][j] = Integer.parseInt(lineSplit[j]);
            }
        }

        fileReader.close();
    }

    /**
     * Prints the grid parameter one row per line.
     * @param grid the matrix to print out
     */
    private static void displayGrid(int[][] grid) {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
