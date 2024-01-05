package battleship;

import java.io.Serializable;

/**
 * A single spot on the Battleship game board.
 * A cell knows if there is a ship on it, and it remember
 * if it has been hit.
 */
public class Cell implements Serializable {

    /** Character to display for a ship that has been entirely sunk */
    public static final char SUNK_SHIP_SECTION = '*';

    /** Character to display for a ship that has been hit but not sunk */
    public static final char HIT_SHIP_SECTION = '☐';

    /** Character to display for a water cell that has been hit */
    public static final char HIT_WATER = '.';

    /**
     * Character to display for a water cell that has not been hit.
     * This character is also used for an unhit ship segment.
     */
    public static final char PRISTINE_WATER = '_';

    /**
     * Character to display for a ship section that has not been
     * sunk, when revealing the hidden locations of ships
     */
    public static final char HIDDEN_SHIP_SECTION = 'S';

    private int row;
    private int column;
    private Ship ship;
    private char status = PRISTINE_WATER;

    /**
     *
     Create a new cell.
     */
    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Place a ship on this cell.
     */
    public void putShip(Ship ship) throws OverlapException {
        if (this.ship == null) {
            this.ship = ship;
            status = HIDDEN_SHIP_SECTION;
        } else
            throw new OverlapException(row, column);
    }

    /**
     * Updates the status of the cell
     */
    public void updateStatus(char status){
        this.status = status;
    }

    /**
     * checks if ship exists.
     */
    boolean shipExists() {
        return ship != null;
    }

    /**
     * Checks the cells once hit.
     */
    public void hit() throws CellPlayedException {
        if (status == HIDDEN_SHIP_SECTION) {
            ship.hit();
            if (ship.isSunk()) {
                status = SUNK_SHIP_SECTION;
            } else {
                status = HIT_SHIP_SECTION;
            }
        } else if (status == PRISTINE_WATER) {
            status = HIT_WATER;
        } else {
            throw new CellPlayedException(row, column);
        }
    }

    /**
     * Displays status after hitting.
     */
    public char displayHitStatus() {
        if (status == HIDDEN_SHIP_SECTION) {
            return PRISTINE_WATER;
        } else {
            return status;
        }
    }

    /**
     * Return a character representing the state of this Cell.
     */
    public char displayChar() {
        return status;
    }

}
