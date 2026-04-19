package flashcard;

import java.util.List;

/**
 * Interface for organizing/sorting flash cards.
 */
public interface CardOrganizer {

    /**
     * Sorts and returns the list of flash cards according to the organizer's strategy.
     *
     * @param cards the list of cards to organize
     * @return organized list of cards
     */
    List<FlashCard> organize(List<FlashCard> cards);
}
