package battleship;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A single ship in a Battleship game
 */
public class Ship implements Serializable {

    public static final String SUNK_MESSAGE = "A battleship has been sunk!";

    private Board board;
    private int uRow;
    private int lCol;
    private Orientation ort;
    private int length;
    private int count = 0;
    private boolean isSunk;

    /**
     * Creates ship constructor.
     */
    public Ship(Board board, int uRow, int lCol, Orientation ort, int length)
            throws OverlapException, OutOfBoundsException {
        this.board = board;
        this.uRow = uRow;
        this.lCol = lCol;
        this.ort = ort;
        this.length = length;

        if (isOutOfBound()) {
            throw new OutOfBoundsException(uRow, lCol);
        }

        if (isOverlapped()) {
            throw new OverlapException(uRow, lCol);
        }

        addShipToGrid();
    }

    /**
     * Checks boundaries for exceptions.
     */
    private boolean isOutOfBound() {
        if (uRow < 0 || uRow >= board.getHeight()) {
            return true;
        }
        if (lCol < 0 || lCol >= board.getWidth()) {
            return true;
        }

        if (ort.equals(Orientation.HORIZONTAL)) {
            if (lCol + length >= board.getWidth()) {
                return false;
            }
        }

        else if (ort.equals(Orientation.VERTICAL)) {
            if (uRow + length >= board.getHeight()) {
                return false;
            }
        }

        return false;

    }

    /**
     * Checks overlap exception
     */
    private boolean isOverlapped() {
        Cell[][] cells = board.getCells();

        if (ort.equals(Orientation.HORIZONTAL)) {
            for (int col = lCol; col < lCol + length; col++) {
                if (cells[uRow][col].shipExists()) {
                    return true;
                }
            }
        } else {
            for (int row = uRow; row < uRow + length; row++) {
                if (cells[row][lCol].shipExists()) {
                    return true;
                }
            }
        }

        return false;

    }

    /**
     * Adds the ship to the board.
     */
    private void addShipToGrid() {
        Cell[][] cells = board.getCells();
        if (ort.equals(Orientation.HORIZONTAL)) {
            for (int col = lCol; col < lCol + length; col++) {
                try {
                    cells[uRow][col].putShip(this);
                } catch (Exception e) {
                }

            }
        } else {
            for (int row = uRow; row < uRow + length; row++) {
                try {
                    cells[row][lCol].putShip(this);

                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * Hit function for the hit cell.
     */
    public void hit() {
        ++count;
        if (count == length) {
            isSunk = true;
            Cell[][] cells = board.getCells();
            if (ort.equals(Orientation.HORIZONTAL)) {
                for (int col = lCol; col < lCol + length; col++) {
                    try {
                        cells[uRow][col].updateStatus(Cell.SUNK_SHIP_SECTION);
                    } catch (Exception e) {
                    }
                }
            } else {
                for (int row = uRow; row < uRow + length; row++) {
                    try {
                        cells[row][lCol].updateStatus(Cell.SUNK_SHIP_SECTION);

                    } catch (Exception e) {
                    }
                }
            }
            System.out.println(SUNK_MESSAGE);
        }
    }

    /**
     * Returns if ship is sunk or not.
     */
    public boolean isSunk() {
        return isSunk;
    }
}
