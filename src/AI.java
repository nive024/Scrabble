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
        String word;
        board = gb.getStringBoard();
        if (gb.isBoardEmpty()){
            word = makeWord(letters.get(0).toString(), "");
            board[7][7] = String.valueOf(word.toCharArray()[0]);
            if (gb.checkPlay()) {
                gb.setStringBoard(board);
            }
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!board[i][j].equals("_")) {
                    int count = 0;

                    // Checks which tiles around the letter is empty
                    if (board[i + 1][j].equals("_")) {
                        if(board[i + 1][j].equals("_") && board[i - 1][j].equals("_")){
                            vertical = false;
                            horizontal = true;
                        }
                        emptyDown = true;
                        count++;
                    }
                    if (board[i - 1][j].equals("_")) {
                        if(board[i + 1][j].equals("_") && board[i - 1][j].equals("_")){
                            vertical = false;
                            horizontal = true;
                        }
                        emptyUp = true;
                        count++;
                    }
                    if (board[i][j + 1].equals("_")) {
                        if(board[i][j + 1].equals("_") && board[i][j - 1].equals("_")){
                            vertical = true;
                            horizontal = false;
                        }
                        emptyRight = true;
                        count++;
                    }
                    if (board[i][j - 1].equals("_")) {
                        if(board[i][j + 1].equals("_") && board[i][j - 1].equals("_")){
                            vertical = true;
                            horizontal = false;
                        }
                        emptyLeft = true;
                        count++;
                    }

                    // If there are 3 spaces around the tile
                    if (count == 3) {
                        String temp = board[i][j];

                        temp = makeWord(temp, "");
                        if (temp.charAt(0) == board[i][j].charAt(0)) {
                            if (vertical) {
                                board[i][j + 1] = (temp.charAt(1) + "");
                                if (validWord()){
                                    if (checkNewLetter(i, j+1) != 3){
                                        temp = makeWord(board[i][j], temp);
                                    }
                                    return;
                                }
                            } else if (horizontal) {
                                board[i + 1][j] = (String.valueOf(temp.toCharArray()[1]));

                                if (validWord()){
                                    if (checkNewLetter(i+1, j) != 3){
                                        temp = makeWord(board[i][j], temp);
                                    }
                                    //gb.setTileBoard(board);
                                    return;
                                }
                            }
                        } else if (temp.toCharArray()[0] != board[i][j].toCharArray()[0]) {
                            if (vertical) {
                                board[i][j - 1] = (String.valueOf(temp.toCharArray()[1]));
                                if (validWord()){
                                    if (checkNewLetter(i, j-1) != 3){
                                        temp = makeWord(board[i][j], temp);
                                    }
                                    return;
                                }
                            } else if (horizontal) {
                                board[i - 1][j] = (String.valueOf(temp.toCharArray()[1]));
                                if (validWord()){
                                    if (checkNewLetter(i-1, j) != 3){
                                        temp = makeWord(board[i][j], temp);
                                    }
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean validWord() {
        if (gb.checkPlay()) {
            gb.setStringBoard(board);
            for (ScrabbleView view: gb.getViews()){
                view.updateBoard(board);
            }
            return true;
        }
        return false;
    }

    private int checkNewLetter(int i, int j){
        int count = 0;
        if (!board[i][j].equals("_")) {count++;}
        if (board[i - 1][j].equals("_")) {count++;}
        if (board[i][j + 1].equals("_")) {count++;}
        if (board[i][j - 1].equals("_")) {count++;}

        return count;
    }



    private String makeWord(String letter, String invalidWord) {

        String word = "";
        String[] words = new String[100];
        for (int i = 0; i < 7; i++) {
            if (horizontal && emptyDown || vertical && emptyRight) {
                word = letter + letters.get(i).getLetter();
                if (gb.checkWord(word)) {
                    gb.setStringBoard(board);
                    return word;
                }
            }
            else if (horizontal && emptyUp || vertical && emptyLeft) {
                word = letters.get(i).getLetter() + letter;
                if (gb.checkWord(word)) {
                    gb.setStringBoard(board);
                    return word;
                }
            }
            if (gb.isBoardEmpty()) {
                word = letter + letters.get(i).getLetter();
                if (gb.checkWord(word)) {
                    gb.setStringBoard(board);
                    return word;
                }
            }
        }
        if (word.equals("")) {
            gb.turnSkipped();
            gb.getNextPlayer();
        }
        return word;
    }

    public ArrayList<Letters> getLetter(){
        return letters;
    }
}


