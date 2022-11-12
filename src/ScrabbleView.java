public interface ScrabbleView {
    void update(String letter, String place);


    void updatePlayersLetters(String s, int playersNumber);


    void enableUsedPlayerButtons(int indexOfCurrentPlayer);

    void enableGridButtons(String word, String place, int row, int col);

    void updateScore(int score, int indexOfCurrentPlayer);

    void displayErrorMessage(String word, String message);
}