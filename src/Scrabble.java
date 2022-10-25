import java.util.ArrayList;
import java.util.Scanner;

public class Scrabble {

    private GameBoard gameBoard;
    private Letters letters;
    private Player player1;
    private Player player2;

    public Scrabble() {

        gameBoard = new GameBoard(5, 5, player1);
        letters = new Letters();

        initializePlayers();
    }

    public void initializePlayers(){
        //Player 1
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Player1's Name: ");
        player1 = new Player(scan.nextLine());
        player1.setLetters(letters.deal(player1.getLetters()));

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
        System.out.println("Enter your word: ");
        this.gameBoard.placeWord(scan.nextLine(), player1);
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
