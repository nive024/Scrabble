import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScrabbleController implements ActionListener {
    GameBoard model;
    ScrabbleFrame frame;
    private ArrayList<JButton> wordButtons;
    int index = 0;

    public ScrabbleController (GameBoard g, ScrabbleFrame frame) {
        model = g;
        this.frame = frame;
        wordButtons = new ArrayList<>();
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        JButton b = (JButton) e.getSource();
        //if the player presses play then get num players and tell model
        if (b.getText().equals("Play")) {
            model.addPlayers(frame.getNumberofPlayers());
        }

        String word = "fire";

        //if it is a letter button
        if (!b.getActionCommand().equals("")) {
//            b.setText(""+word.charAt(index));
            //System.out.println(b.getActionCommand());
//            String place = b.getActionCommand();
            wordButtons.add(b);
            model.addLetter(""+word.charAt(index), b.getActionCommand());
            index++;
            //get letter from player that is selected
            //model.addLetter();
        }

        if (b.getText().equals("End Turn")) {
            model.placeWord(getWord());
        }
    }

    private String getWord () {
        boolean horizontal = false;

        ArrayList<Character> letter = new ArrayList<>();
        if (wordButtons.size()> 1) {
            //if first letters of place are not equal, then the word is horizontal
            horizontal = wordButtons.get(0).getActionCommand().charAt(0) != wordButtons.get(1).getActionCommand().charAt(0);
        }
        System.out.println(horizontal + " " + wordButtons.size());

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
        char last = letter.get(letter.size()-1);
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

        String place = (horizontal) ? (wordButtons.get(0).getActionCommand().charAt(1)+""+letter.get(0)) : wordButtons.get(0).getActionCommand().charAt(0)+""+letter.get(0);

        System.out.println(word + " " + place);
        wordButtons.clear();
        return word + " " + place;
    }
}
