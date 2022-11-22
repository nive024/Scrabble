import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    /**
     * Contructor to initialize the controller
     * @param g the model it needs to update
     * @param frame the view it updates and listens from
     */
    public ScrabbleController (GameBoard g, ScrabbleFrame frame) {
        model = g;
        this.frame = frame;
        currentLetter = "";
        wordButtons = new ArrayList<>();
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
            model.addPlayers(frame.getNumberofPlayers());
            for (int i = 0; i < frame.getNumberofPlayers(); i++){
                model.deal(i);
            }
            frame.disableOtherPlayers(0);
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
            try {
                //model.placeWord(getWord());
                model.checkPlay();
                frame.getTurn().setText(model.getCurrentPlayer().getName()+"'s turn");
                if (frame.getBot() && frame.getTurn().getText().equals("Bot's turns")){
                    AI.playTurn();
                }

            } catch (IndexOutOfBoundsException exception) {
                JOptionPane.showMessageDialog(null, "You didn't place any letters. If you want to skip" +
                        " your turn, click Skip Turn");
            }
        }

        else if (b.getText().equals("Skip Turn")){
            model.turnSkipped();
            model.getNextPlayer();
            frame.getTurn().setText(model.getCurrentPlayer().getName()+"'s turn");
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
