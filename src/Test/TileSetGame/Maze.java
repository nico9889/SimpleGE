package Test.TileSetGame;

import java.util.ArrayList;
import java.util.Random;

/*
* Thx StackOverflow
*/
public class Maze {
    private final int dimensionX, dimensionY; // dimension of maze
    public final int gridDimensionX, gridDimensionY; // dimension of output grid
    private Cell[][] cells; // 2d array of Cells
    private final Random random = new Random(); // The random object
    private final int[][] maze;

    // constructor
    public Maze(int xDimension, int yDimension) {
        dimensionX = xDimension;
        dimensionY = yDimension;
        gridDimensionX = xDimension * 2 + 1;
        gridDimensionY = yDimension * 2 + 1;
        init();
        generateMaze();
        maze = new int[gridDimensionX][gridDimensionY];
        updateGrid();
    }

    private void init() {
        // create cells
        cells = new Cell[dimensionX][dimensionY];
        for (int x = 0; x < dimensionX; x++) {
            for (int y = 0; y < dimensionY; y++) {
                cells[x][y] = new Cell(x, y, false); // create cell (see Cell constructor)
            }
        }
    }

    // inner class to represent a cell
    private static class Cell{
        int x, y; // coordinates
        // cells this cell is connected to
        ArrayList<Cell> neighbors = new ArrayList<>();
        // impassable cell
        boolean wall;
        // if true, has yet to be used in generation
        boolean open = true;
        // construct Cell at x, y
        Cell(int x, int y) {
            this(x, y, true);
        }
        // construct Cell at x, y and with whether it isWall
        Cell(int x, int y, boolean isWall) {
            this.x = x;
            this.y = y;
            this.wall = isWall;
        }
        // add a neighbor to this cell, and this cell as a neighbor to the other
        void addNeighbor(Cell other) {
            if (!this.neighbors.contains(other)) { // avoid duplicates
                this.neighbors.add(other);
            }
            if (!other.neighbors.contains(this)) { // avoid duplicates
                other.neighbors.add(this);
            }
        }
        // used in updateGrid()
        boolean isCellBelowNeighbor() {
            return this.neighbors.contains(new Cell(this.x, this.y + 1));
        }
        // used in updateGrid()
        boolean isCellRightNeighbor() {
            return this.neighbors.contains(new Cell(this.x + 1, this.y));
        }

        // useful Cell representation
        @Override
        public String toString() {
            return String.format("Cell(%s, %s)", x, y);
        }
        // useful Cell equivalence
        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Cell)) return false;
            Cell otherCell = (Cell) other;
            return (this.x == otherCell.x && this.y == otherCell.y);
        }
    }

    // generate the maze from coordinates x, y
    private void generateMaze() {
        generateMaze(getCell(0,0)); // generate from Cell
    }

    private void generateMaze(Cell startAt) {
        // don't generate from cell not there
        if (startAt == null) return;
        startAt.open = false; // indicate cell closed for generation
        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(startAt);

        while (!cells.isEmpty()) {
            Cell cell;
            // this is to reduce but not completely eliminate the number
            //   of long twisting halls with short easy to detect branches
            //   which results in easy mazes
            if (random.nextInt(10)==0)
                cell = cells.remove(random.nextInt(cells.size()));
            else cell = cells.remove(cells.size() - 1);
            // for collection
            ArrayList<Cell> neighbors = new ArrayList<>();
            // cells that could potentially be neighbors
            Cell[] potentialNeighbors = new Cell[]{
                    getCell(cell.x + 1, cell.y),
                    getCell(cell.x, cell.y + 1),
                    getCell(cell.x - 1, cell.y),
                    getCell(cell.x, cell.y - 1)
            };
            for (Cell other : potentialNeighbors) {
                // skip if outside, is a wall or is not opened
                if (other==null || other.wall || !other.open) continue;
                neighbors.add(other);
            }
            if (neighbors.isEmpty()) continue;
            // get random cell
            Cell selected = neighbors.get(random.nextInt(neighbors.size()));
            // add as neighbor
            selected.open = false; // indicate cell closed for generation
            cell.addNeighbor(selected);
            cells.add(cell);
            cells.add(selected);
        }

    }
    // used to get a Cell at x, y; returns null out of bounds
    public Cell getCell(int x, int y) {
        try {
            return cells[x][y];
        } catch (ArrayIndexOutOfBoundsException e) { // catch out of bounds
            return null;
        }
    }

    // draw the maze
    public void updateGrid() {
        for (int x = 0; x < gridDimensionX; x ++) {
            for (int y = 0; y < gridDimensionY; y ++) {
                maze[x][y] = 1;
            }
        }

        // make meaningful representation
        for (int x = 0; x < dimensionX; x++) {
            for (int y = 0; y < dimensionY; y++) {
                Cell current = getCell(x, y);
                int gridX = x * 2+1, gridY = y * 2+1;
                    maze[gridX][gridY] = 0;
                    if (current.isCellBelowNeighbor()) {
                        maze[gridX][gridY+1] = 0;
                    }
                    if (current.isCellRightNeighbor()) {
                        maze[gridX + 1 ][gridY] = 0;
                    }
                }
            }
        }

    public int[][] getMaze(){
        return maze;
    }
}