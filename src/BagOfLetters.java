import java.util.*;
import java.util.Map.Entry;

/**
 * The BagOfLetters represents the finite amount of letters in the scrabble game
 *
 * This class includes a hashmap of each letter and their quantities, it checks that a letter is in a bag,
 * updates the quantity of a letter, and also determines the quantity of a letter.
 *
 * @author Rimsha Atif
 * @author Nivetha Sivasaravanan
 */

public class BagOfLetters {
    private HashMap<Letters, Integer> bag = new HashMap<>();

    /**
     * The constructor initializes the bag.
     */
    public BagOfLetters() {
        initializeBag(1);
        initializeBag(2);
        initializeBag(3);
        initializeBag(4);
        initializeBag(6);
        initializeBag(8);
        initializeBag(9);
        initializeBag(12);
    }


    /**
     * This method initializes the bag to be a hashmap with key: Letters and value: the quantity of the letter
     * @param value the quantity of the letter.
     */
    public void initializeBag(int value) {

        Character[] ones = {'J', 'K', 'Q', 'X', 'Z'};
        Character[] twos = {'B', 'C', 'F', 'H','M','P', 'V', 'W', 'Y'};
        Character[] threes = {'G'};
        Character[] fours = {'D', 'L', 'S', 'U'};
        Character[] sixs = {'N', 'R', 'T'};
        Character[] eights = {'O'};
        Character[] nines = {'A','I'};
        Character[] twelves = {'E'};

        // int n = 0;
        switch(value){
            case 1:
                for(int n=0; n< ones.length; n++){
                    bag.put(new Letters(ones[n]), 1);
                }
                break;
            case 2:
                for(int n =0; n< twos.length; n++){
                    bag.put(new Letters(twos[n]),2);
                }
                break;
            case 3:
                for(int n = 0; n< threes.length; n++){
                    bag.put(new Letters(threes[n]), 3);
                }
                break;
            case 4:
                for(int n=0; n< fours.length; n++){
                    bag.put(new Letters(fours[n]), 4);
                }
                break;
            case 6:
                for(int n=0; n< sixs.length; n++){
                    bag.put(new Letters(sixs[n]), 6);
                }
                break;
            case 8:
                for(int n =0; n< eights.length; n++){
                    bag.put(new Letters(eights[n]), 8);
                }
                break;
            case 9:
                for(int n= 0; n< nines.length; n++){
                    bag.put(new Letters(nines[n]), 9);
                }
                break;
            case 12:
                for(int n= 0; n< twelves.length; n++){
                    bag.put(new Letters(twelves[n]), 12);
                }
                break;
        }
    }

    /**
     * This method returns true if the letter is in the bag and false otherwise. It also updates the quantity of the letter.
     * @param l the letter we want to search for
     * @return true if the letter is in the bag and false otherwise.
     */
    public  boolean inBag(Letters l){
        for(Entry<Letters, Integer> entry : bag.entrySet()){
            if(entry.getKey().getLetter().equals(l.getLetter())){
                bag.put(entry.getKey(), entry.getValue() -1);

                if(entry.getValue() == (0)){
                    bag.remove(entry.getKey());
                }

                return true;
            }
        }
        return false;
    }

    /**
     * This method checks the quantity of a letter.
     * @param l the letter we want to check
     * @return the quantity of the letter
     */
    public Integer getQuantity(Letters l) {
        for (Entry<Letters, Integer> entry : bag.entrySet()) {
            if (entry.getKey().getLetter().equals(l.getLetter())) {
                return entry.getValue();
            }
        }
        return 100;
    }

    /**
     * This method returns the HashMap bag;
     * @return bag
     */
    public HashMap<Letters, Integer> getBag() {
        return bag;
    }
}
