import java.util.ArrayList;
import java.util.Scanner;

public class Scrabble {

    private GameBoard gameBoard;
    private Letters letters;
    private Player player1;
    private Player player2;

    public Scrabble() {

        gameBoard = new GameBoard(7, 7, player1);
        letters = new Letters();

        initializePlayers();
    }

    public void initializePlayers(){
        //Player 1
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Player1's Name: ");
        player1 = new Player(scan.nextLine());
        player1.setLetters(gameBoard.deal(player1.getLetters()));

        String s = "";
        for(Letters l: player1.getLetters()){
            s += l.getLetter() + ", ";
        }
        System.out.println("These are your letters: " + s);

        /**
        //Player 2
        System.out.println("Enter Player2's Name: ");
        player2 = new Player(scan.nextLine());
        player2.setLetters(letters.deal(player2.getLetters()));
        **/

    }
    public void playGame(){
        Scanner scan = new Scanner(System.in);
        System.out.println("check: " + gameBoard.checkWord("be"));
//        gameBoard.placeWord("horses 4A", player1);
//        gameBoard.placeWord("wir(e) E1", player1);
//        gameBoard.placeWord("xi(s) D2", player1);
//        gameBoard.placeWord("a(r)s C3", player1);
        gameBoard.placeWord("horn 4B", player1);
        gameBoard.placeWord("fa(r)m D2", player1);
        gameBoard.placeWord("paste 6B", player1);
        gameBoard.placeWord("mob 5D",player1);
        //this.gameBoard.placeWord(scan.nextLine(), player1);
    }

    public static void main(String[] args) {
        Scrabble game = new Scrabble();
        game.playGame();

        /**
        game.player1 = new Player("Player 1");
        game.player2 = new Player("Player 2");
        game.gameBoard.placeWord("fire 3A");
        game.gameBoard.placeWord("h(i)de B2");
        int p = game.letters.getPointValue('F');
        System.out.println(p);
        **/
    }
}
