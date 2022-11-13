import static org.junit.Assert.*;
import java.util.HashMap;
import org.junit.*;

public class LettersTest {
    private HashMap<Character, Integer> Alphabet;
    private Letters l;
    private Letters l1;
    private Letters l2;
    private Letters l3;

    @Before
    public void setUp(){
        Alphabet = new HashMap<>();
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

        l = new Letters('A');
        l1 = new Letters('Z');
        l2 = new Letters('M');
        l3 = new Letters('*');

    }

    @Test
    public void checkAlphabet() {
        assertEquals(Letters.getAlphabet(), Alphabet);
    }

    @Test
    public void checkpointValueA() {

        assertEquals(1, l.getPointValue(l.getLetter()));
    }

    @Test
    public void checkpointValueZ() {

        assertEquals(10, l1.getPointValue(l1.getLetter()));
    }

    @Test
    public void checkpointValueM() {

        assertEquals(3, l2.getPointValue(l2.getLetter()));
    }

    @Test
    public void pointValueForInvalidLetter(){

        assertEquals(0, l3.getPointValue(l3.getLetter()));
    }

    @Test
    public void PointValueShouldNotbeEqual(){
        assertNotEquals(l.getPointValue(l.getLetter()), l1.getPointValue(l1.getLetter()));
    }

    @Test
    public void setLetter(){
        l.setLetter('X');

        assertTrue('X' == l.getLetter());
    }

    @Test
    public void settingLettersShouldChangePoints(){
        l.setLetter(l1.getLetter());

        assertEquals(l1.getPointValue(l1.getLetter()), l.getPointValue(l1.getLetter()));
    }




}