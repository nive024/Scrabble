import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for BagOfLetters
 *
 * @author Rimsha Atif
 */
public class BagOfLettersTest {
    private BagOfLetters bagOfLetters;

    private Letters letter;
     @Before
    public void setUp(){


        bagOfLetters = new BagOfLetters();

        letter = new Letters('R');
    }

    @Test
    public void InitialBag() {
         assertEquals(26, bagOfLetters.getBag().keySet().size());
    }

    @Test
    public void CheckElementinBag(){
        Letters l = new Letters('M');
        assertEquals(true, bagOfLetters.inBag(l));

    }

    @Test
    public void CheckElementNotInBag(){
        Letters l = new Letters('m');
        assertEquals(false, bagOfLetters.inBag(l));
    }

    @Test
    public void checkQuantityIsUpdated() {

        int originalQuantity = bagOfLetters.getQuantity(letter);
        bagOfLetters.inBag(letter);
        bagOfLetters.inBag(letter);
        int newQuantity = bagOfLetters.getQuantity(letter);

        assertEquals(originalQuantity -2, newQuantity);
    }
}

