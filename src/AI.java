import javax.swing.*;
import java.util.ArrayList;

public class AI extends Player {
    private String[][] board;
    private ScrabbleFrame frame;
    private String[][] lettersOnBoard;
    private final int SIZE = 15;
    private ArrayList<Letters> letters;
    private GameBoard gb;
    private boolean emptyUp, emptyDown, emptyRight, emptyLeft;
    private boolean vertical, horizontal;

    public AI(String name) {
        super(name);
        board = new String[SIZE][SIZE];
        letters = this.getLetters();
        emptyUp = true;
        emptyDown = true;
        emptyRight= true;
        emptyLeft= true;
        vertical = true;
        horizontal= true;
    }

    public void setGameBoard(GameBoard board) {
        gb = board;
    }


    public void playTurn() {
        System.out.println("PLAY TURN");
        String word;
        board = gb.getStringBoard();
        if (gb.isBoardEmpty()){
            word = makeWord(letters.get(0).toString());
            board[7][7] = String.valueOf(word.toCharArray()[0]);

            gb.checkPlay();
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!board[i][j].equals("_")) {
                    System.out.println(board[i][j]);
                    int count = 0;

                    // Checks which tiles around the letter is empty
                    if (board[i + 1][j].equals("_")) {
                        vertical = true;
                        horizontal = false;
//                        vertical = false;
//                        horizontal = true;
                        emptyDown = true;
                        count++;
                    }
                    if (board[i - 1][j].equals("_")) {
                        vertical = true;
                        horizontal = false;
//                        vertical = false;
//                        horizontal = true;
                        emptyUp = true;
                        count++;
                    }
                    if (board[i][j + 1].equals("_")) {
                        vertical = false;
                        horizontal = true;
//                        horizontal = false;
//                        vertical = true;
                        emptyRight = true;
                        count++;
                    }
                    if (board[i][j - 1].equals("_")) {
                        vertical = false;
                        horizontal = true;
//                        horizontal = false;
//                        vertical = true;
                        emptyLeft = true;
                        count++;
                    }

                    // If there are 3 spaces around the tile
                    if (count == 3) {
                        String temp = board[i][j];

                        temp = makeWord(temp);
                        System.out.println("temp " + temp);
                        if (temp.charAt(0) == board[i][j].charAt(0)) {
                            if (vertical) {
                                board[i][j + 1] = (temp.charAt(1) + "");
                                gb.checkPlay();
                            } else if (horizontal) {
                                board[i + 1][j] = (String.valueOf(temp.toCharArray()[1]));
                                gb.checkPlay();
                            }
                        } else if (temp.toCharArray()[0] != board[i][j].toCharArray()[0]) {
                            if (vertical) {
                                board[i][j - 1] = (String.valueOf(temp.toCharArray()[1]));
                                gb.checkPlay();
                            } else if (horizontal) {
                                board[i - 1][j] = (String.valueOf(temp.toCharArray()[1]));
                                gb.checkPlay();
                            }
                        }
                    }
                }
            }
        }
//        return word;

    }

    private String makeWord(String letter) {

        String word = "";
        //Letters l = new Letters(letter.charAt(0));
        //letters.add(l);
        System.out.println(horizontal);
        System.out.println(vertical);
        System.out.println(emptyDown);
        System.out.println(emptyLeft);
        System.out.println(emptyUp);
        System.out.println(emptyRight);
        String[] words = new String[100];
        for (int i = 0; i < 7; i++) {
            if (horizontal && emptyDown || vertical && emptyRight) {
                word = letter + letters.get(i).getLetter();
                if (gb.checkWord(word)) {
                    System.out.println(word);
                    return word;
                }
            }
            else if (horizontal && emptyUp || vertical && emptyLeft) {
                word = letters.get(i).getLetter() + letter;
                if (gb.checkWord(word)) {
                    System.out.println(word);
                    return word;
                }
            }
            if (gb.isBoardEmpty()) {
                word = letter + letters.get(i).getLetter();
                if (gb.checkWord(word)) {
                    System.out.println(word);
                    return word;
                }
            }

        }
        System.out.println("asdfggfdewedfb " + word);
        return word;
    }
}


