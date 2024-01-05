package battleship;

/**
 * A BattleshipException that informs the program that it attempted
 * to place a ship outside the bounds of the board
 *
 * @author Shreya Pramod    sp3045@rit.edu
 * @author Disha Revandkar  dr6742@rit.edu
 */
public class OutOfBoundsException extends BattleshipException{

    /**
     * Descriptive error message to display for this exception
     */
    static final String PAST_EDGE = "Coordinates are past board edge";

    /**
     * The constructor stores the illegal coordinates where the violation
     * occurred and sets the error message to PAST_EDGE.
     */
    public OutOfBoundsException(int row, int column){

        super(row, column, PAST_EDGE);
    }

}
