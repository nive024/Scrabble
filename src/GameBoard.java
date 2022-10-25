import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class GameBoard {
    private String[][] stringBoard; //used for error checking when placing a word
    private Tile[][] tileBoard; //used for displaying the board
    private int rows;
    private int cols;
    private boolean isBoardEmpty;

    private Player currentPlayer;

    public GameBoard (int rows, int cols, Player currentPlayer) {
        this.rows = rows;
        this.cols = cols;
        isBoardEmpty = true;
        this.currentPlayer = currentPlayer;
        stringBoard = new String[rows][cols];
        tileBoard = new Tile[rows][cols];
        //initialize places in the board
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                stringBoard[i][j] = "_";
            }
        }

        //initialize new tiles on the board
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                tileBoard[i][j] = new Tile();
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
                    if (stringBoard[i][col].equals("_")) {
                        // Checks below the letter
                        for (int j = i + 1; j < rows; j++) {
                            if (!stringBoard[j][col].equals("_")) {
                                wordToCheck += stringBoard[j][col];
                            }
                        }
                        if (wordToCheck.length() > 1) {
                            if (!checkWord(wordToCheck)) {
                                System.out.println("Invalid placement: " + wordToCheck + " is not a valid word.");
                                return false;
                            }
                            else{calculateScore(wordToCheck, currentPlayer);}
                        }
                        wordToCheck = "";
                    }
                }

                // Checks if the rows has any attached letters to the letter
                // Checks before the letter
                for (int i = tempCol; i >= 0; i--) {
                    if (stringBoard[row][i].equals("_") || i == 0) {

                        // Checks after the letter
                        for (int j = i + 1; j < cols; j++) {
                            if (!stringBoard[row][j].equals("_")) {
                                wordToCheck += stringBoard[row][j];
                            }
                        }
                        System.out.println(wordToCheck.length());
                        if (wordToCheck.length() > 1) {
                            if (!checkWord(wordToCheck)) {
                                System.out.println("Invalid placement: " + wordToCheck + " is not a valid word.");
                                //reset stringBoard back to tileBoard version
                                for (int n = 0; k < rows; k++) {
                                    for (int j = 0; j < cols; j++) {
                                        stringBoard[k][j] = tileBoard[k][j].getLetter().getLetter()+"";
                                    }
                                }
                                return false;
                            }
                            else{
                                calculateScore(wordToCheck, currentPlayer);
                            }
                        }
                        wordToCheck = "";
                    }
                }
            }
        }
        return true;
    }

    public void placeWord (String play, Player p) {
        currentPlayer = p;

        String word = play.split(" ")[0]; //gets the word
        String place = play.split(" ")[1]; //gets where the word will be placed

        char commonChar = ' '; //character that is shared between word being places and existing word
        int commonCharIndex = word.indexOf('('); //index of that char in new word
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

                //check to see if the overlapping letter is in the right spot
                if(!isBoardEmpty) {
                    if (!(stringBoard[row][col + commonCharIndex].equals(commonChar + ""))) {
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
                    if (stringBoard[row][i + col].equals("_")|| (stringBoard[row][i + col].equals(word.charAt(i)+""))) {
                        stringBoard[row][i + col] = word.charAt(i) + "";
                    } else {
                        System.out.println("This doesn't fit here.");
                        return;
                    }

                }
                if (checkSurroundingWords(place, word)) {
                    for (int i = 0; i < word.length(); i++) {
                        tileBoard[row][i + col].placeLetter(new Letters(stringBoard[row][i + col].toUpperCase().charAt(0)));
                    }
                }
            } else { //else we place vertically
                col = place.charAt(0) - 'A';
                row = Character.getNumericValue(place.charAt(1)) - 1;

                //check to see if the overlapping letter is in the right spot
                if(!isBoardEmpty) {
                    if (!(stringBoard[row + commonCharIndex][col].equals(commonChar + ""))) {
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
                    if (stringBoard[i + row][col].equals("_") || (stringBoard[i + row][col].equals(word.charAt(i) + ""))) {
                        stringBoard[i + row][col] = word.charAt(i) + "";
                    } else {
                        System.out.println("This doesn't fit here");
                        return;
                    }
                }
                if (checkSurroundingWords(place, word)) {
                    for (int i = 0; i < word.length(); i++) {
                        tileBoard[i + row][col].placeLetter(new Letters(stringBoard[i + row][col].toUpperCase().charAt(0)));
                    }
                }
            }

            calculateScore(word, currentPlayer);
            printBoard();
            isBoardEmpty = false;
        } else {
            System.out.println(word + " is not a valid word.");
        }
    }


    private void printBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Tile t = tileBoard[i][j];
                if (t.isEmpty())
                    System.out.print("_ ");
                else
                    System.out.print(t.getLetter().getLetter() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    private int calculateScore(String word, Player p){
        int score=0;
        int i = 0;

        ArrayList<Letters> letter = new ArrayList<>();

        while(i<word.length()){
            Letters nl = new Letters(word.charAt(i));
            letter.add(nl);
            score += nl.getPointValue(nl.getLetter());
            i++;
        }

        p.setScore(score);
        System.out.println("Yay! You scored " + score + " points for that word.");
        System.out.println("Your total score is now: " + p.getScore());

        return score;
    }


}
