import static org.junit.Assert.*;

import java.util.HashMap;
import org.junit.*;

public class LettersTest {

    @Test
    public void checkAlphabet() {
        HashMap<Character, Integer> Alphabet = new HashMap<>();
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

        assertEquals(Letters.getAlphabet(), Alphabet);
    }

    @Test
    public void checkpointValueA() {
        Letters l = new Letters('A');
        assertEquals(1, l.getPointValue(l.getLetter()));
    }

    @Test
    public void checkpointValueZ() {
        Letters l = new Letters('Z');
        assertEquals(10, l.getPointValue(l.getLetter()));
    }

    @Test
    public void checkpointValueM() {
        Letters l = new Letters('M');
        assertEquals(3, l.getPointValue(l.getLetter()));
    }

    @Test
    public void PointValueShouldNotbeEqual(){
        Letters l = new Letters('F');
        Letters l1 = new Letters('Q');

        assertNotEquals(l.getPointValue(l.getLetter()), l1.getPointValue(l1.getLetter()));
    }

    @Test
    public void setLetter(){
        Letters l = new Letters('E');
        l.setLetter('X');

        assertTrue('X' == l.getLetter());
    }

    @Test
    public void settingLettersShouldChangePoints(){
        Letters l = new Letters('K');
        Letters l1 = new Letters('B');

        l.setLetter(l1.getLetter());

        assertEquals(l1.getPointValue(l1.getLetter()), l.getPointValue(l1.getLetter()));
    }




}