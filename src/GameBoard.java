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
    /**
     * Constructor to initialize the game board with the specified columns and rows. Also initializes the first player.
     *
     * @param rows The number of rows on the board
     * @param cols The number of rows on the board
     */
    public GameBoard (int rows, int cols) {
        players = new ArrayList<>();
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
                if (tileBoard[i][j] == null) {
                    if (i == j){
                        tileBoard[i][j] = new Tile(2, true);
                    }
                }
            }
        }
        int col = cols - 1;
        for (int i = 0; i < rows; i++) {
                if (tileBoard[i][col] == null) {
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
        players.add(currentPlayer);
    }

    /**
     * Gets the current player whose turn it is
     * @return The player whose turn it is
     */
    public Player getCurrentPlayer(){
        return this.currentPlayer;
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

    private void revertStringBoard() {
        for (int k = 0; k < rows; k++) {
            for (int n = 0; n < cols; n++) {
                if (tileBoard[k][n].isEmpty())
                    stringBoard[k][n] = "_";
                else
                    stringBoard[k][n] = tileBoard[k][n].getLetter().getLetter() + "";
            }
        }
    }

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

    public void checkPlay() {
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
                        return;
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
        }

        printGameStatus();
        getNextPlayer();
        numSkips = 0;
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
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                System.out.print(stringBoard[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
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
        /**
        while(i<word.length()){
            Letters nl = new Letters(word.charAt(i));
            score += nl.getPointValue(nl.getLetter());
            i++;
        }
        //if it's the first word being placed, then they should get double the points
        System.out.println("Yay! You scored " + score + " points for " + word);
        return (isBoardEmpty) ? 2*score : score;
         */
    }

    /**
     * This method deals an ArrayList of random Letters. If the currentLetter already contains some letters, 7- the number of current letters are dealt
     * Otherwise, 7 random letters are dealt
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
    public void addPlayers(int numPlayers) {
        for (int i = 0; i < numPlayers; i++) {
            Player p = new Player("Player " + (i+1));
            players.add(p);
        }
        currentPlayer = players.get(0);
        for (ScrabbleView view: views) {
            view.enableGameComponents(true);
            view.enableChooseNumPlayerComponents(false);
        }
    }


    //Methods for tests

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

    public void endGame() {
        for (ScrabbleView view: views) {
            Collections.sort(players);
            view.endGame(players);
        }
    }

}
