import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static javax.swing.BoxLayout.*;

/**
 * This ScrabbleFrame extends JFrame representing the GUI of scrabble
 *
 * This class updates the GUI whenever the status of Scrabble game is changed. This includes displaying buttons and their test,
 * disabling and enabling buttons, updating score, and displaying error messages.
 *
 * @author Nicole Lim
 * @author Nivetha Sivasaravanan
 * @author Rimsha Atif
 */

public class ScrabbleFrame extends JFrame implements ScrabbleView{

    private JPanel buttonsPanel, xPanel, yPanel, botPanel, movePanel;
    private JPanel playerPanel, gameboardPanel, gamePanel;
    private String[] numberofPlayers = {"1 player", "2 players", "3 players", "4 players"};
    private ScrabbleController sc;
    private JComboBox<String> playerCB, botCB;
    private String[] botPlayers = {"No bot","1 bot"};
    private JButton[][] grid;
    private JButton[][] oldGrid;
    private ArrayList<JButton>[] playersButtonsArray;
    private ArrayList<JPanel> playerPanelArray;
    private JLabel turn;
    private JButton endTurnBtn, playBtn, skipBtn;
    private JButton undoBtn, redoBtn;
    private int width, height;
    private boolean bot;
    private GameBoard gameBoardModel; //change made

    /**
     * Constructor to initialize the JFrame and add the components on to it
     */
    public ScrabbleFrame(){
        super("Scrabble");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) screenSize.getWidth();
        height = (int) screenSize.getHeight();
        System.out.println(width);
        System.out.println(height);


        grid = new JButton[15][15];
        oldGrid = new JButton[15][15];
        playerPanelArray = new ArrayList<>();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel=new JPanel();
        this.setLayout(new BoxLayout(gamePanel, X_AXIS));

        //add model
        gameBoardModel = new GameBoard(15, 15); //change made
        sc = new ScrabbleController(gameBoardModel, this);
        gameBoardModel.addView(this);

        // Panel for the title label
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(4,0));
        JLabel titleLabel = new JLabel("SCRABBLE");
        titleLabel.setFont(new Font("Verdana", Font.PLAIN, 30));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(titleLabel);

        JPanel numberPlayersPanel = new JPanel();
        numberPlayersPanel.setLayout(new FlowLayout());
        JLabel numberLabel = new JLabel("Please enter the number of players: ");
        numberLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        playerCB = new JComboBox<>(numberofPlayers);
        numberPlayersPanel.add(numberLabel);
        numberPlayersPanel.add(playerCB);
        titlePanel.add(numberPlayersPanel);
        turn = new JLabel("");
        numberLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        numberPlayersPanel.add(turn);

        botPanel = new JPanel();
        JLabel botLabel = new JLabel("Please click bot if you would like to play with a bot: ");
        botLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        botCB = new JComboBox<>(botPlayers);
        botPanel.add(botLabel);
        botPanel.add(botCB);
        titlePanel.add(botPanel);

        movePanel = new JPanel();
        playBtn = new JButton("Play");
        playBtn.addActionListener(sc);
        playBtn.setActionCommand("");
        movePanel.add(playBtn);
        endTurnBtn = new JButton("End Turn");
        endTurnBtn.setActionCommand("");
        endTurnBtn.addActionListener(sc);
        movePanel.add(endTurnBtn);
        endTurnBtn.setEnabled(false);
        skipBtn = new JButton("Skip Turn");
        skipBtn.setActionCommand("");
        skipBtn.addActionListener(sc);
        movePanel.add(skipBtn);
        undoBtn = new JButton("Undo");
        undoBtn.setActionCommand("");
        undoBtn.addActionListener(sc);
        undoBtn.setEnabled(false);
        movePanel.add(undoBtn);
        redoBtn = new JButton("Redo");
        redoBtn.setActionCommand("");
        redoBtn.addActionListener(sc);
        redoBtn.setEnabled(false);
        movePanel.add(redoBtn);
        titlePanel.add(movePanel);


        // Panel for the buttons of the board
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(15,15));
        //buttonsPanel.setSize(width/3, height);
        buttonsPanel.setPreferredSize(new Dimension(width/2, (int) (height/1.5)));
        addButtons();

        // Panel for the X axis letter labels
        xPanel = new JPanel();
        xPanel.setLayout(new GridLayout(1,15));
        addXAxisLabel();

        // Panel for the Y axis number labels
        yPanel = new JPanel();
        yPanel.setLayout(new GridLayout(15,1));
        addYAxisLabel();

        // Panel for the player information
        playerPanel = new JPanel();
        playerPanel.setLayout(new GridLayout(4,1));
        addPlayerToPanel(4);

        // Panel to combine the game board components
        gameboardPanel = new JPanel();
        gameboardPanel.setLayout(new BorderLayout());
        gameboardPanel.add(xPanel, BorderLayout.PAGE_START);
        gameboardPanel.add(yPanel, BorderLayout.LINE_START);
        gameboardPanel.add(buttonsPanel, BorderLayout.CENTER);

        gamePanel.add(titlePanel);
        gamePanel.add(gameboardPanel);
        gamePanel.add(playerPanel);
        this.setContentPane(gamePanel);

        enableGameComponents(false);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width/3,height);
        this.setResizable(true);
        this.setVisible(true);

    }

    /**
     * This method returns the JLabel turn
     * @return the JLabel
     */
    public JLabel getTurn(){
        return turn;
    }

    /**
     * This method creates the grid of JButtons
     */
    private void addButtons(){
        int count = 1;
        char col = 'A';
        char row = 'A';
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                JButton b = new JButton("");
                b.setActionCommand(col+""+row+"");
                if(gameBoardModel.getTileBoard()[i][j].redTile()){ // testing
                    b.setBackground(Color.RED);
                }
                else if (gameBoardModel.getTileBoard()[i][j].blueTile()){ // testing
                    b.setBackground(Color.BLUE);
                }
                else if (gameBoardModel.getTileBoard()[i][j].lightBlueTile()){ // testing
                    b.setBackground(new Color(0,192,255));
                }
                else if (gameBoardModel.getTileBoard()[i][j].pinkTile()){ // testing
                    b.setBackground(Color.PINK);
                }
                else if(count % 2 != 0){
                    b.setBackground(new Color(224, 224, 224));
                }
                else{
                    b.setBackground(Color.WHITE);
                }
                b.setBorderPainted(false);
                b.setOpaque(true);
                b.setFont(new Font("Verdana", Font.PLAIN, 8));
                b.setMargin(new Insets(0, 0, 0, 0));
                b.addActionListener(sc);
                grid[i][j] = b;
                buttonsPanel.add(b);
                count++;
                col++;
            }
            col = 'A';
            row++;
        }

        //initialize oldGrid
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                JButton b = new JButton("");
                b.setActionCommand(col+""+row+"");
                b.addActionListener(sc);
                oldGrid[i][j] = b;
                count++;
                col++;
            }
            col = 'A';
            row++;
        }

    }

    /**
     * This method create the label for the x-axis of the grid. The letters range from a to p representing the column coordinate.
     */
    private void addXAxisLabel(){
        xPanel.add(new JLabel(""));
        for (char alphabet = 'a'; alphabet < 'p'; alphabet ++){
            JLabel l = new JLabel(Character.toString(alphabet));
            xPanel.add(l);
        }
    }

    /**
     * This method creates the label for the y-axis of the grid. The numbers range from 1-15 representing the row coordinate.
     */
    private void addYAxisLabel(){
        for (int i = 0; i < 15; i++){
            JLabel l = new JLabel(String.valueOf(i+1));
            yPanel.add(l);
        }
    }

    /**
     * This method initializes the players panel
     * @param number The number of players in the game.
     */
    private void addPlayerToPanel(int number){
        playersButtonsArray = new ArrayList[number];
        for (int i = 0; i < number; i++){
            playersButtonsArray[i] = new ArrayList<>(); //initialize arraylists
            addPlayerLabel(i);
        }
    }

    /**
     * This method initializes the players label, inluding the letter buttons and the score.
     * @param i an integer representing the index of the current player's label to initialze
     */
    private void addPlayerLabel(int i){
        JPanel playerP = new JPanel();
        playerP.setLayout(new BorderLayout());
        JLabel lettersLabel = new JLabel("Player " + (i+1) + "'s Letters: ");
        JPanel playerLettersPanel = new JPanel();
        playerLettersPanel.add(lettersLabel);
        for (int j = 0; j< 7; j++){
            JButton b = new JButton();
            b.setPreferredSize(new Dimension(50,35));
            b.setFont(new Font("Verdana", Font.PLAIN, 8));
            b.setActionCommand("Player Button");
            b.addActionListener(sc);
            playerLettersPanel.add(b);
            b.setEnabled(false);
            playersButtonsArray[i].add(b); //add buttons to each player's list
        }
        lettersLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        JLabel scoreLabel = new JLabel("Player " + (i+1) + "'s Score: " + 0);
        scoreLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        playerP.add(playerLettersPanel, BorderLayout.WEST);
        playerP.add(scoreLabel, BorderLayout.EAST);
        playerPanel.add(playerP);
        playerPanelArray.add(playerP);
    }

    /**
     * This method returns the number of players in the game.
     * @return the number of players in the game
     */
    public int getNumberofPlayers() {
        String s = String.valueOf(playerCB.getSelectedItem());

        return Integer.parseInt(s.charAt(0)+"");
    }

    /**
     * This method returns the number of bots in the game.
     * @return the number of bots in the game
     */
    public int getNumberofBots() {
        if (String.valueOf(botCB.getSelectedItem()).equals("1 bot")) {
            bot = true;
            return 1;
        }
        return 0;
    }

    /**
     * Returns if the bot is activated or not
     * @return true if the bot is activated, false otherwise
     */
    public boolean getBot(){
        return bot;
    }

    /**
     * This method updates the view by changing the text of the grid button that was clicked to represent the letter
     * @param letter the letter we want to set the grid button to
     * @param place the string representation of the button coordinate
     */
    @Override
    public void update(String letter, String place) {
        int row, col;
        col = place.toUpperCase().charAt(0) - 'A';
        row = place.toUpperCase().charAt(1) - 'A';
        grid[row][col].setText(letter);
        skipBtn.setEnabled(false);
        endTurnBtn.setEnabled(true);
    }

    /**
     * When new letters are dealt update the view
     * @param s the string representation of all the new letters
     * @param playersNumber the index of the player who we want to deal the letters to.
     */
    @Override
    public void updatePlayersLetters(String s, int playersNumber){
        int charIndex=0;

        for(JButton b: playersButtonsArray[playersNumber]){
                if (!b.isEnabled()) {
                    b.setText(Character.toString(s.charAt(charIndex)));
                    b.setEnabled(true);
                    charIndex++;
                }
        }
    }

    /**
     * If a word is invalid this method re-enables all the used player buttons
     * @param indexOfCurrentPlayer the index of the current player
     */
    @Override
    public void enableUsedPlayerButtons(int indexOfCurrentPlayer){
        for(JButton b: playersButtonsArray[indexOfCurrentPlayer]){
            b.setEnabled(true);
        }
    }

    /**
     * If an invalid play is made this method re-enables all the grid buttons that were clicked
     */
    @Override
    public void enableGridButtons(){
        //copy the contents of oldGrid into grid
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                grid[i][j].setText(oldGrid[i][j].getText());
            }
        }
    }

    /**
     * This method displays the appropriate error message when an invalid move is made
     * @param word the word that was played
     * @param message the string representation of the message to be displayed
     */
    @Override
    public void displayErrorMessage(String word, String message) {
        if(message.compareTo("ov") == 0){
            JOptionPane.showMessageDialog(this, "The word overlaps another");
        }
        if(message.compareTo("fit") == 0){
            JOptionPane.showMessageDialog(this,  word +" does not fit here");
        }
        if(message.compareTo("iv") == 0){
            JOptionPane.showMessageDialog(this, word+" is not a valid word");
            skipBtn.setEnabled(true);
        }
        if(message.equals("floating")){
            JOptionPane.showMessageDialog(this, "Cannot place a floating word");
            skipBtn.setEnabled(true);
        }
        if(message.compareTo("center") == 0){
            JOptionPane.showMessageDialog(this, "The first word must be placed on the center square");
        }
        if(message.equals("singleLetter")) {
            JOptionPane.showMessageDialog(this, "The word must be longer than one letter");
        }
        endTurnBtn.setEnabled(false);
    }

    /**
     * This method updates the view to show the current score of the player
     * @param score the score of the current player
     * @param indexOfPlayer the index of the current player
     */
    @Override
    public void updateScore(int score, int indexOfPlayer){
        for (Component component: playerPanelArray.get(indexOfPlayer).getComponents()) {
            if (component instanceof JLabel) {
                if (((JLabel) component).getText().contains("Score:")) {
                    ((JLabel) component).setText("Player " + (indexOfPlayer+1) + "'s Score: " + score);
                }
            }
        }
        skipBtn.setEnabled(true);
        endTurnBtn.setEnabled(false);
    }

    /**
     * This method disables the buttons of players while they are not the current player
     * @param playerIndex the index of the current player
     */
    @Override
    public void disableOtherPlayers(int playerIndex) {
        for (int i = 0; i < 4; i++) {
            if (i != playerIndex) {
                for (JButton b: playersButtonsArray[i]) {
                    b.setEnabled(false);
                }
            } else {
                for (JButton b: playersButtonsArray[i]) {
                    b.setEnabled(true);
                }
            }
        }
    }

    /**
     * This method enables/disables the game components (the grid and the skip button)
     * @param isEnabled true if we want to enable the components otherwise false
     */
    @Override
    public void enableGameComponents(boolean isEnabled) {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                grid[i][j].setEnabled(isEnabled);
            }
        }
        skipBtn.setEnabled(isEnabled);
        undoBtn.setEnabled(isEnabled);
        redoBtn.setEnabled(isEnabled);
//        endTurnBtn.setEnabled(isEnabled);
    }

    /**
     * This method enables/disables the player components (the combo box and play button)
     * @param isEnabled true if we want to enable the components otherwise false
     */
    @Override
    public void enableChooseNumPlayerComponents(boolean isEnabled) {
        playBtn.setEnabled(isEnabled);
        playerCB.setEnabled(isEnabled);
    }

    /**
     * This method saves the current status of the grid
     */
    @Override
    public void saveGridStatus(){
        //copy the contents of grid into oldGrid
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                oldGrid[i][j].setText(grid[i][j].getText());
            }
        }
    }

    /**
     * This method ends the game. It disables the endTurn button and displays the player's score and winner
     * @param players all the players in the game
     */
    @Override
    public void endGame(ArrayList<Player> players) {
        enableGameComponents(false);
        endTurnBtn.setEnabled(false);
        JLabel[] playerScoreLabels = new JLabel[players.size() + 1];
        playerScoreLabels[0] = new JLabel("Game ended!\n");
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            playerScoreLabels[i+1] = new JLabel(player.getName() + "'s score: " + player.getScore());
        }
        JOptionPane.showMessageDialog(null, playerScoreLabels, "Score Board", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
    }

    /**
     *  Updates the letters on the board after the AI played
     * @param board
     */
    @Override
    public void updateBoard(String[][] board){
        for (int i = 0; i < 15; i++){
            for (int j = 0; j < 15; j++){
                if (board[i][j].equals("_")){
                    grid[i][j].setText("");
                }
                else{
                    grid[i][j].setText(board[i][j]);
                }
               saveGridStatus();
            }
        }
    }
    public static void main(String[] args) {
        new ScrabbleFrame();
    }
}
