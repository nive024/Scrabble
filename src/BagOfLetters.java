import java.util.*;
import java.util.Map.Entry;


public class BagOfLetters {
    private static  HashMap<Letters, Integer> Bag = new HashMap<>();
    static{
        initializeBag(1);
        initializeBag(2);
        initializeBag(3);
        initializeBag(4);
        initializeBag(6);
        initializeBag(8);
        initializeBag(9);
        initializeBag(12);
    }

    public BagOfLetters() {
    }

    public static void initializeBag(int value) {

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
                    Bag.put(new Letters(ones[n]), 1);
                }
                break;
            case 2:
                for(int n =0; n< twos.length; n++){
                    Bag.put(new Letters(twos[n]),2);
                }
                break;
            case 3:
                for(int n = 0; n< threes.length; n++){
                    Bag.put(new Letters(threes[n]), 3);
                }
                break;
            case 4:
                for(int n=0; n< fours.length; n++){
                    Bag.put(new Letters(fours[n]), 4);
                }
                break;
            case 6:
                for(int n=0; n< sixs.length; n++){
                    Bag.put(new Letters(sixs[n]), 6);
                }
                break;
            case 8:
                for(int n =0; n< eights.length; n++){
                    Bag.put(new Letters(eights[n]), 8);
                }
                break;
            case 9:
                for(int n= 0; n< nines.length; n++){
                    Bag.put(new Letters(nines[n]), 9);
                }
                break;
            case 12:
                for(int n= 0; n< twelves.length; n++){
                    Bag.put(new Letters(twelves[n]), 12);
                }
                break;
        }
    }

    public boolean inBag(Letters l){
        for(Entry<Letters, Integer> entry : BagOfLetters.getBag().entrySet()){
            if(entry.getKey().equals(l)){
                BagOfLetters.getBag().put(entry.getKey(), entry.getValue() -1);

                if(entry.getValue() == (0)){
                    BagOfLetters.getBag().remove(entry.getKey());
                }

                return true;
            }
        }
        return false;
    }

    public static HashMap<Letters, Integer> getBag() {
        return Bag;
    }
}
