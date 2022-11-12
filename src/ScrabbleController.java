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
    private String currentLetter;

    public ScrabbleController (GameBoard g, ScrabbleFrame frame) {
        model = g;
        this.frame = frame;
        currentLetter = "";
        wordButtons = new ArrayList<>();
    }
    @Override
    public void actionPerformed(ActionEvent e) {


        JButton b = (JButton) e.getSource();
        //System.out.println(b.getActionCommand());
        //if the player presses play then get num players and tell model
        if (b.getText().equals("Play")) {
            model.addPlayers(frame.getNumberofPlayers());
            for (int i = 0; i < frame.getNumberofPlayers(); i++){
                model.deal(7, i);
                }
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
            model.placeWord(getWord());
        }

        //else it has to be a grid button
        else {
            System.out.println("else");
            if (!currentLetter.equals("")){
                 b.setEnabled(false);
//            b.setText(""+word.charAt(index));
            //System.out.println(b.getActionCommand());
//            String place = b.getActionCommand();
                wordButtons.add(b);
                System.out.println("current letter " + currentLetter);
                model.addLetter(currentLetter, b.getActionCommand());
                currentLetter = "";
             }
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

        System.out.println(word + " " + place);
        wordButtons.clear();
        return word + " " + place;
    }


}
