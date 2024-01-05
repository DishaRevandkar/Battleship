package battleship;

import java.io.*;

/**
 * The main class that runs the game.
 *
 * @author Shreya Pramod    sp3045@rit.edu
 * @author Disha Revandkar  dr6742@rit.edu
 */

public class Battleship {
    /**
     * At end of game.
     */
    public static final String ALL_SHIPS_SUNK = "All ships sunk!";

    /**
     * For player commands
     */
    public static final String BAD_ARG_COUNT = "Wrong number of arguments for command";

    /**
     * We don't allow boards larger than this value, height or width.
     */
    public static final String DIM_TOO_BIG = "Board dimensions too big to display.";

    /**
     * We don't allow boards larger than this value, height or width.
     */
    public static final int MAX_DIM = 20;

    /**
     * What to display when the program is ready for a user command
     */
    public static final String PROMPT = "> ";

    /**
     * Help message.
     */
    public static final String HELP = "help";

    /**
     * The regular expression to use with String.split(String).
     */
    public static final String WHITESPACE = "\\s+";

    /**
     * HIT symbol.
     */
    public static final String HIT = "h";

    /**
     * Save symbol.
     */
    public static final String SAVE = "s";

    /**
     * Reveal symbol.
     */
    public static final String REVEAL = "!";

    /**
     * Quit symbol.
     */
    public static final String QUIT = "q";

    /**
     * saved filename.
     */
    public static final String SAVE_FILENAME = "board.ser";

    /**
     * help message to be displayed.
     */
    public static final String HELP_MESSAGE = "h row column - Hit a cell.\ns file - Save game state to file. (Serialization process)\n! - Reveal all ship locations.\nq - Quit game.";


    /**
     * Read the setup file and build all data structures needed later.
     */
    public static void main(String[] args)
            throws IOException, OutOfBoundsException, OverlapException, CellPlayedException {

        if (args.length != 1) {
            System.out.println(BAD_ARG_COUNT);
            return;
        }
        play(args[0]);
    }

    /**
     * Displays the board.
     */
    public static void displayCells(Cell[][] cells, boolean cheatMode) {
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                if (cheatMode) {
                    System.out.print(cell.displayChar() + " ");
                } else {
                    System.out.print(cell.displayHitStatus() + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Simulation method for the game.
     */
    public static void play(String filename)
            throws IOException, OutOfBoundsException, OverlapException, CellPlayedException {
        Board board = null;

        try (BufferedReader input = new BufferedReader(new FileReader(filename))) {

            // discard first line headers
            String line = null;

            boolean readDimensions = false;
            // read until EOF
            while ((line = input.readLine()) != null) {
                if (!readDimensions) {
                    String[] content = line.split(" ");
                    int height = Integer.parseInt(content[0]);
                    int width = Integer.parseInt(content[1]);

                    if (height >= MAX_DIM || width >= MAX_DIM) {
                        System.out.println(DIM_TOO_BIG);
                        continue;
                    }
                    board = new Board(height, width);
                    readDimensions = true;
                } else {
                    String[] content = line.split(" ");
                    int uRow = Integer.parseInt(content[0]);
                    int lCol = Integer.parseInt(content[1]);
                    Orientation orientation = Orientation.HORIZONTAL;
                    if (content[2].equals("VERTICAL")) {
                        orientation = Orientation.VERTICAL;
                    }
                    int length = Integer.parseInt(content[3]);
                    Ship ship = new Ship(board, uRow, lCol, orientation, length);
                    board.addShip(ship);
                }
            }

        } catch (IOException e) {
            System.out.println("Error  reading file");
            System.exit(0);
        }

        Cell[][] cells = board.getCells();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        while (!board.allSunk()) {
            System.out.print(PROMPT);
            String line = reader.readLine();
            if (line.equals(HELP)) {
                System.out.println(HELP_MESSAGE);
            } else if (line.equals(QUIT)) {
                System.exit(0);
            } else if (line.equals(REVEAL)) {
                displayCells(cells, true);
            } else if (line.equals(SAVE)) {
                System.out.println("Saving file");
                write(SAVE_FILENAME, board);
            } else {
                String[] content = line.split(" ");
                if (!content[0].equals(HIT) || content.length < 3) {
                    System.out.println(BAD_ARG_COUNT);
                    continue;
                }
                int row = Integer.parseInt(content[1]);
                int col = Integer.parseInt(content[2]);
                if (row >= board.getHeight() || col >= board.getWidth()) {
                    System.out.println(DIM_TOO_BIG);
                    continue;
                }
                try {
                    cells[row][col].hit();
                } catch (CellPlayedException e) {
                }

                displayCells(cells, false);

            }

        }
        System.out.println(ALL_SHIPS_SUNK);
    }

    /**
     * Serialize object and write to file
     * 
     * @param filename filename
     * @param board    board object
     */
    public static void write(String filename, Board board) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(board);

            objectOutputStream.close();
            fileOutputStream.close();

        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }
}