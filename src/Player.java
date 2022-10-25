import java.util.ArrayList;

public class Player {

    /**
     * name variable for the name of the player
     */
    private String name;
    /**
     * score variable for the score of score
     */
    private int score;

    private ArrayList<Letters> letters;

    /**
     *
     * @param name Name of the player
     */
    public Player(String name){
        this.name = name;
        score = 0;
        letters = new ArrayList<>();
    }

    /**
     *
     * @param n The name to set to the players
     */
    public void setName(String n){
        name = n;
    }

    /**
     *
     * @return the name the of the player
     */
    public String getName(){
        return name;
    }

    /**
     *
     * @param s The score of the player
     */
    public void setScore(int s){
        score += s;
    }

    /**
     *
     * @return The score of the player
     */
    public int getScore(){
        return score;
    }

    public ArrayList<Letters> getLetters(){
        return letters;
    }

    public void setLetters(ArrayList<Letters> newList){
        letters.clear();

        for(Letters l : newList){
            letters.add(l);
        }
    }

}


