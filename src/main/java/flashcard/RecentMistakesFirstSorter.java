package flashcard;

import java.util.ArrayList;
import java.util.List;

/**
 * Organizes flash cards so that cards answered incorrectly in the most recent round
 * appear first, while preserving the relative order within each group.
 *
 * <p>Strategy: Cards that were wrong in the last round come first (in their original
 * relative order), followed by cards that were correct (in their original relative order).
 */
public class RecentMistakesFirstSorter implements CardOrganizer {
    /**
     * Constructs a new RecentMistakesFirstSorter.
     */
    public RecentMistakesFirstSorter() {}


    /**
     * Returns cards with recently-missed cards first.
     * Internal ordering within wrong/correct groups is preserved.
     *
     * @param cards the list of cards to organize
     * @return organized list with recent mistakes first
     */
    @Override
    public List<FlashCard> organize(List<FlashCard> cards) {
        List<FlashCard> wrongCards = new ArrayList<>();
        List<FlashCard> otherCards = new ArrayList<>();

        for (FlashCard card : cards) {
            if (card.isWrongInLastRound()) {
                wrongCards.add(card);
            } else {
                otherCards.add(card);
            }
        }

        List<FlashCard> result = new ArrayList<>();
        result.addAll(wrongCards);
        result.addAll(otherCards);
        return result;
    }
}
