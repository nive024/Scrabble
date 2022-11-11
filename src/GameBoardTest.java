import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;

public class GameBoardTest {
    private GameBoard emptygameBoard;
    private GameBoard gameBoard;

    private GameBoard gameBoardCopy;
    private Player p1;

    private ArrayList<Letters> letters;
    private ArrayList<Letters> letters1;

    @Before
    public void setUp(){
        p1 = new Player("player1");
        emptygameBoard= new GameBoard(15,15);

        gameBoard = new GameBoard(15,15);
        gameBoard.setCurrentPlayer(p1);


        gameBoardCopy = new GameBoard(15, 15);
        gameBoardCopy.setCurrentPlayer(p1);

        letters = new ArrayList<>();
        //letters = gameBoard.deal(letters);
        letters1 = new ArrayList<>();
        //letters1 = gameBoard.deal(letters1);

    }

    @Test
    public void initialBoardShouldBeEmpty(){
        assertEquals(true, emptygameBoard.isBoardEmpty());
    }

 /////////////////////////////////////////////////Testing CheckWord()////////////////////////////////////////////////////////////
    @Test
    public void checkingValidWords(){
        assertEquals(true, gameBoard.checkWord("word"));
    }

    @Test
    public void checkingValidWordbe(){
        assertEquals(true, gameBoard.checkWord("be"));
    }

    @Test
    public void checkingInvalidWords(){
        assertEquals(false, gameBoard.checkWord("hfhjdkj"));
    }



///////////////////////////////////////////Testing CheckCenterSquare()/////////////////////////////////////////////////////////////
    @Test
    public void checkingCenterSquareHorizontalStartingOnCenter() {
        assertEquals(true, gameBoard.checkCenterSquare("fire", "8h"));
    }

    @Test
    public void checkingCenterSquareHorizontal() {
        assertEquals(true, gameBoard.checkCenterSquare("fire", "8f"));
    }

    @Test
    public void checkingCenterSquareVerticalStartingOnCenter() {
        assertEquals(true, gameBoard.checkCenterSquare("fire", "h8"));
    }

    @Test
    public void checkingCenterSquareVerticalS() {
        assertEquals(true, gameBoard.checkCenterSquare("fire", "h6"));
    }

    @Test
    public void checkNotOnCenterSquareHorizontal(){
        assertEquals(false, gameBoard.checkCenterSquare("fire", "3b"));
    }

    @Test
    public void checkNotOnCenterSquareVertical(){
        assertEquals(false, gameBoard.checkCenterSquare("fire", "k4"));
    }


////////////////////////////////////testing placeWord()/////////////////////////////////////////////////////////////////
    @Test
    public void checkPlaceWordHorizontalEmptyBoard(){
        gameBoard.placeWord("fire 8h");
       String s= "";
       int j;

       for(j=7; j<11; j++){
           s += gameBoard.getStringBoard()[7][j];
        }

      assertEquals("FIRE", s);
    }

    @Test
    public void checkPlaceWordVerticalEmptyBoard(){

        gameBoard.placeWord("fire h8");
        String s= "";
        int j;

        for(j=7; j<11; j++){
            s += gameBoard.getStringBoard()[j][7];
        }

        assertEquals("FIRE", s);
    }

    @Test
    public void checkPlaceWordBoardNotEmpty(){
        gameBoard.placeWord("fire 8h");
        gameBoard.placeWord("t(i)e i7");
        String s1= "";
        String s2 = "";
        int j;

        for(j=7; j<11; j++){
            s1 += gameBoard.getStringBoard()[7][j];
        }

        for(int i = 6; i<9; i++){
            s2 += gameBoard.getStringBoard()[i][8];
        }

        assertEquals("FIRE", s1);
        assertEquals("TIE", s2);

    }

    @Test
    public void placingOverAnotherWordShouldNotBePlaced(){
        gameBoard.placeWord("true h8");

        String s= "";
        int j;

        for(j=7; j<11; j++){
            s += gameBoard.getStringBoard()[j][7];
        }

        assertNotEquals("TRUE", s);
    }

    @Test
    public void placingOverAnotherWordShouldNotChangeTheBoard(){
        gameBoard.placeWord("fire h8");
        gameBoard.placeWord("true h8");

        String s= "";
        int j;

        for(j=7; j<11; j++){
            s += gameBoard.getStringBoard()[j][7];
        }

        assertEquals("FIRE", s);
    }

    @Test
    public void placingInvalidWordShouldNotChangeBoard(){
        gameBoard.placeWord("hfj 8h");
        assertEquals(true, gameBoard.isBoardEmpty());
    }

/////////////////////////////////Testing CheckNewWords()/////////////////////////////////////////////////
    @Test
    public void NoInvalidNewWordsShouldBeFound(){
        gameBoard.placeWord("fire 8h");
        gameBoard.placeWord("t(i)e i7");

        assertEquals(true, gameBoard.checkNewWords());
    }

    @Test
    public void InvalidWordShouldBeFoundAndNotAddedToTheBoard() {
        gameBoardCopy.placeWord("fire 8h");
        gameBoardCopy.placeWord("t(i)e i7");

        gameBoard.placeWord("fire 8h");
        gameBoard.placeWord("t(i)e i7");
        gameBoard.placeWord("t(e)a 9h");

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                assertEquals(gameBoardCopy.getStringBoard()[i][j],gameBoard.getStringBoard()[i][j]);
            }
        }
    }

//////////////////////////////////////Test Deal()/////////////////////////////////////////////////////////////////////////
    @Test
    public void initiallyDeal7Letters(){

        assertEquals(7, letters.size());
    }

    @Test
    public void CheckThatLettersAreRandom(){
        assertEquals(false, letters.equals(letters1));
    }

    @Test
    public void dealAfterWordIsPlayed(){
        letters.remove(0);
        letters.remove(1);

       // letters = gameBoard.deal(letters);
        assertEquals(7, letters.size());
    }


////////To do
    //floatingLetter()
    //adding a letter on to a word
    // score (initially zero, after word is played, adding on to previous score, entering invalid word doesn't change score)

}
