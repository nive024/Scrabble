import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class GameBoard {
    private String[][] stringBoard; //used for error checking when placing a word
    private Tile[][] tileBoard; //used for displaying the board
    private int rows;
    private int cols;
    private boolean isBoardEmpty;
    private Set<String> wordsOnBoard;

    private Player currentPlayer;

    public GameBoard (int rows, int cols, Player currentPlayer) {
        wordsOnBoard = new HashSet<>();
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

    public boolean checkWord (String word) {
        //testing purposes
        if (word.equals("BE"))
            return true;
        try {
            HttpURLConnection connection = null;
            URL url = new URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + word);
            //We will probably use this API for future milestones since some basic words are missing
            //from the above API, but I haven't gotten the key for it yet.
//            URL url = new URL("https://api.wordnik.com/v4/word.json/" + word + "/definitions?limit=200&includeRelated=false&sourceDictionaries=all&useCanonical=false&includeTags=false&api_key=YOURAPIKEY");

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

    public boolean checkNewWords() {
        int tempRow = 0;
        int tempCol = 0;
        ArrayList<String> tempNewWords = new ArrayList<>();
        //check the row and col of the word that was just added
        String wordToCheck = "";

        //go through the board left to right and look for complete words
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!(stringBoard[i][j]).equals("_")) {
                    wordToCheck += stringBoard[i][j];
                    tempRow = i;
                    tempCol = j;
                } else {
                    if(wordToCheck.length() > 1) { //if it's word longer than 1 letter
                        if (checkWord(wordToCheck)) { //if its a real word
                            wordToCheck += " " + tempRow + (tempCol-wordToCheck.length()+1);
                            tempNewWords.add(wordToCheck); //add to arratList bc we don't know if valid placement or not
                        } else {
                            System.out.println("Invalid placement: " + wordToCheck + " is not a valid word.");
                            for (int k = 0; k < rows; k++) {
                                for (int n = 0; n < cols; n++) {
                                    if (tileBoard[k][n].isEmpty())
                                        stringBoard[k][n] = "_";
                                    else
                                        stringBoard[k][n] = tileBoard[k][n].getLetter().getLetter() + "";
                                }
                            }
                            return false;
                        }
                    }
                    wordToCheck = "";
                }

            }
        }

        //go through the board up to down and look for complete words
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (!(stringBoard[j][i]).equals("_")) {
                    wordToCheck += stringBoard[j][i];
                    tempRow = j;
                    tempCol = i;
                } else {
                    if(wordToCheck.length() > 1) { //if the word is more than 1 letter
                        if (checkWord(wordToCheck)) { //if its an actual word
                            wordToCheck += " " + (tempRow-wordToCheck.length()+1) + tempCol;
//                            System.out.println(wordToCheck);
                            tempNewWords.add(wordToCheck); //add to arratList bc we don't know if valid placement or not
//                            if (wordOnBoard.add(wordToCheck)) { //if it hasnt been added yet
//
//                            }
                        } else {
                            System.out.println("Invalid placement: " + wordToCheck + " is not a valid word.");
                            for (int k = 0; k < rows; k++) {
                                for (int n = 0; n < cols; n++) {
                                    if (tileBoard[k][n].isEmpty())
                                        stringBoard[k][n] = "_";
                                    else
                                        stringBoard[k][n] = tileBoard[k][n].getLetter().getLetter() + "";
                                }
                            }
                            return false;
                        }
                    }
                    wordToCheck = "";
                }

            }
        }
        for (String s: tempNewWords) { //only add the word if the placement of the new word is valid
            if (wordsOnBoard.add(s)) {
                calculateScore(s.split(" ")[0], currentPlayer);
            }
        }
        return true;
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
            boolean isHori = false;
            if (Character.isDigit(place.charAt(0))) {
                isHori = true;
                row = Character.getNumericValue(place.charAt(0)) - 1;
                col = place.charAt(1) - 'A'; //cols starts at A, so we find the offset
            } else {
                col = place.charAt(0) - 'A';
                row = Character.getNumericValue(place.charAt(1)) - 1;
            }

            // Loops through each letter
            for (int k = 0; k < wordLength - 1; k++) {
                if (!isHori) {
                    tempRow = row + k;


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
                                    for (int n = 0; k < rows; k++) {
                                        for (int j = 0; j < cols; j++) {
                                            stringBoard[k][j] = tileBoard[k][j].getLetter().getLetter() + "";
                                        }
                                    }
                                    return false;
                                }
                            }
                            wordToCheck = "";
                        }
                    }
                } else {

                    tempCol = col + k;
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
                            if (wordToCheck.length() > 1) {
                                if (!checkWord(wordToCheck)) {
                                    System.out.println("Invalid placement: " + wordToCheck + " is not a valid word.");
                                    //reset stringBoard back to tileBoard version
                                    for (int n = 0; k < rows; k++) {
                                        for (int j = 0; j < cols; j++) {
                                            stringBoard[k][j] = tileBoard[k][j].getLetter().getLetter() + "";
                                        }
                                    }
                                    return false;
                                }
                            }
                            wordToCheck = "";
                        }
                    }
                }
            }
        }
        return true;
    }

    public void placeWord (String play, Player p) {
        currentPlayer = p;
        int row = 0;
        int col = 0;

        String word = play.split(" ")[0]; //gets the word
        String place = play.split(" ")[1]; //gets where the word will be placed

        char commonChar = ' '; //character that is shared between word being places and existing word
        int commonCharIndex = word.indexOf('('); //index of that char in new word

        //get the index of the row and col where the word will be placed
        if (Character.isDigit(place.charAt(0))) {
            row = Character.getNumericValue(place.charAt(0)) - 1;
            col = place.charAt(1) - 'A'; //cols starts at A, so we find the offset
        } else {
            col = place.charAt(0) - 'A';
            row = Character.getNumericValue(place.charAt(1)) - 1;
        }
        Matcher matcher = Pattern.compile(".*\\((.)\\).*").matcher(word);
        //get word and remove brackets
        if (!isBoardEmpty) {
            //find the character between the brackets (to make sure they are placing in the same spot)
            if (matcher.find()) {
                commonChar = matcher.group(1).toUpperCase().charAt(0);
                if (word.charAt(0) == '(') {
                    word = commonChar + word.split("\\)")[1];
                } else if (word.charAt(word.length()-1) == ')') {
                    word = word.split("\\(")[0] + commonChar;
                } else {
                    word = word.split("\\(")[0] + commonChar + word.split("\\)")[1];
                }
            } else { //they are placing the word adjacent to existing word, need to check if connected to another word
//                boolean isValid = false;
//                try {
//                    if (Character.isDigit(place.charAt(0))) { //if horizontal
//                        for (int i = 0; i < word.length(); i++) {
//                            if (((!(stringBoard[row - 1][col + i]).equals("_") || !(stringBoard[row + 1][col + i]).equals("_")) || !(stringBoard[row][col - 1]).equals("_")) || !(stringBoard[row][col + word.length()]).equals("_")) {
//                                isValid = true;
//                                break;
//                            }
//                        }
//                    } else {
//                        for (int i = 0; i < word.length(); i++) {
//                            if (((!(stringBoard[row + i][col + 1]).equals("_") || !(stringBoard[row + i][col - 1]).equals("_")) || !(stringBoard[row - 1][col]).equals("_")) || !(stringBoard[row + word.length()][col]).equals("_")) {
//                                isValid = true;
//                                break;
//                            }
//                        }
//                    }
//                } catch (ArrayIndexOutOfBoundsException e ) {
//                    if (col == 0) {
//                        if (Character.isDigit(place.charAt(0))) { //horizontal and on first col
//                            for (int i = 0; i < word.length(); i++) {
//                                if (!(stringBoard[row + i][col + 1]).equals("_")) {
//
//                                }
//                            }
//                        }
//                    }
//                }
//                if (!isValid){
//                    System.out.println("Invalid placement, floating word");
//                    return;
//                }
            }

        }

        if (checkWord(word)) {
            word = word.toUpperCase();
            place = place.toUpperCase();
            //if first char is a digit then we place horizontally
            if (Character.isDigit(place.charAt(0))) {
                //error check: if the word placement exceeds # of cols, then return
                if (col + word.length() > cols) {
                    System.out.println("This doesn't fit on the board");
                    return;
                }

                //check to see if the overlapping letter is in the right spot
                if(!isBoardEmpty && matcher.find()) {
                    if (!(stringBoard[row][col + commonCharIndex].equals(commonChar + ""))) {
                        System.out.println("Invalid Placement.");
                        return;
                    }
                }

                for (int i = 0; i < word.length(); i++) {
                    if (stringBoard[row][i + col].equals("_")|| (stringBoard[row][i + col].equals(word.charAt(i)+""))) {
                        stringBoard[row][i + col] = word.charAt(i) + "";
                    } else {
                        System.out.println("This doesn't fit here.");
                        return;
                    }

                }
                if (checkNewWords()) {
                    for (int i = 0; i < word.length(); i++) {
                        tileBoard[row][i + col].placeLetter(new Letters(stringBoard[row][i + col].toUpperCase().charAt(0)));
                    }
                }
            } else { //else we place vertically
                //error check: if the word placement exceeds # of rows, then return
                if (row + word.length() > rows) {
                    System.out.println("This doesn't fit on the board");
                    return;
                }

                //check to see if the overlapping letter is in the right spot
                if(!isBoardEmpty && matcher.find()) {
                    if (!(stringBoard[row + commonCharIndex][col].equals(commonChar + ""))) {
                        System.out.println(stringBoard[row + commonCharIndex][col] + " " + commonChar);
                        System.out.println("Invalid Placement.");
                        return;
                    }
                }

                for (int i = 0; i < word.length(); i++) {
                    if (stringBoard[i + row][col].equals("_") || (stringBoard[i + row][col].equals(word.charAt(i) + ""))) {
                        stringBoard[i + row][col] = word.charAt(i) + "";
                    } else {
                        System.out.println("This doesn't fit here");
                        return;
                    }
                }
                if (checkNewWords()) {
                    for (int i = 0; i < word.length(); i++) {
                        tileBoard[i + row][col].placeLetter(new Letters(stringBoard[i + row][col].toUpperCase().charAt(0)));
                    }
                }
            }
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
        System.out.println("Yay! You scored " + score + " points for " + word);
        System.out.println("Your total score is now: " + p.getScore());

        return score;
    }


}
