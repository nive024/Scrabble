/**
 *
 */
public class Tile {
    int wordPointMultiplier;
    int letterPointMultiplier;

    /**
     * default constructor that will create tiles with a letter and word multiplier value of 1.
     */
    Tile(){
        setLetterPointMultiplier(1);
        setWordPointMultiplier(1);
    }

    /**
     * Constructor to set letter or word multiplier.
     * @param letter
     * @param word
     */
    Tile(int letter, int word){
        setLetterPointMultiplier(letter);
        setWordPointMultiplier(word);
    }

    /**
     * Set the tile to have a word multiplier. The number used as a parameter is the multiplication value
     * @param wordPointMultiplier
     */
    public void setWordPointMultiplier(int wordPointMultiplier) {
        this.wordPointMultiplier = wordPointMultiplier;
    }

    /**
     * Set the tile to have a letter multiplier. The number used as a parameter is the multiplication value
     * @param letterPointMultiplier
     */
    public void setLetterPointMultiplier(int letterPointMultiplier) {
        this.letterPointMultiplier = letterPointMultiplier;
    }

    public void tileMultiplierUsed(){
        this.letterPointMultiplier = 1;
        this.wordPointMultiplier = 1;
    }
}
