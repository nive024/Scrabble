import java.util.ArrayList;

/**
 * The ScrabbleView interface represents the View in the MVC structure. It contains methods that the model uses
 * to update the view.
 *
 * @author Nivetha Sivasaravanan
 * @author Rimsha Atif
 */
public interface ScrabbleView {
    void update(String letter, String place);

    void updatePlayersLetters(String s, int playersNumber);

    void enableUsedPlayerButtons(int indexOfCurrentPlayer);

    void saveGridStatus();

    void enableGridButtons();

    void updateScore(int score, int indexOfCurrentPlayer);

    void displayErrorMessage(String word, String message);

    void disableOtherPlayers(int playerIndex);

    void enableChooseNumPlayerComponents(boolean isEnabled);

    void enableGameComponents(boolean isEnabled);

    void endGame(ArrayList<Player> players);

    void updateBoard(String[][] board);

    void setCustomBoard();

}