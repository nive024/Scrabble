import java.util.*;

public class Letters {
    private static final Map<Character, Integer> Alphabet= new HashMap<>();

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

    public int getPointValue(Character c){ //return the point value associated with the letter
        return Alphabet.get(c);
    }

    public ArrayList<Character> deal(ArrayList<Character> currentLetters){
        Random r = new Random();
        Object[] keys = Alphabet.keySet().toArray();
        ArrayList<Character> newLetters = new ArrayList<>();

        int n;  //number of letters needed to be dealt
        n= 7- currentLetters.size();;

        //copy currentLetters into the newList
        for(Character c: currentLetters){
            newLetters.add(c);
        }

        //randomly deal n new letters to the player
        for(int i=0; i<n; i++){
            Character c = (Character) keys[r.nextInt(keys.length)];
            newLetters.add(c);
        }

        return newLetters;
    }

    public static void main(String[] args) {
        Letters l = new Letters();

        int p = l.getPointValue('F');
        System.out.println(p);

        ArrayList<Character> pl = new ArrayList<>();
        pl.add('A');
        pl.add('B');
        pl.add('C');
        pl.add('D');
        pl.add('E');
        pl.add('F');
        //pl.add('G');

        ArrayList<Character> c = l.deal(pl);

        for(Character ch: c ){
            System.out.println(ch);
        }

    }
}
