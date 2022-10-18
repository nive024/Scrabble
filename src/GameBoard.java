import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
public class GameBoard {
    private String[][] board;
    private int rows;
    private int cols;

    public GameBoard (int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new String[rows][cols];
        //initialize places in the board
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = "_";
            }
        }
    }

    private boolean checkWord (String word) {
        try {
            HttpURLConnection connection = null;
            URL url = new URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + word);
            connection = (HttpURLConnection) url.openConnection();

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void placeWord (String play) {
        String word = play.split(" ")[0]; //gets the word
        String place = play.split(" ")[1]; //gets where the word will be placed

        int row = 0;
        int col = 0;
        if (checkWord(word)) {
            word = word.toUpperCase();
            place = place.toUpperCase();
            //if first char is a digit then we place horizontally
            if (Character.isDigit(place.charAt(0))) {
                row = Character.getNumericValue(place.charAt(0)) - 1;
                col = place.charAt(1) - 'A'; //cols starts at A, so we find the offset

                //error check: if the word placement exceeds # of cols, then return
                if (col + word.length() > cols) {
                    System.out.println("This doesn't fit on the board");
                    return;
                }
                for (int i = 0; i < word.length(); i++) {
                    board[row][i + col] = word.charAt(i) + "";
                }
            } else { //else we place vertically
                col = place.charAt(0) - 'A';
                row = Character.getNumericValue(place.charAt(1)) - 1;

                //error check: if the word placement exceeds # of rows, then return
                if (row + word.length() > rows) {
                    System.out.println("This doesn't fit on the board");
                    return;
                }

                for (int i = 0; i < word.length(); i++) {
                    board[i + row][col] = word.charAt(i) + "";
                }
            }
            printBoard();
        } else {
            System.out.println(word + " is not a valid word.");
        }
    }

    private void printBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        GameBoard scrabble = new GameBoard(5,5);
        scrabble.placeWord("fire 3A");
        scrabble.placeWord("hide B2");
    }

}
