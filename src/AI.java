import javax.swing.*;
import java.util.ArrayList;

public class AI extends Player{
    private JButton[][] board;
    private ScrabbleFrame frame;
    private JButton[][] lettersOnBoard;
    private final int SIZE = 15;
    private ArrayList<Letters> letters;
    private GameBoard gb;
    private boolean emptyUp, emptyDown, emptyRight, emptyLeft;
    private boolean vertical, horizontal;


    public AI(ScrabbleFrame frame, GameBoard g){
        super("Bot");
        this.frame = frame;
        board = new JButton[15][15];
        gb = g;
        //lettersOnBoard = new JButton [15][15];
        letters = this.getLetters();
    }

    public String playTurn(){
        board = frame.getGrid();
        letters = this.getLetters();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!board[i][j].getText().equals("")){
                    int count = 0;

                    // Checks which tiles around the letter is empty
                    if(board[i+1][j].getText().equals("")){
                        vertical = false;
                        horizontal = true;
                        emptyDown = true;
                        count++;
                    }
                    if(board[i-1][j].getText().equals("")){
                        vertical = false;
                        horizontal = true;
                        emptyUp = true;
                        count++;
                    }
                    if(board[i][j+1].getText().equals("")){
                        horizontal = false;
                        vertical = true;
                        emptyRight = true;
                        count++;
                    }
                    if(board[i][j-1].getText().equals("")){
                        horizontal = false;
                        vertical = true;
                        emptyLeft = true;
                        count++;
                    }

                    // If there are 3 spaces around the tile
                    if (count == 3){
                        String temp = board[i][j].getText();

                        if (horizontal && emptyDown && emptyUp) {
                            temp = makeWord(temp);
                            board[i+1][j].setText(String.valueOf(temp.toCharArray()[0]));

                        }
                        //gb.placeWord(temp + "");

                    }
                }
            }
        }
        return "";
    }

    private String makeWord(String letter){


        Letters l = new Letters(letter.charAt(0));
        letters.add(l);
        String[] words = new String[100];
        for (int i = 0; i < 7; i++){
            String word = letters.get(i) + letter;
            if (gb.checkWord(word)){
                return word;
            }
        }

        return "";
    }

}
