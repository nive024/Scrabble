import java.util.*;
/**
 * The Letters class represents a Letter placed on a Tile on the board.
 *
 * This class sets the point value for each Letter and gets the character and point value
 *
 * @author Rimsha Atif
 * @author Nivetha Sivasaravanan
 */
public class Letters {
    private static Map<Character, Integer> Alphabet= new HashMap<>();
    static{
        Alphabet.put('A', 1);
        Alphabet.put('B', 3);
        Alphabet.put('C', 3);
        Alphabet.put('D', 2);
        Alphabet.put('E', 1);
        Alphabet.put('F', 4);
        Alphabet.put('G', 2);
        Alphabet.put('H', 4);
        Alphabet.put('I', 1);
        Alphabet.put('J', 8);
        Alphabet.put('K', 5);
        Alphabet.put('L', 1);
        Alphabet.put('M', 3);
        Alphabet.put('N', 1);
        Alphabet.put('O', 1);
        Alphabet.put('P', 3);
        Alphabet.put('Q', 10);
        Alphabet.put('R', 1);
        Alphabet.put('S', 1);
        Alphabet.put('T', 1);
        Alphabet.put('U', 1);
        Alphabet.put('V', 4);
        Alphabet.put('W', 4);
        Alphabet.put('X', 8);
        Alphabet.put('Y', 4);
        Alphabet.put('Z', 10);
    }
    private Character Letter;
    private Integer Value;

    /**
     * Creates a Letter object with a specific char and sets the point value
     * @param letter Character of the Letters object
     */
    public Letters(Character letter){
        super();
        this.Letter = letter;
        Value = getPointValue(letter);
    }

    /**
     * Gets the character of the Letters object
     *
     * @return the character of the Letters object
     */
    public Character getLetter(){
        if(Letter == null){
            return null;
        }
        return Letter;
    }

    public void setLetter(Character c){
        Letter = c;
    }

    /**
     * Gets the point value of the given char
     *
     * @param c Character whose point value is needed
     * @return The point value of the given char
     */
    public int getPointValue(Character c) { //return the point value associated with the letter
        for (Character character : Alphabet.keySet()) {
            if (character.equals(c)) {
                return Alphabet.get(character);
            }
        }
        return 0; //for invalid letter
    }

    /**
     * Gets the hash map of the point value for each letter
     *
     * @return The hash map of the point value for each letter
     */
    public static Map<Character, Integer> getAlphabet(){
        return Alphabet;
    }


    /**
     * This method overrides the Object equals() method to compare two letters using their Letter.
     * @param obj the Letter object that we want to compare
     * @return true if the objects are equal otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if (obj instanceof Letters) {
            return this.getLetter().equals(((Letters) obj).getLetter());
        } else {
            return false;
        }

    }

    @Override
    public String toString(){
        return Letter + "";
    }
}
