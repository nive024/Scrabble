import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    @Test
    public void testGetPlayerName(){
        Player p = new Player("Homer");
        assertEquals(p.getName(), "Homer");
    }

    @Test
    public void testSetAndGetPlayerScore(){
        Player p = new Player("Homer");
        p.setScore(30);
        assertEquals(p.getScore(), 30);
    }

}
