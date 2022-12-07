import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * The GameBoard class represents the game board logic in Scrabble, it is the model in the MVC structure
 *
 * This class checks the validity of the word, places words on the board,
 * adds up scores to the players’ total, prints the current state of the board to the console
 * and checks the validity of the placement of the word.
 *
 * @author Nivetha Sivasaravanan
 * @author Nicole Lim
 * @author Rimsha Atif
 */
public class GameBoard {
    private String[][] stringBoard; //used for error checking when placing a word
    private Tile[][] tileBoard; //used for displaying the board
    private int rows;
    private int cols;
    private boolean isBoardEmpty;
    private Set<String> wordsOnBoard;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private BagOfLetters bagOfLetters;
    private List<ScrabbleView> views;
    private String wordToCheck;
    private ArrayList<String> wordsAddedThisTurn;
    private int numSkips;
    private ArrayList<String> tilesChangedThisTurn;
    private int currentScore;

    private int numBots;
    /**
     * Constructor to initialize the game board with the specified columns and rows. Also initializes the first player.
     *
     * @param rows The number of rows on the board
     * @param cols The number of rows on the board
     */
    public GameBoard (int rows, int cols) {
        views = new ArrayList<>();
        wordsOnBoard = new HashSet<>();
        wordsAddedThisTurn = new ArrayList<>();
        this.rows = rows;
        this.cols = cols;
        currentScore = 0;
        numSkips = 0;
        isBoardEmpty = true;
        this.bagOfLetters = new BagOfLetters();
        tilesChangedThisTurn = new ArrayList<>();

        stringBoard = new String[rows][cols];
        tileBoard = new Tile[rows][cols];
        //initialize places in the board
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                stringBoard[i][j] = "_";
            }
        }

//        //initialize new tiles on the board
//        //Triple Word Score (redTile())
//        tileBoard[0][0] = new Tile(3, true);
//        tileBoard[0][14] = new Tile(3, true);
//        tileBoard[14][0] = new Tile(3, true);
//        tileBoard[14][14] = new Tile(3, true);
//        tileBoard[14][7] = new Tile(3, true);
//        tileBoard[7][14] = new Tile(3, true);
//        tileBoard[0][7] = new Tile(3, true);
//        tileBoard[7][0] = new Tile(3, true);
//        //Triple Letter Score (blueTile())
//        tileBoard[1][5] = new Tile(3, false);
//        tileBoard[1][9] = new Tile(3, false);
//        tileBoard[5][1] = new Tile(3, false);
//        tileBoard[9][1] = new Tile(3, false);
//        tileBoard[5][5] = new Tile(3, false);
//        tileBoard[5][9] = new Tile(3, false);
//        tileBoard[9][5] = new Tile(3, false);
//        tileBoard[9][9] = new Tile(3, false);
//        tileBoard[13][5] = new Tile(3, false);
//        tileBoard[13][9] = new Tile(3, false);
//        tileBoard[5][13] = new Tile(3, false);
//        tileBoard[9][13] = new Tile(3, false);
//        //Double Letter Score (magentaTile())
//        tileBoard[0][3] = new Tile(2, false);
//        tileBoard[0][11] = new Tile(2, false);
//        tileBoard[2][6] = new Tile(2, false);
//        tileBoard[2][8] = new Tile(2, false);
//        tileBoard[3][0] = new Tile(2, false);
//        tileBoard[3][7] = new Tile(2, false);
//        tileBoard[3][14] = new Tile(2, false);
//        tileBoard[6][2] = new Tile(2, false);
//        tileBoard[6][6] = new Tile(2, false);
//        tileBoard[6][8] = new Tile(2, false);
//        tileBoard[6][12] = new Tile(2, false);
//        tileBoard[7][3] = new Tile(2, false);
//        tileBoard[7][11] = new Tile(2, false);
//        tileBoard[8][2] = new Tile(2, false);
//        tileBoard[8][6] = new Tile(2, false);
//        tileBoard[8][8] = new Tile(2, false);
//        tileBoard[8][12] = new Tile(2, false);
//        tileBoard[11][0] = new Tile(2, false);
//        tileBoard[11][7] = new Tile(2, false);
//        tileBoard[11][14] = new Tile(2, false);
//        tileBoard[12][6] = new Tile(2, false);
//        tileBoard[12][8] = new Tile(2, false);
//        tileBoard[14][3] = new Tile(2, false);
//        tileBoard[14][11] = new Tile(2, false);
//        //Double Word Score (pinkTile())
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                if (tileBoard[i][j] == null) {
//                    if (i == j){
//                        tileBoard[i][j] = new Tile(2, true);
//                    }
//                }
//            }
//        }
//        int col = cols - 1;
//        for (int i = 0; i < rows; i++) {
//                if (tileBoard[i][col] == null) {
//                    tileBoard[i][col] = new Tile(2, true);
//                }
//                col--;
//        }
//        //non special tiles
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (tileBoard[i][j] == null) { // line added
                    tileBoard[i][j] = new Tile();
                }
            }
        }
    }

    public Tile[][] getTileBoard() {
        return tileBoard;
    }

    /**
     * Sets the current player
     * @param currentPlayer the current player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Gets the current player whose turn it is
     * @return The player whose turn it is
     */
    public Player getCurrentPlayer(){
        return this.currentPlayer;
    }

    /**
     * Gets the players arraylist
     * @return players; The arraylist of all the players
     */
    public ArrayList<Player> getPlayers(){
        return players;
    }
    /**
     * Add view to model for MVC structure
     * @param view the view that will be added to this model
     */
    public void addView(ScrabbleView view) {
        views.add(view);
    }

    /**
     * Check validity of the word by comparing it with the API
     * @param word the word entered by the player
     * @return true if the word is valid; otherwise false
     */
    public boolean checkWord (String word) {
        //testing purposes
        if ((word.equals("BE")) || (word.equals("IS") || (word.equals("WAS"))))
            return true;
        try {
            HttpURLConnection connection;
            URL url = new URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + word.toLowerCase());
            //URL url = new URL("https://api.wordnik.com/v4/word.json/" + word.toLowerCase() + "/definitions?limit=200&includeRelated=false&sourceDictionaries=all&useCanonical=false&includeTags=false&api_key=k5mz36509sb4q1eyagr8gq1juoxjfpwjt1gki6kcxo13p30p5");
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

    /**
     * Check the surrounding words (if any) that are created when the player makes a move
     * @return true, if the surrounding words are valid otherwise false
     */
    public boolean checkNewWords() {
        wordsAddedThisTurn.clear();

        ArrayList<String> tempNewWords = new ArrayList<>();
        //check the row and col of the word that was just added
        wordToCheck = "";
        String place = "";
        //go through the board left to right and look for complete words
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!(stringBoard[i][j]).equals("_")) {
                    wordToCheck += stringBoard[i][j];
                    tilesChangedThisTurn.add(i + " " + j);
                    if (place.isEmpty()) {
                        place += (1+i);
                        place += (char)('A'+j);
                    }
                } else {

                    if(wordToCheck.length() > 1) { //if it's word longer than 1 letter
                        if (checkWord(wordToCheck)) { //if it's a real word
                            wordToCheck += " " + place;
                            if ((!isFloating(wordToCheck.split(" ")[0], place)) || (isBoardEmpty)) {
                                if (wordsOnBoard.add(wordToCheck)) {
                                    currentScore += calculateScore(wordToCheck.split(" ")[0]);
                                    wordsAddedThisTurn.add(wordToCheck);
                                }
                                tempNewWords.add(wordToCheck); //add to arrayList bc we don't know if valid placement or not
                            }
                        } else {
                            System.out.println("Invalid placement: " + wordToCheck + " is not a valid word.");
                            revertStringBoard();
                            return false;
                        }
                    } else if (wordToCheck.length() == 1) {
                        if (isFloating(wordToCheck, place)) {
                            System.out.println(wordToCheck.toUpperCase() + " is floating, invalid play");
                            for (ScrabbleView v : views) {
                                v.enableUsedPlayerButtons(players.indexOf(currentPlayer));
                                v.enableGridButtons();
                                v.displayErrorMessage(wordToCheck, "floating");
                            }
                            revertStringBoard();
                            return true;
                        }
                    }
                    wordToCheck = "";
                    tilesChangedThisTurn.clear();
                    place = "";

                }

            }
        }

        place="";
        //go through the board up to down and look for complete words
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (!(stringBoard[j][i]).equals("_")) {
                    wordToCheck += stringBoard[j][i];
                    tilesChangedThisTurn.add(j + " " + i);
                    if (place.isEmpty()) {
                        place += (char)('A'+i);
                        place += (j+1);
                    }
                } else {
                    if(wordToCheck.length() > 1) { //if the word is more than 1 letter
                        if (checkWord(wordToCheck)) { //if it's an actual word
                            wordToCheck += " " + place;
                            if ((!isFloating(wordToCheck.split(" ")[0], place)) || (isBoardEmpty)) {
                                if (wordsOnBoard.add(wordToCheck)) {
                                    currentScore += calculateScore(wordToCheck.split(" ")[0]);
                                    System.out.println("current score " + calculateScore(wordToCheck.split(" ")[0]));
                                    wordsAddedThisTurn.add(wordToCheck);
                                }
                                tempNewWords.add(wordToCheck); //add to arrayList bc we don't know if valid placement or not

                            }
                        } else {
                            System.out.println("Invalid placement: " + wordToCheck + " is not a valid word.");
                            revertStringBoard();
                            return false;
                        }
                    } else if (wordToCheck.length() == 1) {
                        if (isFloating(wordToCheck, place)) {
                            System.out.println(wordToCheck.toUpperCase() + " is floating, invalid play");
                            for (ScrabbleView v : views) {
                                v.enableUsedPlayerButtons(players.indexOf(currentPlayer));
                                v.enableGridButtons();
                                v.displayErrorMessage(wordToCheck, "floating");
                            }
                            revertStringBoard();
                            return true;
                        }
                    }
                    wordToCheck = "";
                    tilesChangedThisTurn.clear();
                    place="";
                }

            }
        }
//        for (String s: tempNewWords) { //only add the word if the placement of the new word is valid
//            if (wordsOnBoard.add(s)) {
//                String word = s.split(" ")[0];
//                place = s.split(" ")[1];
//
//                if (word.length() == 1) {
//
//                } else {
//                    //calcualte score
//                    currentScore += calculateScore(s);
//                    wordsAddedThisTurn.add(s);
//                }
//
//            }
//        }
        return true;
    }

    /**
     * This method returns the StringBoard to its previous valid state
     */
    private void revertStringBoard() {
        for (int k = 0; k < rows; k++) {
            for (int n = 0; n < cols; n++) {
                if (tileBoard[k][n].isEmpty()) {
                    stringBoard[k][n] = "_";
                } else
                    stringBoard[k][n] = tileBoard[k][n].getLetter().getLetter() + "";
                }
            }
        }
//        System.out.println("revert");
//        printGameStatus();


    /**
     * Place the word entered by the player p on the board
     * @param play String containing the word and its placement
     */
    public void placeWord (String play) {
        String word = play.split(" ")[0]; //gets the word
        String place = play.split(" ")[1]; //gets where the word will be placed

        int row = getRowAndCol(place)[0];
        int col = getRowAndCol(place)[1];

        word = word.toUpperCase();
        if (isHorizontal(place)) {
            for (int i = 0; i < word.length(); i++) {
                if (stringBoard[row][i + col].equals("_") || (stringBoard[row][i + col].equals(word.charAt(i) + ""))) {
                    stringBoard[row][i + col] = word.charAt(i) + "";
                    tilesChangedThisTurn.add(row + " " + (i+col));
                }
            }
        } else {
            for (int i = 0; i < word.length(); i++) {
                if (stringBoard[i + row][col].equals("_") || (stringBoard[i + row][col].equals(word.charAt(i) + ""))) {
                    stringBoard[i + row][col] = word.charAt(i) + "";
                    String place2 = (i+row) + " " + col;
                    tilesChangedThisTurn.add(place2);
                }
            }
        }
        checkPlay();
    }

    /**
     * This method checks the validity of the move that was made. It checks the validaty of the word.
     * As well as that the word was placed on the center tile for the first move.
     * @return true if the play is valid, false otherwise
     */
    public boolean checkPlay() {
        currentScore = 0;
        if (checkNewWords()) {
            for (String play: wordsAddedThisTurn) {

                String word = play.split(" ")[0];
                String place = play.split(" ")[1];
                //if this is the first word played, check to see if it's on the center square
                if (isBoardEmpty) {
                    if (!checkCenterSquare()) {
                        for (ScrabbleView v : views) {
                            v.enableUsedPlayerButtons(players.indexOf(currentPlayer));
                            v.enableGridButtons();
                            v.displayErrorMessage(word, "center");
                        }
                        revertStringBoard();
                        wordsOnBoard.clear();
                        return false;
                    }
                }

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        tileBoard[i][j].placeLetter(new Letters(stringBoard[i][j].toUpperCase().charAt(0)));
                    }
                }

                this.currentPlayer.setScore(currentScore);

                deal(players.indexOf(currentPlayer));
                for (ScrabbleView v : views) {
                    v.saveGridStatus();
                    v.updateScore(currentPlayer.getScore(), players.indexOf(currentPlayer));
                }
                isBoardEmpty = false;

            }
        } else {
            for (ScrabbleView v : views) {
                v.enableUsedPlayerButtons(players.indexOf(currentPlayer));
                v.enableGridButtons();
                v.displayErrorMessage(wordToCheck, "iv");
            }
            revertStringBoard();
            return false;
        }

        printGameStatus();
        getNextPlayer();
        numSkips = 0;
        return true;
    }
    /**
     * Adds a letter on the GUI
     * @param letter the letter to add on the GUI
     * @param place the place where to add it
     */
    public void addLetter(String letter, String place) {
        for (ScrabbleView view: views) {
            view.update(letter, place);
        }
        int col = place.toUpperCase().charAt(0) - 'A';
        int row = place.toUpperCase().charAt(1) - 'A';
        stringBoard[row][col] = letter;
        tilesChangedThisTurn.add(row + " " + col);
        currentPlayer.removeLetter(new Letters(letter.charAt(0)));
    }

    /**
     * Checks to see if the new word placed is connected to another word on the board
     * @param word the new word being placed
     * @param place the place of the first letter of the new word
     * @return true if the word is floating, false otherwise
     */
    public boolean isFloating(String word, String place) {
        int row, col;
        boolean horizontal = isHorizontal(place);

        row = getRowAndCol(place)[0];
        col = getRowAndCol(place)[1];

        if (word.length() == 1) {
            int emptySpace = 0; //how many empty spaces there are around the letter
            int totalEmptySpace = 4; //how many empty spaces there would be if floating. decremented if edge case.
            //if top is empty for single char, then its floating
            try {
                emptySpace = (stringBoard[row - 1][col].equals("_")) ? emptySpace + 1 : emptySpace;
            } catch (ArrayIndexOutOfBoundsException e) {totalEmptySpace--;}

            //if bottom is empty for single char, then its floating
            try {
                emptySpace = (stringBoard[row + 1][col].equals("_")) ? emptySpace + 1 : emptySpace;
            } catch (ArrayIndexOutOfBoundsException ignored) {totalEmptySpace--;}

            //if left is empty for single char, then its floating
            try {
                emptySpace = (stringBoard[row][col - 1].equals("_")) ? emptySpace + 1 : emptySpace;
            } catch (ArrayIndexOutOfBoundsException ignored) {totalEmptySpace--;}

            //if right is empty for single char, then its floating
            try {
                emptySpace = (stringBoard[row][col + 1].equals("_")) ? emptySpace + 1 : emptySpace;
            } catch (ArrayIndexOutOfBoundsException ignored) {totalEmptySpace--;}

            return emptySpace == totalEmptySpace; //if 4 empty spaces, then floating
        } else {
            if (horizontal) {
                for (int i = 0; i < word.length(); i++) { //for each letter, check above below
                    //check space above the word
                    try {
                        if (!(stringBoard[row - 1][col+i].equals("_")))
                            return false;
                    } catch (ArrayIndexOutOfBoundsException ignored) {}

                    //check space below the word
                    try {
                        if (!(stringBoard[row + 1][col+i].equals("_"))) //if there is a letter, automatically return false
                            return false;
                    } catch (ArrayIndexOutOfBoundsException ignored) {}

                    //check beside first letter
                    try {
                        if (!(stringBoard[row][col-1].equals("_"))) //if there is a letter, automatically return false
                            return false;
                    } catch (ArrayIndexOutOfBoundsException ignored) {}

                    //check beside last letter
                    try {
                        if (!(stringBoard[row][col+word.length()].equals("_"))) //if there is a letter, automatically return false
                            return false;
                    } catch (ArrayIndexOutOfBoundsException ignored) {}
                }
            } else { //if vertical
                for (int i = 0; i < word.length(); i++) { //for each letter, check above below
                    //check space to the left of word
                    try {
                        if (!(stringBoard[row+i][col-1].equals("_")))
                            return false;
                    } catch (ArrayIndexOutOfBoundsException ignored) {}

                    //check space to the right of word
                    try {
                        if (!(stringBoard[row+i][col+1].equals("_"))) //if there is a letter, automatically return false
                            return false;
                    } catch (ArrayIndexOutOfBoundsException ignored) {}

                    //check above top letter
                    try {
                        if (!(stringBoard[row-1][col].equals("_"))) //if there is a letter, automatically return false
                            return false;
                    } catch (ArrayIndexOutOfBoundsException ignored) {}

                    //check below bottom letter
                    try {
                        if (!(stringBoard[row+word.length()][col].equals("_"))) //if there is a letter, automatically return false
                            return false;
                    } catch (ArrayIndexOutOfBoundsException ignored) {}
                }
            }
        }

        return true;
    }

    /**
     * Check to see if the first word is being placed in the center square
     * @return true if the word is on the center square, false otherwise
     */
    public boolean checkCenterSquare(){
        int centerRow = (rows/2);
        int centerCol = (cols/2);

        if (stringBoard[centerRow][centerCol].equals("_")) {
            System.out.println("The first word must be placed on the center square");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Calculates the score of the player after their turn
     * @param word the word they entered
     * @return the integer value of the score of the word
     */
    private int calculateScore(String word){
        int score = 0;
        int wordMultiplier = 1;

        //int i = 0;
        for(String t: tilesChangedThisTurn){
            String[] coordinates = t.split(" ");
            int row = Integer.parseInt(coordinates[0]);
            int col = Integer.parseInt(coordinates[1]);
            Tile tile = tileBoard[row][col];
            score += tile.getTileScore(stringBoard[row][col].charAt(0));
            if (tile.isWordPointMultiplier()){
                wordMultiplier *= tile.getPointMultiplier();
            }
            tile.tileMultiplierUsed();
        }

        //if it's the first word being placed, then they should get double the points
        System.out.println("Yay! You scored " + (score * wordMultiplier) + " points for " + word);
        return score * wordMultiplier;
    }

    /**
     * This method deals an ArrayList of random Letters. If the currentLetter already contains some letters, 7- the number of current letters are dealt
     * Otherwise, 7 random letters are dealt
     * @Param playerNumber the index of the current player
     * @return the String representation of all the new letters
     */
    public String deal(int playerNumber){
        Random r = new Random();
        Object[] keys = bagOfLetters.getBag().keySet().toArray();
        String s= "";

        Player player = players.get(playerNumber);
        int amountToDeal = (7 - player.getLetters().size());

        if (keys.length > 0) {
            //randomly deal n new letters to the player
            for (int i = 0; i < amountToDeal; i++) {
                Letters newLetter = (Letters) keys[r.nextInt(keys.length)];

                while (!bagOfLetters.inBag(newLetter)) {
                    newLetter = (Letters) keys[r.nextInt(keys.length)];
                }
                s += newLetter.getLetter();
                player.addLetter(newLetter);
            }
        } else {
            Set<Integer> numLetters = new HashSet<>();
            for (Player p: players) {
                numLetters.add(p.getLetters().size());
            }
            if ((numLetters.size() == 1) && (numLetters.contains(0))){
                endGame();
            }
        }
        for (ScrabbleView view: views) {
            view.updatePlayersLetters(s, playerNumber);
        }

        return s;
    }

    /**
     * This method prints the board after the word is placed on it. It also prints the total score of the currentPlayer.
     */
    public void printGameStatus(){
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

        System.out.println(currentPlayer.getName() + " Your total score is now: " + currentPlayer.getScore());

        String s = "";
        for(Letters l: currentPlayer.getLetters()){
            s += l.getLetter() + ", ";
        }

    }

    /**
     * Initializes the Player objects based on the number of players selected
     * @param numPlayers the number of players playing
     */
    public void addPlayers(int numPlayers, int bots) {
        players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            Player p = new Player("Player " + (i+1));
            players.add(p);
        }
        for (int i = 0; i < bots; i++){
            AI p = new AI("Bot");
            p.setGameBoard(this);
            players.add(p);
        }
        currentPlayer = players.get(0);
        for (ScrabbleView view: views) {
            view.enableGameComponents(true);
            view.enableChooseNumPlayerComponents(false);
        }
    }




    /**
     * Check to see if the board is empty
     * @return true if the board is empty false otherwise
     */
    public boolean isBoardEmpty(){
        for(int i=0; i<rows; i++){
            for(int j =0; j<cols; j++){
                if(!tileBoard[i][j].isEmpty()){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the string version of the board
     * @return the string version of the board
     */
    public String[][] getStringBoard(){
        return stringBoard;
    }

    /**
     * Returns the coordinates (row and col) of the word being placed
     * @param place the place of the letter/word being placed
     * @return an array containing the row and col
     */
    public int[] getRowAndCol (String place) {
        int row, col;
        if (isHorizontal(place)) { //horizontal means number first 10H
            if (place.length() == 3) {
                row = Integer.parseInt(place.substring(0,2))-1;
                col = place.toUpperCase().charAt(2) - 'A'; //cols starts at A, so we find the offset
            } else {
                row = Character.getNumericValue(place.charAt(0)) - 1;
                col = place.toUpperCase().charAt(1) - 'A'; //cols starts at A, so we find the offset
            }

        } else { //vertical means letter first H10
            col = place.toUpperCase().charAt(0) - 'A';
            if (place.length() == 3) {
                row = Integer.parseInt(place.substring(1))-1;
            } else {
                row = Character.getNumericValue(place.charAt(1)) - 1;
            }
        }
        return new int[] {row, col};
    }

    /**
     * Returns true if the word is horizontal and false otherwise
     * @param place the place of the first letter of the word
     * @return true if the word is horizontal and false otherwise
     */
    public boolean isHorizontal(String place) {
        return Character.isDigit(place.charAt(0));
    }

    /**
     * Sets the current player to the next player in the ArrayList of players and then tells the
     * view to disable the buttons of all the other players
     */
    public void getNextPlayer() {
        int currentIndex = (players.indexOf(currentPlayer) + 1 ) % players.size();
        currentPlayer = players.get(currentIndex);
        for (ScrabbleView view: views) {
            view.disableOtherPlayers(players.indexOf(currentPlayer));
        }
    }

    /**
     * Checks how many times the skip button was clicked. If all players skip they're turn then the game
     * will end
     */
    public void turnSkipped() {
        numSkips += 1;
        if (numSkips % players.size() == 0) {
            endGame();
        }
    }

    /**
     * Get the views
     * @return the views
     */
    public List<ScrabbleView> getViews() {
        return views;
    }

    /**
     * ends the game
     */
    public void endGame() {
        for (ScrabbleView view: views) {
            Collections.sort(players);
            view.endGame(players);
        }
    }

    public void setStringBoard(String[][] stringBoard) {
        this.stringBoard = stringBoard;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                tileBoard[i][j].placeLetter(new Letters(stringBoard[i][j].toUpperCase().charAt(0)));
            }
        }
    }

    public void setTileBoard(boolean custom, String filename) {
        if (custom) {
            //first initialize to all normal tiles
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (tileBoard[i][j] == null) { // line added
                        tileBoard[i][j] = new Tile();
                    }
                }
            }
            File f = new File(filename);
            try {
                constructCustomBoard(f);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (ScrabbleView view: views) {
                view.setCustomBoard();
            }
        } else {
            //initialize new tiles on the board
            //Triple Word Score (redTile())
            tileBoard[0][0] = new Tile(3, true);
            tileBoard[0][14] = new Tile(3, true);
            tileBoard[14][0] = new Tile(3, true);
            tileBoard[14][14] = new Tile(3, true);
            tileBoard[14][7] = new Tile(3, true);
            tileBoard[7][14] = new Tile(3, true);
            tileBoard[0][7] = new Tile(3, true);
            tileBoard[7][0] = new Tile(3, true);
            //Triple Letter Score (blueTile())
            tileBoard[1][5] = new Tile(3, false);
            tileBoard[1][9] = new Tile(3, false);
            tileBoard[5][1] = new Tile(3, false);
            tileBoard[9][1] = new Tile(3, false);
            tileBoard[5][5] = new Tile(3, false);
            tileBoard[5][9] = new Tile(3, false);
            tileBoard[9][5] = new Tile(3, false);
            tileBoard[9][9] = new Tile(3, false);
            tileBoard[13][5] = new Tile(3, false);
            tileBoard[13][9] = new Tile(3, false);
            tileBoard[5][13] = new Tile(3, false);
            tileBoard[9][13] = new Tile(3, false);
            //Double Letter Score (magentaTile())
            tileBoard[0][3] = new Tile(2, false);
            tileBoard[0][11] = new Tile(2, false);
            tileBoard[2][6] = new Tile(2, false);
            tileBoard[2][8] = new Tile(2, false);
            tileBoard[3][0] = new Tile(2, false);
            tileBoard[3][7] = new Tile(2, false);
            tileBoard[3][14] = new Tile(2, false);
            tileBoard[6][2] = new Tile(2, false);
            tileBoard[6][6] = new Tile(2, false);
            tileBoard[6][8] = new Tile(2, false);
            tileBoard[6][12] = new Tile(2, false);
            tileBoard[7][3] = new Tile(2, false);
            tileBoard[7][11] = new Tile(2, false);
            tileBoard[8][2] = new Tile(2, false);
            tileBoard[8][6] = new Tile(2, false);
            tileBoard[8][8] = new Tile(2, false);
            tileBoard[8][12] = new Tile(2, false);
            tileBoard[11][0] = new Tile(2, false);
            tileBoard[11][7] = new Tile(2, false);
            tileBoard[11][14] = new Tile(2, false);
            tileBoard[12][6] = new Tile(2, false);
            tileBoard[12][8] = new Tile(2, false);
            tileBoard[14][3] = new Tile(2, false);
            tileBoard[14][11] = new Tile(2, false);
            //Double Word Score (pinkTile())
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (tileBoard[i][j].getPointMultiplier() == 1) {
                        if (i == j){
                            tileBoard[i][j] = new Tile(2, true);
                        }
                    }
                }
            }
            int col = cols - 1;
            for (int i = 0; i < rows; i++) {
                if (tileBoard[i][col].getPointMultiplier() == 1) {
                    tileBoard[i][col] = new Tile(2, true);
                }
                col--;
            }
            //non special tiles
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (tileBoard[i][j] == null) { // line added
                        tileBoard[i][j] = new Tile();
                    }
                }
            }
            for (ScrabbleView view: views) {
                view.setCustomBoard();
            }
        }
    }

    public void constructCustomBoard(File f) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(f);
        int row = 0;
        int col, multiplier;
        boolean wordMultiplier;

        NodeList list = doc.getDocumentElement().getChildNodes();
        for(int i = 0; i < list.getLength(); i++){
            Node n = list.item(i);
            NodeList list2 = n.getChildNodes();
            if ((n.getNodeType() == Node.ELEMENT_NODE) && (n.getNodeName() != null)) {
                row = Integer.parseInt(n.getAttributes().getNamedItem("index").getNodeValue());
            }
            for (int j = 0; j < list2.getLength(); j++) {
                Node n2 = list2.item(j);
                if ((n2.getNodeType() == Node.ELEMENT_NODE) && (n2.getNodeName() != null)) {
                    col = Integer.parseInt(n2.getAttributes().getNamedItem("index").getNodeValue());
                    multiplier = Integer.parseInt(n2.getAttributes().getNamedItem("multiplier").getNodeValue());
                    wordMultiplier = n2.getAttributes().getNamedItem("type").getNodeValue().equals("word");
                    tileBoard[row][col] = new Tile(multiplier, wordMultiplier);
                }

            }
        }
    }

    /**
     * This method saves the scrabble game into a file
     * @param fileName the file to save to
     */
    public void save(String fileName) throws IOException {
        String xmlFile = fileName + "XML";
        saveTileBoardXML(new File(xmlFile));
        //saveTileBoardXML(new File(xmlFile));
        // saveTileBoardXML(xmlfile);

        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);


        String s= toString();


        byte[] bytes = s.getBytes();
        fos.write(bytes);
    }

    /**
     * This method reads from a file to laod a previous scrabble game
     * @param fileName the file to read from
     */
    public void load(String fileName) throws IOException, ParserConfigurationException, SAXException {
        String xmlFile = fileName + "XML";
        setTileBoard(true, xmlFile);

        String data="";

        int i =0;

        FileInputStream fis = new FileInputStream(fileName);
        InputStreamReader is = new InputStreamReader(fis, "UTF-8");
        try(BufferedReader buffer = new BufferedReader(is)){

            while((data = buffer.readLine()) != null ){
                int stringCounter=0;
                if(data.contains("#")){
                    loadPlayers(data);
                    stringCounter= 16;
                }
                if(data.contains("/")){
                    loadWordsOnBoard(data);
                    stringCounter=16;
                }
                else{
                    System.out.println(data.length());
                    while (stringCounter < 15) {
                        for (int j = 0; j < 15; j++) {
                            if (data.charAt(stringCounter) != ' ' && data.charAt(stringCounter) != '_') {
                                getStringBoard()[i][j] = data.charAt(stringCounter) + "";
                            }
                            else{
                                getStringBoard()[i][j] = "_";
                            }
                            stringCounter++;
                        }
                        i++;
                    }
                }
            }
            loadTileBoard();
            System.out.println("GM:\n " + toString());
            printGameStatus();
            for(ScrabbleView v: views){
                v.loadGame(this);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * this method loads the tile board
     */
    private void loadTileBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (stringBoard[i][j] == "_") {
                    tileBoard[i][j] = new Tile();
                } else {
                    tileBoard[i][j].placeLetter(new Letters(stringBoard[i][j].toUpperCase().charAt(0)));
                }
            }
        }
    }

    /**
     *
     * This method loads the words on the board of the previous game
     * @param data the string representation of the words that were played
     */
    private void loadWordsOnBoard(String data){
        wordsOnBoard.clear();
        Scanner scan = new Scanner(data).useDelimiter("/");
        while(scan.hasNext()){
            wordsOnBoard.add(scan.next());
        }
        for(String s: wordsOnBoard){
            bagOfLetters.inBag(new Letters(s.charAt(0)));
            System.out.println("Print: " + s);
        }

    }

    /**
     * loads the players from the saved game
     * @param data the string representation of the players
     */
    private void loadPlayers(String data){
        bagOfLetters = new BagOfLetters();
        int n =0;
        Scanner scan = new Scanner(data).useDelimiter("#");
        int numPlayers = Integer.parseInt(scan.next());
        int numBots = Integer.parseInt(scan.next());
        addPlayers(numPlayers, numBots);
        while(scan.hasNext()){
            for( ;n< numPlayers + numBots; n++){
                players.get(n).setName(scan.next());
                players.get(n).setScore(Integer.parseInt(scan.next()));
                String[] letters = scan.next().split(",");
                ArrayList<Letters> newLetters = new ArrayList<>();
                for(int l =0; l<7; l++){
                    bagOfLetters.inBag(new Letters(letters[l].charAt(0)));
                    System.out.println(letters[l].charAt(0));
                    newLetters.add(new Letters(letters[l].charAt(0)));
                }
                players.get(n).setLetters(newLetters);
            }
            setCurrentPlayer(players.get(Integer.parseInt(scan.next())));
        }
        System.out.println("Current Player: " + getCurrentPlayer());
        System.out.println("###");

        if(numBots !=0){
            AI ai = (AI) players.get(players.size()-1);
            ArrayList<Letters> let = players.get(players.size()-1).getLetters();
            for(int i=0; i<7; i++){
                ai.getLetter().add(let.get(i));
            }
        }
    }

    public void setNumBots(int numBot){
        numBots = numBot;
    }

    /**
     * This method converts returns the string representation of gameboard
     * @return the string representation of gameboard
     */
    @Override
    public String toString(){
        String str ="";
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                String s = getStringBoard()[i][j];
                if (s.isEmpty())
                    str += "_";
                else
                    str += s;
            }
            str += "\n";
        }

        //getPlayers score
        if(numBots ==0){
            str += "#" + players.size() + "#" + numBots;
        }
        else{
            str += "#" + (players.size()-1) + "#" + numBots;
        }

        for(Player p: players){
            str+= p.toString();
        }
        str+= "#" + players.indexOf(getCurrentPlayer());

        str += "\n/";

        for(String s:  wordsOnBoard){
            str+= s+ "/";
        }

        return str;
    }

    /**
     * Saves the current tileBoard into XML format - used for deserialization
     */
    private void saveTileBoardXML(File f) {
        StringBuilder returnStr = new StringBuilder();
        returnStr.append("<?xml version=\"1.0\"?>\n");
        returnStr.append("<tileBoard>\n");
        for (int i = 0; i < rows; i++) {
            returnStr.append(("<row index=\"" + i + "\"" + ">\n").indent(4));
            for (int j = 0; j < cols; j++) {
                String type = tileBoard[i][j].isWordPointMultiplier() ? "word" : "letter";
                returnStr.append(("<col index=\""+j+"\" multiplier=\"" + tileBoard[i][j].getPointMultiplier()+"\" type=\"" + type + "\"> </col>\n").indent(8));
            }
            returnStr.append("</row>\n");
        }
        returnStr.append("</tileBoard>");

        try {
            try (PrintStream out = new PrintStream(new FileOutputStream(f))) {
                out.print(returnStr);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Used for testing purposes
     */
    public void addPlayer(Player p) {
        players.add(p);
    }

}




