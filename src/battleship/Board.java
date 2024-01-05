package battleship;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The class to represent the grid of cells (squares).
 *
 * @author Shreya Pramod    sp3045@rit.edu
 * @author Disha Revandkar  dr6742@rit.edu
 */
public class Board implements Serializable {
    private int height;
    private int width;
    private Cell cells[][];
    private ArrayList<Ship> ships;

    /**
     *Construct a board.
     */
    public Board(int height, int width) {
        this.height = height;
        this.width = width;

        ships = new ArrayList<Ship>();
        cells = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    /**
     *Creating ships
     */
    public ArrayList<Ship> getShips() {
        return ships;
    }

    /**
     *Used for input error checking.
     */
    public int getHeight() {
        return height;
    }

    /**
     *Used for input error checking.
     */
    public int getWidth() {
        return width;
    }

    /**
     *Fetch the Cell object at the given location.
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     *Useful for debugging. This is not the method that displays the board to the user.
     */
    public String toString() {
        return "The board dimensions are " + getHeight() + " * " + getWidth();
    }

    /**
     * Adds ship according to the input
     */
    public void addShip(Ship ship) {
        ships.add(ship);
    }

    /**
     * checks if all ships have sunk
     */
    public boolean allSunk() {
        int n = ships.size();
        for (int i = 0; i < n; i++) {
            if (!ships.get(i).isSunk()) {
                return false;
            }
        }
        return true;
    }

}
