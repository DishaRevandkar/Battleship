package battleship;

/**
 * The Battleship exception class.
 *
 * @author Shreya Pramod    sp3045@rit.edu
 * @author Disha Revandkar  dr6742@rit.edu
 */
public class BattleshipException extends Exception {

    public static final int UNSET = -1;

    private final int row;

    private final int column;

    /**
     * The constructor set the given error message and set the coordinates to the UNSET default value.
     */
    public BattleshipException( int row, int column, String message ) {
        super( message + ", row=" + row + ", column=" + column );
        this.row = row;
        this.column = column;
    }

    /**
     * The constructor stores the coordinates where the violation occurred and sets the given error message.
     */
    public BattleshipException(String msg){
        super( msg + ", row=" + UNSET + ", column=" + UNSET );
        row = UNSET;
        column = UNSET;
    }
}
