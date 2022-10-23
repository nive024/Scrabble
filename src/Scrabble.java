import java.util.ArrayList;

public class Scrabble {

    private GameBoard gameBoard;
    private Letters letters;
    private Player player1;
    private Player player2;

    public Scrabble(){
        gameBoard = new GameBoard(5,5);
        letters = new Letters();
    }

    public static void main(String[] args) {
        Scrabble game = new Scrabble();

        game.player1 = new Player("Player 1");
        game.player2 = new Player("Player 2");
        game.gameBoard.placeWord("fire 3A");
        game.gameBoard.placeWord("hide B2");
        int p = game.letters.getPointValue('F');
        System.out.println(p);

        ArrayList<Character> pl = new ArrayList<>();
        pl.add('A');
        pl.add('B');
        pl.add('C');
        pl.add('D');
        pl.add('E');
        pl.add('F');
        //pl.add('G');

        ArrayList<Character> c = game.letters.deal(pl);

        for(Character ch: c ){
            System.out.println(ch);
        }
    }
}
