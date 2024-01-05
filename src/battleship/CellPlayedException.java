package battleship;

/**
 * A BattleshipException that informs the program that it attempted to
 * "hit" the same Cell instance more than once
 *
 * @author Shreya Pramod    sp3045@rit.edu
 * @author Disha Revandkar  dr6742@rit.edu
 */
public class CellPlayedException extends BattleshipException {

    /**
     *Descriptive error message to display for this exception
     */
    public static final String ALREADY_HIT = "This cell has already been hit";

    /**
     *The constructor stores the coordinates where the violation
     * occurred and sets the error message to ALREADY_HIT.
     */
    public CellPlayedException(int row, int column){

        super(row, column, ALREADY_HIT);

    }
}
