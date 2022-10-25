import java.util.*;

public class Letters {
    private static final Map<Character, Integer> Alphabet= new HashMap<>();

    private char letter;
    private int value;

    public Letters(){
        Alphabet.put('A', 1);
        Alphabet.put('B', 3);
        Alphabet.put('C', 3);
        Alphabet.put('D', 2);
        Alphabet.put('E', 1);
        Alphabet.put('F', 2);
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

    public Letters(char letter){
        super();
        this.letter = letter;
        value = getPointValue(letter);
    }

    public char getLetter(){
        return letter;
    }
    public static int getPointValue(Character c){ //return the point value associated with the letter
        return Alphabet.get(c);
    }

    public ArrayList<Letters> deal(ArrayList<Letters> currentLetters){
        Random r = new Random();
        Object[] keys = Alphabet.keySet().toArray();
        ArrayList<Letters> newLetters = new ArrayList<>();

        int n;  //number of letters needed to be dealt
        n= 7- currentLetters.size();;

        //copy currentLetters into the newList
        for(Letters l: currentLetters){
            newLetters.add(l);
        }

        //randomly deal n new letters to the player
        for(int i=0; i<n; i++){
            Letters newLetter = new Letters((Character) keys[r.nextInt(keys.length)]);
            newLetters.add(newLetter);
        }

        return newLetters;
    }


}
