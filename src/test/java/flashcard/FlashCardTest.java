package flashcard;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the flashcard system.
 */
public class FlashCardTest {

    @Test
    void testFlashCardCreation() {
        FlashCard card = new FlashCard("Java гэж юу вэ?", "Программчлалын хэл");
        assertEquals("Java гэж юу вэ?", card.getQuestion());
        assertEquals("Программчлалын хэл", card.getAnswer());
        assertEquals(0, card.getTotalAttempts());
        assertEquals(0, card.getCorrectAttempts());
    }

    @Test
    void testRecordCorrectAttempt() {
        FlashCard card = new FlashCard("Q", "A");
        card.recordAttempt(true);
        assertEquals(1, card.getTotalAttempts());
        assertEquals(1, card.getCorrectAttempts());
        assertEquals(1, card.getConsecutiveCorrect());
        assertFalse(card.isWrongInLastRound());
    }

    @Test
    void testRecordWrongAttempt() {
        FlashCard card = new FlashCard("Q", "A");
        card.recordAttempt(false);
        assertEquals(1, card.getTotalAttempts());
        assertEquals(0, card.getCorrectAttempts());
        assertEquals(0, card.getConsecutiveCorrect());
        assertTrue(card.isWrongInLastRound());
    }

    @Test
    void testConsecutiveCorrectResets() {
        FlashCard card = new FlashCard("Q", "A");
        card.recordAttempt(true);
        card.recordAttempt(true);
        assertEquals(2, card.getConsecutiveCorrect());
        card.recordAttempt(false);
        assertEquals(0, card.getConsecutiveCorrect());
    }

    @Test
    void testRandomSorterReturnsSameCards() {
        FlashCard c1 = new FlashCard("Q1", "A1");
        FlashCard c2 = new FlashCard("Q2", "A2");
        FlashCard c3 = new FlashCard("Q3", "A3");
        List<FlashCard> cards = Arrays.asList(c1, c2, c3);

        RandomSorter sorter = new RandomSorter();
        List<FlashCard> result = sorter.organize(cards);

        assertEquals(3, result.size());
        assertTrue(result.containsAll(cards));
    }

    @Test
    void testRecentMistakesFirstSorterOrdering() {
        FlashCard c1 = new FlashCard("Q1", "A1");
        FlashCard c2 = new FlashCard("Q2", "A2");
        FlashCard c3 = new FlashCard("Q3", "A3");

        // Mark c2 as wrong in last round
        c2.recordAttempt(false);

        List<FlashCard> cards = Arrays.asList(c1, c2, c3);
        RecentMistakesFirstSorter sorter = new RecentMistakesFirstSorter();
        List<FlashCard> result = sorter.organize(cards);

        // c2 should be first
        assertEquals(c2, result.get(0));
        assertEquals(3, result.size());
    }

    @Test
    void testRecentMistakesFirstPreservesRelativeOrder() {
        FlashCard c1 = new FlashCard("Q1", "A1");
        FlashCard c2 = new FlashCard("Q2", "A2");
        FlashCard c3 = new FlashCard("Q3", "A3");
        FlashCard c4 = new FlashCard("Q4", "A4");

        c1.recordAttempt(false); // wrong
        c3.recordAttempt(false); // wrong

        List<FlashCard> cards = Arrays.asList(c1, c2, c3, c4);
        RecentMistakesFirstSorter sorter = new RecentMistakesFirstSorter();
        List<FlashCard> result = sorter.organize(cards);

        // Wrong cards come first in original relative order
        assertEquals(c1, result.get(0));
        assertEquals(c3, result.get(1));
        // Correct cards follow in original relative order
        assertEquals(c2, result.get(2));
        assertEquals(c4, result.get(3));
    }

    @Test
    void testWorstFirstSorterOrdering() {
        FlashCard c1 = new FlashCard("Q1", "A1");
        FlashCard c2 = new FlashCard("Q2", "A2");
        FlashCard c3 = new FlashCard("Q3", "A3");

        // c1: 3 wrong out of 3 (100% error)
        c1.recordAttempt(false);
        c1.recordAttempt(false);
        c1.recordAttempt(false);

        // c2: 1 wrong out of 3 (33% error)
        c2.recordAttempt(false);
        c2.recordAttempt(true);
        c2.recordAttempt(true);

        // c3: no attempts
        List<FlashCard> cards = Arrays.asList(c2, c3, c1);
        WorstFirstSorter sorter = new WorstFirstSorter();
        List<FlashCard> result = sorter.organize(cards);

        assertEquals(c1, result.get(0)); // worst first
    }

    @Test
    void testAchievementManagerCorrect() {
        FlashCard c1 = new FlashCard("Q1", "A1");
        c1.recordAttempt(true);

        AchievementManager manager = new AchievementManager();
        manager.checkAfterRound(Arrays.asList(c1), true, 10.0);

        assertTrue(manager.getEarned().contains(AchievementManager.CORRECT));
    }

    @Test
    void testAchievementManagerSpeedy() {
        FlashCard c1 = new FlashCard("Q1", "A1");
        AchievementManager manager = new AchievementManager();
        manager.checkAfterRound(Arrays.asList(c1), false, 3.0);

        assertTrue(manager.getEarned().contains(AchievementManager.SPEEDY));
    }

    @Test
    void testAchievementManagerRepeat() {
        FlashCard c1 = new FlashCard("Q1", "A1");
        for (int i = 0; i < 5; i++) {
            c1.recordAttempt(i % 2 == 0);
        }
        AchievementManager manager = new AchievementManager();
        manager.checkAfterRound(Arrays.asList(c1), false, 10.0);

        assertTrue(manager.getEarned().contains(AchievementManager.REPEAT));
    }

    @Test
    void testAchievementManagerConfident() {
        FlashCard c1 = new FlashCard("Q1", "A1");
        c1.recordAttempt(true);
        c1.recordAttempt(true);
        c1.recordAttempt(true);

        AchievementManager manager = new AchievementManager();
        manager.checkAfterRound(Arrays.asList(c1), true, 10.0);

        assertTrue(manager.getEarned().contains(AchievementManager.CONFIDENT));
    }
}
