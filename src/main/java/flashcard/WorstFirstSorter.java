package flashcard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Organizes flash cards so that the most frequently missed cards come first.
 * Cards with more incorrect attempts relative to total attempts appear first.
 */
public class WorstFirstSorter implements CardOrganizer {
    /**
     * Constructs a new WorstFirstSorter.
     */
    public WorstFirstSorter() {}


    /**
     * Returns cards sorted so that worst-performing cards come first.
     * Cards with no attempts yet are placed at the end.
     *
     * @param cards the list of cards to organize
     * @return list sorted worst-first
     */
    @Override
    public List<FlashCard> organize(List<FlashCard> cards) {
        List<FlashCard> sorted = new ArrayList<>(cards);
        sorted.sort(Comparator.comparingDouble(this::errorRate).reversed());
        return sorted;
    }

    /**
     * Calculates the error rate for a card.
     * Cards with no attempts get a rate of 0 (placed at end).
     *
     * @param card the flash card
     * @return error rate between 0.0 and 1.0
     */
    private double errorRate(FlashCard card) {
        if (card.getTotalAttempts() == 0) {
            return 0.0;
        }
        int wrong = card.getTotalAttempts() - card.getCorrectAttempts();
        return (double) wrong / card.getTotalAttempts();
    }
}
