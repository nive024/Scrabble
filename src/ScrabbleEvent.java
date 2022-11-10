import java.util.EventObject;

public class ScrabbleEvent extends EventObject {

    public ScrabbleEvent (GameBoard model, String word, String Place) {
        super(model);
    }

}
