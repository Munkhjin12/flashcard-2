package flashcard;

/**
 * Represents a single flash card with a question and answer.
 */
public class FlashCard {

    private final String question;
    private final String answer;
    private int totalAttempts;
    private int correctAttempts;
    private int consecutiveCorrect;
    private boolean wrongInLastRound;

    /**
     * Constructs a FlashCard with the given question and answer.
     *
     * @param question the question on the card
     * @param answer   the answer on the card
     */
    public FlashCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.totalAttempts = 0;
        this.correctAttempts = 0;
        this.consecutiveCorrect = 0;
        this.wrongInLastRound = false;
    }

    /**
     * Returns the question.
     *
     * @return question string
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns the answer.
     *
     * @return answer string
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Returns total number of attempts.
     *
     * @return total attempts
     */
    public int getTotalAttempts() {
        return totalAttempts;
    }

    /**
     * Returns number of correct attempts.
     *
     * @return correct attempts
     */
    public int getCorrectAttempts() {
        return correctAttempts;
    }

    /**
     * Returns consecutive correct answers count.
     *
     * @return consecutive correct count
     */
    public int getConsecutiveCorrect() {
        return consecutiveCorrect;
    }

    /**
     * Returns whether this card was answered wrong in the last round.
     *
     * @return true if wrong in last round
     */
    public boolean isWrongInLastRound() {
        return wrongInLastRound;
    }

    /**
     * Sets the wrong-in-last-round flag.
     *
     * @param wrong true if wrong in last round
     */
    public void setWrongInLastRound(boolean wrong) {
        this.wrongInLastRound = wrong;
    }

    /**
     * Records the result of an attempt.
     *
     * @param correct whether the attempt was correct
     */
    public void recordAttempt(boolean correct) {
        totalAttempts++;
        if (correct) {
            correctAttempts++;
            consecutiveCorrect++;
            wrongInLastRound = false;
        } else {
            consecutiveCorrect = 0;
            wrongInLastRound = true;
        }
    }

    /**
     * Returns a string representation of this card.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return "FlashCard{question='" + question + "', answer='" + answer + "'}";
    }
}
