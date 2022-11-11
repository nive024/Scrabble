public interface ScrabbleView {
    void update(String letter, String place);


    void updatePlayersLetters(String s, int playersNumber);


    void enableUsedButtons(int indexOfCurrentPlayer);
}