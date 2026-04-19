package flashcard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Organizes flash cards in random order.
 */
public class RandomSorter implements CardOrganizer {
    /**
     * Constructs a new RandomSorter.
     */
    public RandomSorter() {}


    /**
     * Returns a randomly shuffled copy of the card list.
     *
     * @param cards the list of cards to organize
     * @return randomly shuffled list of cards
     */
    @Override
    public List<FlashCard> organize(List<FlashCard> cards) {
        List<FlashCard> shuffled = new ArrayList<>(cards);
        Collections.shuffle(shuffled);
        return shuffled;
    }
}
