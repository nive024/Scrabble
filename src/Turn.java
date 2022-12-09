/**
 * The Turn class represents a turn that a user takes.
 *
 * This class saved the board of the turn, the player who played the turn and the score of the turn
 *
 * @author Nicole Lim
 */
public class Turn {
    private String[][] board;
    private Player player;
    private int score;

    /**
     * Constructor for the turn object
     * @param b the board of the turn
     * @param p the player whose turn it was
     * @param s the score of the turn
     */
    public Turn(String[][] b, Player p, int s){
        board = b;
        player = p;
        score = s;
    }

    /**
     * Returns the gameboard of the player's turn
     * @return gameboard of the turn that was just played
     */
    public String[][] getBoard() {
        return board;
    }

    /**
     * Sets the board to the current board
     * @param board the board with the most recent play
     */
    public void setBoard(String[][] board) {
        this.board = board;
    }

    /**
     * Returns the player who just played the last move
     * @return most recent player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player of the most recent turn
     * @param player most recent player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Returns the score of the most recent player
     * @return score of the player
     */
    public int getScore() {
        return score;
    }

    /**
     * sets the score of the turn
     * @param score the score of the turn
     */
    public void setScore(int score) {
        this.score = score;
    }
}

