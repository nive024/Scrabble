import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import org.junit.Test;

import static org.junit.Assert.*;

public class BagOfLettersTest {

    /**protected void setUp(){
        HashMap<Letters, Integer> Bag = new HashMap<>();

        Letters[] l = new Letters[26];
        int i = 0;
        for (Character key : Letters.getAlphabet().keySet()) {
            l[i] = new Letters(key);
            i++;
        }

        Bag.put(l[0], 9); //a
        Bag.put(l[1], 2); //b
        Bag.put(l[2], 2); //c
        Bag.put(l[3], 4); //d
        Bag.put(l[4], 12);//e
        Bag.put(l[5], 2); //f
        Bag.put(l[6], 3); //g
        Bag.put(l[7], 2); //h
        Bag.put(l[8], 9); //i
        Bag.put(l[9], 1); //j
        Bag.put(l[10], 1); //k
        Bag.put(l[11], 4); //l
        Bag.put(l[12], 2); //m
        Bag.put(l[13], 6); //n
        Bag.put(l[14], 8); //o
        Bag.put(l[15], 2); //p
        Bag.put(l[16], 1); //q
        Bag.put(l[17], 6); //r
        Bag.put(l[18], 4); //s
        Bag.put(l[19], 6); //t
        Bag.put(l[20], 4); //u
        Bag.put(l[21], 2); //v
        Bag.put(l[22], 2); //w
        Bag.put(l[23], 1); //x
        Bag.put(l[24], 2); //y
        Bag.put(l[25], 1); //z
    } **/

    @Test
    public void InitialBag() {
        HashMap<Letters, Integer> Bag = new HashMap<>();

        Letters[] l = new Letters[26];
        int i = 0;
        for (Character key : Letters.getAlphabet().keySet()) {
            l[i] = new Letters(key);
            i++;
        }

        Bag.put(l[9], 1); //j
        Bag.put(l[10], 1); //k
        Bag.put(l[16], 1); //q
        Bag.put(l[23], 1); //x
        Bag.put(l[25], 1); //z


        Bag.put(l[1], 2); //b
        Bag.put(l[2], 2); //c
        Bag.put(l[5], 2); //f
        Bag.put(l[7], 2); //h
        Bag.put(l[12], 2); //m
        Bag.put(l[15], 2); //p
        Bag.put(l[21], 2); //v
        Bag.put(l[22], 2); //w
        Bag.put(l[24], 2); //y

        Bag.put(l[6], 3); //g

        Bag.put(l[3], 4); //d
        Bag.put(l[11], 4); //l
        Bag.put(l[18], 4); //s
        Bag.put(l[20], 4); //u

        Bag.put(l[13], 6); //n
        Bag.put(l[17], 6); //r
        Bag.put(l[19], 6); //t

        Bag.put(l[14], 8); //o

        Bag.put(l[0], 9); //a
        Bag.put(l[8], 9); //i

        Bag.put(l[4], 12);//e


        for(Letters key: Bag.keySet()){
            for(Letters letter: BagOfLetters.getBag().keySet()){
                if(letter.equals(key)){
                    assertEquals(BagOfLetters.getBag().get(letter), Bag.get(key));
                }
            }
        }
    }
}

