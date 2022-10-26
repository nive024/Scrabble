import java.util.*;

/**
 * The Player class represents a player playing the Scrabble game.
 *
 * This class sets and gets the player's name, score and letters.
 *
 * @author Nicole Lim
 * @author Rimsha Atif
 */

public class Player {

    private String name;
    private int score;
    private ArrayList<Letters> letters;

    /**
     * Creates a new player with the name parameter, sets their score to 0 and gives player letters.
     *
     * @param name Name of the player
     */
    public Player(String name){
        this.name = name;
        score = 0;
        letters = new ArrayList<>();
    }

    /**
     * Sets the name of the player.
     *
     * @param n The name to set to the players
     */
    public void setName(String n){
        name = n;
    }

    /**
     * Gets the name of the player.
     *
     * @return the name the of the player
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the player's score.
     *
     * @param s The score of the player
     */
    public void setScore(int s){
        score += s;
    }

    /**
     * Gets the player's score
     *
     * @return The score of the player
     */
    public int getScore(){
        return score;
    }

    /**
     * Gets the player's letters.
     *
     * @return The player's letters
     */
    public ArrayList<Letters> getLetters(){
        return letters;
    }

    /**
     * Gives the players letters.
     *
     * @param newList The list of the player's letters
     */
    public void setLetters(ArrayList<Letters> newList){
        letters.clear();

        for(Letters l : newList){
            letters.add(l);
        }
    }

}


