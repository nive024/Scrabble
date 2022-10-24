/**
 * The Tile class will hold letters placed on the GameBoard.
 *
 * Select tiles will have pointMultipliers. When wordPointMultiplier is set to true, the multiplier will be used for
 * the whole word placed. When it is set to false, the multiplier will be used for the letter on the tile only.
 *
 * Once a tiles pointMultiplier is used, it can not be used again.
 *
 * @author Isaiah Hunte 101201612
 * @version 2022-10-24
 */
public class Tile {

    Letters letter;
    int pointMultiplier;
    boolean wordPointMultiplier;

    /**
     * default constructor that will create tiles with a point multiplier value of 1. The wordPointMultiplier
     * is set to false.
     */
    Tile(){
        letter = null;
        setPointMultiplier(1);
        setWordPointMultiplier(false);
    }

    /**
     * Constructor to set point multiplier value and type. If wordPointMultiplier is true the words point value will
     * be multiplied. If it is false the letter placed on the tile will have its point value multiplied.
     * @param pointMultiplier
     * @param wordPointMultiplier
     */
    Tile(int pointMultiplier, boolean wordPointMultiplier){
        letter = null;
        setPointMultiplier(pointMultiplier);
        setWordPointMultiplier(wordPointMultiplier);
    }

    /**
     * Set the tile to have a word multiplier. The number used as a parameter is the multiplication value
     * @param pointMultiplier
     */
    public void setPointMultiplier(int pointMultiplier) {
        this.pointMultiplier = pointMultiplier;
    }

    /**
     * @param wordPointMultiplier
     */
    public void setWordPointMultiplier(boolean wordPointMultiplier) {
        this.wordPointMultiplier = wordPointMultiplier;
    }

    /**
     * After a multiplier tile is used, The tiles multiplier is set to 1.
     */
    public void tileMultiplierUsed(){
        this.pointMultiplier = 1;
    }

    /**
     * @return true if the tile has no letter and false otherwise.
     */
    public boolean isEmpty(){
        return (letter == null);
    }

    /**
     * When a letter is placed on a tile it is saved here.
     */
    public void placeLetter(Letters letter){
        this.letter = letter;
    }
}
