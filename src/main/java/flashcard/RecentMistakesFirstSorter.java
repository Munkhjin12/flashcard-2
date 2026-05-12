package flashcard;

import java.util.ArrayList;
import java.util.List;

public class RecentMistakesFirstSorter implements CardOrganizer {
    @Override
    public List<FlashCard> organize(List<FlashCard> cards) {
        List<FlashCard> sortedCards = new ArrayList<>(cards);

        sortedCards.sort((c1, c2) -> {
            // 1. Сүүлийн тойрогт алдсан картуудыг хооронд нь цаг хугацаагаар эрэмбэлэх
            if (c1.isWrongInLastRound() && c2.isWrongInLastRound()) {
                return Long.compare(c2.getLastFailedTime(), c1.getLastFailedTime());
            }
            // 2. Алдсан картыг зөв хариулсан картын өмнө гаргах
            if (c1.isWrongInLastRound()) return -1;
            if (c2.isWrongInLastRound()) return 1;
            
            return 0;
        });

        return sortedCards;
    }
}