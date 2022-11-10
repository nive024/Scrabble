import javax.swing.*;
import java.awt.*;

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
        titlePanel.setLayout(new GridLayout(2,1));
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
        buttonsPanel.setPreferredSize(new Dimension(675,675));
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
        this.setSize(800,1000);
        this.setResizable(false);
        this.setVisible(true);

    }

    private void addButtons(){
        int count = 1;
        char col = 'A';
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                JButton b = new JButton("W");
                b.setActionCommand(col+""+(i+1)+"");
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
        for (int i = 0; i < number; i++){
            addPlayerLabel();
        }
    }
    private void addPlayerLabel(){
        JPanel playerP = new JPanel();
        playerP.setLayout(new BorderLayout());
        JLabel lettersLabel = new JLabel("Player's Letters: ");
        JPanel playerLettersPanel = new JPanel();
        playerLettersPanel.add(lettersLabel);
        for (int j = 0; j< 7; j++){
            JButton b = new JButton();
            b.setPreferredSize(new Dimension(35,35));
            playerLettersPanel.add(b);
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
        System.out.println("View: update");
        col = place.toUpperCase().charAt(0) - 'A';
        row = Character.getNumericValue(place.charAt(1)) - 1;
        grid[row][col].setText(letter);
    }
}
