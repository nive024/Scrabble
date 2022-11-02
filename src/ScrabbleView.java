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
        JLabel titleLabel = new JLabel("SCRABBLE");
        titleLabel.setFont(new Font("Verdana", Font.PLAIN, 35));
        titlePanel.add(titleLabel);

        // Panel for the buttons of the board
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(15,15));
        addButtons();

        // Panel for the X axis letter labels
        xPanel = new JPanel();
        xPanel.setLayout(new GridLayout(1,15));
        addXAxisLabel();

        // Panel for the Y axis number lables
        yPanel = new JPanel();
        yPanel.setLayout(new GridLayout(15,1));
        addYAxisLabel();

        // Panel for the player information
        playerPanel = new JPanel();
        playerPanel.setLayout(new BorderLayout());
        addPlayerLabel();

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
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                JButton b = new JButton(" ");
                buttonsPanel.add(b);
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

    private void addPlayerLabel(){
        JLabel lettersLabel = new JLabel("Player's Letters: ");
        lettersLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        JLabel scoreLabel = new JLabel("Player's Score: ");
        scoreLabel.setFont(new Font("Verdana", Font.PLAIN, 20));
        playerPanel.add(lettersLabel, BorderLayout.WEST);
        playerPanel.add(scoreLabel, BorderLayout.EAST);
    }

    public static void main(String[] args) {
        new ScrabbleView();
    }
}
