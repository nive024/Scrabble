import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * The ScrabbleController class represents all the user input logic. It is the controller in the MVC
 * structure. This class takes in and handles the user input and tells the model.
 *
 * @author Nivetha Sivasaravanan
 * @author Rimsha Atif
 * @author Nicole Lim
 */
public class ScrabbleController implements ActionListener {
    private GameBoard model;
    private ScrabbleFrame frame;
    private ArrayList<JButton> wordButtons;
    private String currentLetter;
    private AI AI;
    private boolean custom;

    private boolean play;
    JTextField nameField;
    JLabel nameLabel;

    /**
     * Contructor to initialize the controller
     * @param g the model it needs to update
     * @param frame the view it updates and listens from
     */
    public ScrabbleController (GameBoard g, ScrabbleFrame frame) {
        model = g;
        this.frame = frame;
        currentLetter = "";
        custom = false;
        nameField = new JTextField();
        nameLabel = new JLabel("Please enter the name of the file");
        wordButtons = new ArrayList<>();
        play = false;
    }

    /**
     * The method that handles the action event
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        //if the player presses play then get num players and tell model
        if (b.getText().equals("Play")) {
            play = true;
            model.addPlayers(frame.getNumberofPlayers(),frame.getNumberofBots());
            for (int i = 0; i < frame.getNumberofPlayers()+frame.getNumberofBots(); i++){
                System.out.println(frame.getNumberofPlayers()+frame.getNumberofBots());
                model.deal(i);
            }
            frame.disableOtherPlayers(0);
            if (!custom)
                model.setTileBoard(false, "");
            frame.getTurn().setText(model.getCurrentPlayer().getName()+"'s turn");
        }

        //checking if it is a player letter?
        else if(b.getActionCommand().equals("Player Button")) {
            if (currentLetter.equals("")) {
                b.setEnabled(false); //when button is clicked disable
                currentLetter = b.getText();
            }
        }

        //check if its end turn
        else if (b.getText().equals("End Turn")) {

            model.checkPlay();
            frame.getTurn().setText(model.getCurrentPlayer().getName()+"'s turn");
            if (frame.getBot() && model.getCurrentPlayer().getName().equals("Bot")){
                ((AI)model.getCurrentPlayer()).playTurn();
            }
        }

        // if the player clicks skip turn, it will move the turn to the next player
        else if (b.getText().equals("Skip Turn")){
            model.turnSkipped();
            model.getNextPlayer();
            frame.getTurn().setText(model.getCurrentPlayer().getName()+"'s turn");
        }

        // if the player clicks undo, it will bring the board back to the latest gameboard before the move was done
        else if (b.getText().equals("Undo")){
            model.undoTurn();
        }

        // if the player clicks redo, it will bring the board back to the gameboard before the undo
        else if (b.getText().equals("Redo")){
            model.redoTurn();
        }
        // if the player click custom, it will change the board to the custom board
        else if (b.getText().equals("Custom")) {
            JComponent[] inputs = new JComponent[] {nameLabel, nameField};
            int result = JOptionPane.showConfirmDialog(null, inputs, "Import AddressBook", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                model.setTileBoard(true, nameField.getText());
                nameField.setText("");
            }
            custom = true;
        }
        else if(b.getText().equals("Save")) {
            if (play) {
                try {
                    model.save(JOptionPane.showInputDialog("Enter the name of the file you want to save to"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else{
                JOptionPane.showMessageDialog(frame, "Click play before saving the game");
            }
        }

        else if(b.getText().equals("Load")) {
            play = true;
            try {
                model.load(JOptionPane.showInputDialog("Enter the name of the file you want to load"));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ParserConfigurationException ex) {
                throw new RuntimeException(ex);
            } catch (SAXException ex) {
                throw new RuntimeException(ex);
            }
        }
        //else it has to be a grid button
        else {
            if (!b.getText().equals("")) {
                if (currentLetter.equals("")) {
                    wordButtons.add(b);
                    currentLetter = "";
                } else {
                    JOptionPane.showMessageDialog(null, "There is already a letter here, you can't place one here");
                }
            } else {
                if (!currentLetter.equals("")) {
                    wordButtons.add(b);
                    model.addLetter(currentLetter, b.getActionCommand());
                    currentLetter = "";

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a letter to place from your letters first.");
                }
            }

        }
    }

    /**
     * looks at all the buttons clicked and returns the word that was placed and the first letter of the word
     * @return the word created and the place of first letter
     */
    private String getWord () {
        boolean horizontal = false;
        ArrayList<Character> letter = new ArrayList<>();
        if (wordButtons.size()> 1) {
            //if first letters of place are not equal, then the word is horizontal
            horizontal = wordButtons.get(0).getActionCommand().charAt(0) != wordButtons.get(1).getActionCommand().charAt(0);
        }

        for (JButton b: wordButtons) {
            if (horizontal) { //if horizontal, sort based on col
                letter.add(b.getActionCommand().charAt(0));
                Collections.sort(letter);
            } else { //if vertical sort based on row
                letter.add(b.getActionCommand().charAt(1));
                Collections.sort(letter);
            }
        }
        //get first and last and then get button values from in that range
        char first = letter.get(0);
        String word = "";
        if (horizontal) {
            for (int i = 0; i < wordButtons.size(); i++) {
                for (JButton b: wordButtons) {
                    if (b.getActionCommand().charAt(0) == first) {
                        word += b.getText();
                        first++;
                    }
                }
            }
        } else {
            for (int i = 0; i < wordButtons.size(); i++) {
                for (JButton b: wordButtons) {
                    if (b.getActionCommand().charAt(1) == first) {
                        word += b.getText();
                        first++;
                    }
                }
            }
        }

        //int first means horizontal
        //letter first means vertical
        String place = (horizontal) ? ((wordButtons.get(0).getActionCommand().charAt(1)-'A'+1)+""+letter.get(0)) : wordButtons.get(0).getActionCommand().charAt(0)+""+(letter.get(0)-'A'+1);

        wordButtons.clear();
        return word + " " + place;
    }


}
