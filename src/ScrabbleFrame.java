import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

import static javax.swing.BoxLayout.*;

public class ScrabbleFrame extends JFrame implements ScrabbleView{

    private JPanel buttonsPanel;
    private JPanel xPanel;
    private JPanel yPanel;
    private JPanel playerPanel;
    private JPanel gameboardPanel;
    private JPanel gamePanel;
    private String[] numberofPlayers = {"1 player", "2 players", "3 players", "4 players"};
    private ScrabbleController sc;
    private JComboBox<String> playerCB;
    private JButton[][] grid;
    private ArrayList<JButton>[] playersButtonsArray;

    public ScrabbleFrame(){
        super("Scrabble");
        grid = new JButton[15][15];
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel=new JPanel();
//        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.setLayout(new BoxLayout(gamePanel, BoxLayout.X_AXIS));

        //add model
        GameBoard gameBoardModel = new GameBoard(15, 15);
        sc = new ScrabbleController(gameBoardModel, this);
        gameBoardModel.addView(this);

        // Panel for the title label
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(2,0));
        JLabel titleLabel = new JLabel("SCRABBLE");
        titleLabel.setFont(new Font("Verdana", Font.PLAIN, 30));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(titleLabel);

        JPanel numberPlayersPanel = new JPanel();
        numberPlayersPanel.setLayout(new FlowLayout());
        JLabel numberLabel = new JLabel("Please enter the number of players: ");
        numberLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        playerCB = new JComboBox<>(numberofPlayers);
        JButton playBtn = new JButton("Play");
        playBtn.addActionListener(sc);
        playBtn.setActionCommand("");
        numberPlayersPanel.add(numberLabel);
        numberPlayersPanel.add(playerCB);
        numberPlayersPanel.add(playBtn);
        titlePanel.add(numberPlayersPanel);
        JButton remove = new JButton("End Turn");
        remove.setActionCommand("");
        remove.addActionListener(sc);
        numberPlayersPanel.add(remove);


        // Panel for the buttons of the board
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(15,15));
        buttonsPanel.setPreferredSize(new Dimension(375,375));
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


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,500);
        this.setResizable(true);
        this.setVisible(true);

    }
    private void addButtons(){
        int count = 1;
        char col = 'A';
        char row = 'A';
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                JButton b = new JButton("W");
                b.setActionCommand(col+""+row+"");
                if(count % 2 != 0){
                    b.setBackground(new Color(77, 141, 182));
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

    }

    private void addXAxisLabel(){
        xPanel.add(new JLabel(""));
        for (char alphabet = 'a'; alphabet < 'p'; alphabet ++){
            JLabel l = new JLabel(Character.toString(alphabet));
            xPanel.add(l);
        }
    }
    private void addYAxisLabel(){
        for (int i = 0; i < 15; i++){
            JLabel l = new JLabel(String.valueOf(i+1));
            yPanel.add(l);
        }
    }

    private void addPlayerToPanel(int number){
        playersButtonsArray = new ArrayList[number];
        for (int i = 0; i < number; i++){
            playersButtonsArray[i] = new ArrayList<>(); //initialize arraylists
            addPlayerLabel(i);
        }
    }
    private void addPlayerLabel(int i){
        JPanel playerP = new JPanel();
        playerP.setLayout(new BorderLayout());
        JLabel lettersLabel = new JLabel("Player's Letters: ");
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
        JLabel scoreLabel = new JLabel("Player's Score: " + 0);
        scoreLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        playerP.add(playerLettersPanel, BorderLayout.WEST);
        playerP.add(scoreLabel, BorderLayout.EAST);
        playerPanel.add(playerP);
    }

    public int getNumberofPlayers() {
        String s = String.valueOf(playerCB.getSelectedItem());
        return Integer.parseInt(s.charAt(0)+"");
    }
    public static void main(String[] args) {
        new ScrabbleFrame();
    }

    @Override
    public void update(String letter, String place) {
        int row, col;
        System.out.println("View: update " + place);
        col = place.toUpperCase().charAt(0) - 'A';
        row = place.toUpperCase().charAt(1) - 'A';
//        if (place.length() == 3) {
//            row = Integer.parseInt(place.substring(1)) - 1;
//        } else {
//            row = Character.getNumericValue(place.charAt(1)) - 1;
//        }
        grid[row][col].setText(letter);
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
                    System.out.println("disabled");
                    b.setText(Character.toString(s.charAt(charIndex)));
                    b.setEnabled(true);
                    charIndex++;
                }
        }
    }

    /**
     * if a word is invalid we want to re-enable all the used buttons
     * @param indexOfCurrentPlayer the index of the current player
     */
    @Override
    public void enableUsedPlayerButtons(int indexOfCurrentPlayer){
        for(JButton b: playersButtonsArray[indexOfCurrentPlayer]){
            b.setEnabled(true);
        }
    }

    @Override
    public void enableGridButtons(String word, String place, int row, int cols){
       if(Character.isDigit(place.charAt(0))){
          for(int i = 0; i<word.length(); i++){
              grid[row][i + cols].setEnabled(true);
              grid[row][i + cols].setText("W");
          }
        }
       else{
           for(int i = 0; i<word.length(); i++){
               grid[i + row][cols].setEnabled(true);
               grid[row +i][cols].setText("W");
           }
       }

    }
    @Override
    public void updateScore(int score, int indexOfPlayer){

    }
    @Override
    public void displayErrorMessage(String word, String message) {
        JOptionPane op = new JOptionPane();
        op.setSize(200, 200);
        if(message.compareTo("ov") == 0){
            op.showMessageDialog(this, "The word overlaps another");
        }
        if(message.compareTo("fit") == 0){
            op.showMessageDialog(this,  " does not fit here");
        }
        if(message.compareTo("iv") == 0){
            op.showMessageDialog(this, " is not a valid word");
        }
        if(message.equals("floating")){
            op.showMessageDialog(this, "Cannot place a floating word");
        }
        if(message.compareTo("center") == 0){
            op.showMessageDialog(this, "The first word must be placed on the center square");
        }
        op.setVisible(true);
        this.add(op);
    }
}
