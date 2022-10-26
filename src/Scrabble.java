import java.util.ArrayList;
import java.util.Scanner;

/**
 * Scrabble is a word game where players are each given 7 letters and attempt to create words from the letters.
 * Each word on the board must be connected to another word. Each letter is given a number of points and players
 * gets points from combining letters to create word. The goal of the game is to have the most points possible.
 *
 * The Scrabble class is the main class that starts the game.
 *
 * The class creates and initalizes a gameBoard and Players. Additionally, it starts the game.
 */
public class Scrabble {

    private GameBoard gameBoard;
    private Letters letters;
    private Player player1;
    private Player player2;

    /**
     * Constructor that initializes a game board, letters and players.
     */
    public Scrabble() {

        gameBoard = new GameBoard(7, 7, player1);
        letters = new Letters();

        initializePlayers();
    }

    /**
     * Initializes players by taking in a string from user input, that represents their name.
     */
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

    /**
     * Takes input from the user for their turn. The user inputs a word and the placement on the board.
     */
    public void playGame(){
        Scanner scan = new Scanner(System.in);

        int round = 0;
        while(round<5){
            System.out.println("Please enter your word");
            String word = scan.nextLine();
            while (!word.contains(" ")){
                System.out.println("Please enter a word and a placement. Example: WORD 4B");
                word = scan.nextLine();
            }
            gameBoard.placeWord(word, player1);

            String s = "";
            for(Letters l: player1.getLetters()){
                s += l.getLetter() + ", ";
            }
            System.out.println("These are your letters: " + s);
        }
    }
    /**
     * Creates a new Scrabble game and starts the game
     *
     * @param args Stores command line arguments
     */
    public static void main(String[] args) {
        Scrabble game = new Scrabble();
        game.playGame();
    }
}

