import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class GameBoard {
    private String[][] board;
    private int rows;
    private int cols;
    private boolean isBoardEmpty;

    public GameBoard (int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        isBoardEmpty = true;
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

    public boolean checkSurroundingWords(String place, String word) {

        int row;
        int col;
        int tempRow;
        int tempCol;
        int wordLength;
        String wordToCheck = "";

        if(!isBoardEmpty) {
            wordLength = word.length();
            // Getting the column and row of the placed word
            // Horizontal word
            if (Character.isDigit(place.charAt(0))) {
                row = Character.getNumericValue(place.charAt(0)) - 1;
                col = place.charAt(1) - 'A'; //cols starts at A, so we find the offset
            } else {
                col = place.charAt(0) - 'A';
                row = Character.getNumericValue(place.charAt(1)) - 1;
            }

            // Loops through each letter
            for (int k = 0; k < wordLength - 1; k++) {
                tempRow = row + k;
                tempCol = col + k;

                // Checks if the columns has any attached letters to the letter
                // Checks above the letter
                for (int i = tempRow; i >= 0; i--) {
                    if (board[i][col].equals("_")) {
                        // Checks below the letter
                        for (int j = i + 1; j < rows; j++) {
                            if (!board[j][col].equals("_")) {
                                wordToCheck += board[j][col];
                            }
                        }
                        if (wordToCheck.length() > 1) {
                            if (!checkWord(wordToCheck)) {
                                System.out.println("Invalid placement: " + wordToCheck + " is not a valid word.");
                                return false;
                            }
                        }
                        wordToCheck = "";
                    }
                }

                // Checks if the rows has any attached letters to the letter
                // Checks before the letter
                for (int i = tempCol; i >= 0; i--) {
                    if (board[row][i].equals("_") || i == 0) {

                        // Checks after the letter
                        for (int j = i + 1; j < cols; j++) {
                            if (!board[row][j].equals("_")) {
                                wordToCheck += board[row][j];
                            }
                        }
                        System.out.println(wordToCheck.length());
                        if (wordToCheck.length() > 1) {
                            if (!checkWord(wordToCheck)) {
                                System.out.println("Invalid placement: " + wordToCheck + " is not a valid word.");
                                return false;
                            }
                        }
                        wordToCheck = "";
                    }
                }
            }
        }
        return true;
    }

    public void placeWord (String play) {
        String[][] tempBoard = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            if (cols >= 0) System.arraycopy(board[i], 0, tempBoard[i], 0, cols);
        }
        String word = play.split(" ")[0]; //gets the word
        String place = play.split(" ")[1]; //gets where the word will be placed

        char commonChar = ' ';
        int commonCharIndex = word.indexOf('(');
        //get word and remove brackets
        if (!isBoardEmpty) {
            //find the character between the brackets (to make sure they are placing in the same spot)

            Matcher matcher = Pattern.compile(".*\\((.)\\).*").matcher(word);
            if (matcher.find()) {
                commonChar = matcher.group(1).charAt(0);
            }
            word = word.split("\\(")[0] + commonChar + word.split("\\)")[1];
        }
        int row = 0;
        int col = 0;
        if (checkWord(word)) {
            word = word.toUpperCase();
            place = place.toUpperCase();
            //if first char is a digit then we place horizontally
            if (Character.isDigit(place.charAt(0))) {
                row = Character.getNumericValue(place.charAt(0)) - 1;
                col = place.charAt(1) - 'A'; //cols starts at A, so we find the offset

                if(!isBoardEmpty) {
                    if (!(board[row][col + commonCharIndex].equals(commonChar + ""))) {
                        System.out.println("Invalid Placement.");
                        return;
                    }
                }

                //error check: if the word placement exceeds # of cols, then return
                if (col + word.length() > cols) {
                    System.out.println("This doesn't fit on the board");
                    return;
                }
                for (int i = 0; i < word.length(); i++) {
                    if (board[row][i + col].equals("_")|| (board[row][i + col].equals(word.charAt(i)+""))) {
                        board[row][i + col] = word.charAt(i) + "";
                    } else {
                        System.out.println("This doesn't fit here.");
                        return;
                    }

                }
            } else { //else we place vertically
                col = place.charAt(0) - 'A';
                row = Character.getNumericValue(place.charAt(1)) - 1;

                if(!isBoardEmpty) {
                    if (!(board[row + commonCharIndex][col].equals(commonChar + ""))) {
                        System.out.println("Invalid Placement.");
                        return;
                    }
                }

                //error check: if the word placement exceeds # of rows, then return
                if (row + word.length() > rows) {
                    System.out.println("This doesn't fit on the board");
                    return;
                }

                for (int i = 0; i < word.length(); i++) {
                    if (board[i + row][col].equals("_") || (board[i + row][col].equals(word.charAt(i) + ""))) {
                        board[i + row][col] = word.charAt(i) + "";
                    } else {
                        System.out.println("This doesn't fit here");
                        return;
                    }
                }
            }
            checkSurroundingWords(place, word);
            printBoard();
            isBoardEmpty = false;
        } else {
            System.out.println(word + " is not a valid word.");
        }
    }

    public void unPlaceWord() {}

    private void printBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }


}
