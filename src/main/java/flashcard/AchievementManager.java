package flashcard;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages achievements earned during a flashcard session.
 */
public class AchievementManager {
    /**
     * Constructs a new AchievementManager.
     */
    public AchievementManager() {}


    /** Achievement: All cards correct in the last round. */
    public static final String CORRECT = "CORRECT";

    /** Achievement: Answered a single card 5 or more times. */
    public static final String REPEAT = "REPEAT";

    /** Achievement: Answered a single card correctly at least 3 times. */
    public static final String CONFIDENT = "CONFIDENT";

    /** Achievement: Answered in under 5 seconds average per round. */
    public static final String SPEEDY = "SPEEDY";

    private final List<String> earned = new ArrayList<>();

    /**
     * Checks achievements after each round.
     *
     * @param cards          the full list of cards
     * @param allCorrect     whether all cards were correct in this round
     * @param avgTimeSeconds average time in seconds for this round
     */
    public void checkAfterRound(List<FlashCard> cards, boolean allCorrect, double avgTimeSeconds) {
        if (allCorrect && !earned.contains(CORRECT)) {
            earned.add(CORRECT);
            printAchievement(CORRECT, "Сүүлийн тойрогт бүх картыг зөв хариуллаа!");
        }

        if (avgTimeSeconds < 5.0 && !earned.contains(SPEEDY)) {
            earned.add(SPEEDY);
            printAchievement(SPEEDY, "Дундажаар 5 секундээс доош хугацаанд хариуллаа!");
        }

        for (FlashCard card : cards) {
            if (card.getTotalAttempts() >= 5 && !earned.contains(REPEAT)) {
                earned.add(REPEAT);
                printAchievement(REPEAT, "Нэг картад 5-аас олон удаа хариуллаа!");
            }
            if (card.getCorrectAttempts() >= 3 && !earned.contains(CONFIDENT)) {
                earned.add(CONFIDENT);
                printAchievement(CONFIDENT, "Нэг картад дор хаяж 3 удаа зөв хариуллаа!");
            }
        }
    }

    /**
     * Prints an achievement notification.
     *
     * @param name        achievement name
     * @param description description of the achievement
     */
    private void printAchievement(String name, String description) {
        System.out.println("\n🏆 АМЖИЛТ ОЛГОЛОО: [" + name + "] - " + description);
    }

    /**
     * Returns all earned achievements.
     *
     * @return list of earned achievement names
     */
    public List<String> getEarned() {
        return new ArrayList<>(earned);
    }
}
