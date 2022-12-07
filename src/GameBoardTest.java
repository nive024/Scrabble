import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for GameBoard
 *
 * @author Rimsha Atif
 * @author Isaiah Hunte
 * @author Nivetha Sivasaravanan
 */
public class GameBoardTest {
    private GameBoard emptygameBoard;
    private GameBoard gameBoard;

    private GameBoard gameBoardCopy;
    private Player p1;
    private String s;
    private String s1;

    @Before
    public void setUp(){
        p1 = new Player("player1");
        emptygameBoard= new GameBoard(15,15);

        gameBoard = new GameBoard(15,15);
        gameBoard.setTileBoard(false, "");
        gameBoard.addPlayer(p1);
        gameBoard.setCurrentPlayer(p1);


        gameBoardCopy = new GameBoard(15, 15);
        gameBoardCopy.setTileBoard(false, "");
        gameBoardCopy.addPlayer(p1);
        gameBoardCopy.setCurrentPlayer(p1);
        s = "";
        s1 = "";
        s = gameBoard.deal(0);

        s1 = gameBoardCopy.deal(0);

    }

    @Test
    public void initialBoardShouldBeEmpty(){
        assertTrue(emptygameBoard.isBoardEmpty());
    }

 /////////////////////////////////////////////////Testing CheckWord()////////////////////////////////////////////////////////////
    @Test
    public void checkingValidWords(){
        assertTrue(gameBoard.checkWord("word"));
    }

    @Test
    public void checkingValidWordbe(){
        assertTrue(gameBoard.checkWord("BE"));
    }

    @Test
    public void checkingInvalidWords(){
        assertFalse(gameBoard.checkWord("hfhjdkj"));
    }



///////////////////////////////////////////Testing CheckCenterSquare()/////////////////////////////////////////////////////////////
    @Test
    public void checkingCenterSquareHorizontalStartingOnCenter() {
        gameBoard.placeWord("fire 8h");
        assertTrue(gameBoard.checkCenterSquare());
    }

    @Test
    public void checkingCenterSquareHorizontal() {
        gameBoard.placeWord("fire 8f");
        assertTrue(gameBoard.checkCenterSquare());
    }

    @Test
    public void checkingCenterSquareVerticalStartingOnCenter() {
        gameBoard.placeWord("fire 8h");
        assertTrue(gameBoard.checkCenterSquare());
    }

    @Test
    public void checkingCenterSquareVerticalS() {
        gameBoard.placeWord("fire h6");
        assertTrue(gameBoard.checkCenterSquare());
    }

    @Test
    public void checkNotOnCenterSquareHorizontal(){
        gameBoard.placeWord("fire 3b");
        assertFalse(gameBoard.checkCenterSquare());
    }

    @Test
    public void checkNotOnCenterSquareVertical(){
        gameBoard.placeWord("fire k4");
        assertFalse(gameBoard.checkCenterSquare());
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
        gameBoard.placeWord("tie i7");
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
        gameBoard.placeWord("fire h8");
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
        assertTrue(gameBoard.isBoardEmpty());
    }

    @Test
    public void placingFirstWordNotOnCenterSquare(){
        gameBoard.placeWord("fire a1");
        assertEquals(0,p1.getScore());
    }


/////////////////////////////////Testing CheckNewWords()/////////////////////////////////////////////////
    @Test
    public void NoInvalidNewWordsShouldBeFound(){
        gameBoard.placeWord("fire 8h");
        gameBoard.placeWord("tie i7");

        assertTrue(gameBoard.checkNewWords());
    }

    @Test
    public void InvalidWordShouldBeFoundAndNotAddedToTheBoard() {
        gameBoardCopy.placeWord("fire 8h");
        gameBoardCopy.placeWord("tie i7");

        gameBoard.placeWord("fire 8h");
        gameBoard.placeWord("tie i7");
        gameBoard.placeWord("tea 9h");

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                assertEquals(gameBoardCopy.getStringBoard()[i][j],gameBoard.getStringBoard()[i][j]);
            }
        }
    }

//////////////////////////////////////Test Deal()/////////////////////////////////////////////////////////////////////////
    @Test
    public void initiallyDeal7Letters(){
        assertEquals(7, p1.getLetters().size());
    }

    @Test
    public void CheckThatLettersAreRandom(){
        assertNotEquals(s, s1);
    }

//    @Test use the test when AI is finished
//    public void dealAfterWordIsPlayed(){
//       // letters = gameBoard.deal(letters);
//        assertEquals(4, gameBoard.deal(0).length());
//    }
    
    @Test
    public void floatingLetter(){
        gameBoard.placeWord("e 9h");
        assertEquals(0, p1.getScore());
    }

    @Test
    public void floatingWord() {
        gameBoard.placeWord("fire 8h");
        gameBoard.placeWord("fur 6h");
        assertEquals(14, p1.getScore());
    }

    @Test
    public void singleLetterWordCannotBePlaced() {
        gameBoard.placeWord("e 8h");
        assertEquals(0, p1.getScore());
    }

    @Test
    public void initialScore(){
        assertEquals(0, p1.getScore());
    }

    @Test
    public void scoreAfterFirstWordPlaced(){
        gameBoard.placeWord("fire 8h");
        assertEquals(14, p1.getScore());
    }

    @Test
    public void addingToExistingScore(){
        gameBoard.placeWord("fire 8h"); // 10 points
        gameBoard.placeWord("tie i7"); // 3 points
        assertEquals(19, p1.getScore());
    }

    @Test
    public void invalidWordDoesNotChangeScore(){
        gameBoard.placeWord("hfj 8h");
        assertEquals(0, p1.getScore());
    }

    @Test
    public void addingASingleLetter(){
        gameBoard.placeWord("fire 8h"); // 10 points
        gameBoard.placeWord("hi i7"); // 5 points
        assertEquals(23, p1.getScore());
    }

    @Test
    public void customBoard(){
        gameBoardCopy.setTileBoard(true, "test.xml");
        gameBoard.getTileBoard()[5][5] = new Tile(2, true);
        gameBoard.getTileBoard()[5][7] = new Tile(2, true);
        gameBoard.getTileBoard()[7][5] = new Tile(2, true);
        gameBoard.getTileBoard()[7][7] = new Tile(2, true);
        gameBoard.getTileBoard()[8][5] = new Tile(2, true);
        gameBoard.getTileBoard()[8][6] = new Tile(2, true);
        gameBoard.getTileBoard()[8][7] = new Tile(2, true);
        //assertTrue(gameBoard.getTileBoard()[5][7].pinkTile());
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                assertEquals(gameBoardCopy.getTileBoard()[i][j],gameBoard.getTileBoard()[i][j]);
            }
        }
    }

}
