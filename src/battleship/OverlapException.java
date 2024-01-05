package battleship;

/**
 * A BattleshipException that informs the program that it attempted
 * to place a ship where there is already another ship
 *
 * @author Shreya Pramod    sp3045@rit.edu
 * @author Disha Revandkar  dr6742@rit.edu
 */
public class OverlapException extends BattleshipException{
    /**
     *Descriptive error message to display for this exception
     */
    public static final String OVERLAP = "Ships placed in overlapping positions";

    /**
     *The constructor stores the coordinates of intersection and sets
     * the error message to OVERLAP.
     */
    public OverlapException(int row, int column){

        super(row, column, OVERLAP);
    }
}
