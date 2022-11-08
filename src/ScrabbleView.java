import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import java.awt.*;

public class ScrabbleView extends JFrame {

    private JPanel buttonsPanel;
    private JPanel xPanel;
    private JPanel yPanel;
    private JPanel playerPanel;
    private JPanel gameboardPanel;

    public ScrabbleView(){
        super("Scrabble");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Panel for the title label
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(2,1));
        JLabel titleLabel = new JLabel("SCRABBLE");
        titleLabel.setFont(new Font("Verdana", Font.PLAIN, 35));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(titleLabel);

        JPanel numberPlayersPanel = new JPanel();
        numberPlayersPanel.setLayout(new FlowLayout());
        JLabel numberLabel = new JLabel("Please enter the number of players: ");
        numberLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        JRadioButton player1RB=new JRadioButton("1 Player");
        player1RB.setSelected(true);
        JRadioButton player2RB=new JRadioButton("2 Players");
        JRadioButton player3RB = new JRadioButton("3 Players");
        JRadioButton player4RB = new JRadioButton("4 Players");
        Box radioBox = Box.createHorizontalBox();
        radioBox.add(player1RB);
        radioBox.add(player2RB);
        radioBox.add(player3RB);
        radioBox.add(player4RB);
        JButton playBtn = new JButton("Play");
        numberPlayersPanel.add(numberLabel);
        numberPlayersPanel.add(radioBox);
        numberPlayersPanel.add(playBtn);
        titlePanel.add(numberPlayersPanel);


        // Panel for the buttons of the board
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(15,15));
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
        addPlayerToPanel(2);

        // Panel to combine the game board components
        gameboardPanel = new JPanel();
        gameboardPanel.setLayout(new BorderLayout());
        gameboardPanel.add(xPanel, BorderLayout.PAGE_START);
        gameboardPanel.add(yPanel, BorderLayout.LINE_START);
        gameboardPanel.add(buttonsPanel, BorderLayout.CENTER);

        // Adds each panel to the frame
        this.add(titlePanel, BorderLayout.PAGE_START);
        this.add(gameboardPanel, BorderLayout.CENTER);
        this.add(playerPanel, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(750,750);
        this.setResizable(false);
        this.setVisible(true);

    }

    private void addButtons(){
        int count = 1;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                JButton b = new JButton(" ");
                if(count % 2 != 0){
                    b.setBackground(new Color(77, 141, 182));
                }
                else{
                    b.setBackground(Color.WHITE);
                }
                b.setBorderPainted(false);
                b.setOpaque(true);
                buttonsPanel.add(b);
                count++;
            }
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
        lettersLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        JLabel scoreLabel = new JLabel("Player's Score: " + 0);
        scoreLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        playerP.add(playerLettersPanel, BorderLayout.WEST);
        playerP.add(scoreLabel, BorderLayout.EAST);
        playerPanel.add(playerP);
    }

    public static void main(String[] args) {
        new ScrabbleView();
    }
}
