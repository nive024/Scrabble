import java.util.HashSet;
import java.util.Set;

/**
 * The Turn class represents a turn that a user takes.
 *
 * This class saved the board of the turn, the player who played the turn and the score of the turn
 *
 * @author Nicole Lim
 */
public class Turn {
    private String[][] board;
    private Tile[][] tileBoard;
    private Player player;
    private int score;
    private Set<String> wordOnBoard;

    /**
     * Constructor for the turn object
     * @param b the board of the turn
     * @param p the player whose turn it was
     * @param s the score of the turn
     */
    public Turn(String[][] b, Player p, int s, Tile[][] t, Set<String> w){
        super();
        board = b;
        player = p;
        score = s;
        tileBoard = t;
        wordOnBoard = w;
    }

    public Turn() {
        score = 0;
        tileBoard = new Tile[15][15];
        player = new Player("");
        board = new String[15][15];
        wordOnBoard = new HashSet<>();
    }

    /**
     * Returns the gameboard of the player's turn
     * @return gameboard of the turn that was just played
     */
    public String[][] getBoard() {
        return board;
    }

    /**
     * Returns the tile gameboard of the player's turn
     * @return tile gameboard of the turn that was just played
     */
    public Tile[][] getTileBoard() {
        return tileBoard;
    }

    /**
     * Sets the board to the current board
     * @param board the board with the most recent play
     */
    public void setBoard(String[][] board) {
        this.board = board;
    }

    /**
     * Sets the set with the current words on the board
     * @param words the set with the words on the board
     */
    public void setWordOnBoard(Set<String> words) {
        this.wordOnBoard.addAll(words);
    }

    /**
     * Sets the tile board to the current tile board
     * @param board the tile board with the most recent play
     */
    public void setTileBoard(Tile[][] board) {
        //this.tileBoard = board;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                this.tileBoard[i][j] = new Tile(board[i][j].getPointMultiplier(), board[i][j].isWordPointMultiplier());
            }
        }
    }

    /**
     * Returns the player who just played the last move
     * @return most recent player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the set of words on the last move
     * @return set of words on the last move
     */
    public Set<String> getWordsOnBoard() {
        return wordOnBoard;
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
        this.score += score;
    }
}

