# Life

### A question from a recent homework assignment for my OS class

###### Question 4: The Game of Life in a Threaded World

Write a multithreaded Java program that simulates the "Game of Life." The simulation occurs on an M-by-N rectangular array of cells. Each cell has only two values: 0 and 1. Each cell has eight neighbors: up, down, left, right, and four diagonally. Cells on the four edges of the grid still have 8 neighbors… they just wrap around. 

If the grid is declared as: **int[][] grid = new int[M][N];** then the neighbors of an interior cell grid[i][j] are grid[i-1][j], grid[i+1][j], grid[i][j-1], grid[i][j+1], grid[i-1][j-1], grid[i-1][j+1], grid[i+1][j-1], and grid[i+1][j+1].

The game works like this. Fill the grid with initial values. Then for each cell in the grid, compute the value for this cell in the next generation (stored in a separate grid). All changes occur simultaneously, called the generation.

---

- A 1 cell value stays 1 if exactly two or three neighbors are 1 valued.
- A 1 cell value becomes 0 if less than two or greater than three neighbors are 1 valued.
- A 0 cell value becomes 1 if exactly three neighbors are 1 valued.
- A 0 cell value stays 0 if less than three or greater than three neighbors are 1 valued.

---

Design a separate thread to calculate the new value for a specific cell at position (i,j).

> In pseudo-code:
> - Create an M x N grid for the starting state and populate the array.
> - Create an M x N grid for the next generation state.
> - Display starting state grid.
> - Loop for specific number of generations:
> - Create M x N threads, providing each thread with its (i,j) position in the starting state 
    grid. [For java programmers, you must make the threads schedulable by invoking start.]
> - Join all threads. <-must be a separate loop from creation loop
> - Display the next generation grid.
> - Make the starting state grid = next generation grid.

Do *not* create and join each thread inside each loop. This results in no parallelism.

The input data for your program will consist of a file with the values of M and N followed by the values for the starting state of each cell in the rectangular grid.

The first line of the file will be two integer values, the number of rows followed by the number of columns. 

For example,  
5 4

The following lines contain sequences of 0's and 1's for each row. For example,  
0 0 1 0  
1 1 1 1  
1 0 0 1  
0 0 0 1  
1 1 1 1

You may assume the files are formatted correctly. 
