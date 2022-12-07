import java.io.Serializable;
import java.util.*;

/**
 * The Player class represents a player playing the Scrabble game.
 *
 * This class sets and gets the player's name, score and letters.
 *
 * @author Nicole Lim
 * @author Rimsha Atif
 */

public class Player implements Comparable<Player>, Serializable {

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
     * Sets the player's score after undo.
     *
     * @param s The score of the player after undo
     */
    public void setUndoScore(int s){
        score -= s;
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
     * sets the letters arraylist
     * @param l the arraylist we want to set letters to
     */
    public void setLetters(ArrayList<Letters> l){
        letters = l;
    }

    /**
     * Adds a letter to the players letters
     *
     * @param l The letter to be added to the players letters
     */
    public void addLetter(Letters l){
        letters.add(l);
    }

    /**
     * sets the name of the player
     * @param name the name of the player
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Removes a letter to the players letters
     *
     * @param l The letter to be removed to the players letters
     */
    public void removeLetter(Letters l){
        letters.remove(l);
    }

    /**
     * Compares the players based on their scores.
     * @param p the object to be compared.
     * @return a value greater than 0 if this player has a higher score, 0 if its the same, a value less than 0 if it's less
     */
    @Override
    public int compareTo(Player p) {
        return Integer.compare(p.getScore(), score);
    }

    /**
     * compares two player object
     * @param obj the player we want to compare with
     * @return true if the players are the same otherwise false
     */
    @Override
    public boolean equals(Object obj){
        Player p = (Player) obj;
        if(p.getName().equals(this.getName())){
            return true;
        }
        return false;

    }

    /**
     * Returns the string representation of a player object
     * @return the string representation of the player
     */
    @Override
    public String toString(){
        String s="#" + getName() + "#" + getScore() + "#";
        for(Letters l: letters){
            s+= l.getLetter() + ",";
        }
        return s;

    }
}


