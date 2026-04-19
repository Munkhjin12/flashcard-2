package flashcard;

/**
 * Holds the configuration for a flashcard session.
 */
public class SessionConfig {

    private final String cardsFile;
    private final String order;
    private final int repetitions;
    private final boolean invertCards;

    /**
     * Constructs a SessionConfig.
     *
     * @param cardsFile   path to the cards file
     * @param order       ordering strategy (random, worst-first, recent-mistakes-first)
     * @param repetitions number of correct answers required per card
     * @param invertCards whether to invert question and answer
     */
    public SessionConfig(String cardsFile, String order, int repetitions, boolean invertCards) {
        this.cardsFile = cardsFile;
        this.order = order;
        this.repetitions = repetitions;
        this.invertCards = invertCards;
    }

    /**
     * Returns the cards file path.
     *
     * @return cards file path
     */
    public String getCardsFile() {
        return cardsFile;
    }

    /**
     * Returns the ordering strategy.
     *
     * @return order string
     */
    public String getOrder() {
        return order;
    }

    /**
     * Returns the number of repetitions required.
     *
     * @return repetitions count
     */
    public int getRepetitions() {
        return repetitions;
    }

    /**
     * Returns whether cards are inverted.
     *
     * @return true if cards should be inverted
     */
    public boolean isInvertCards() {
        return invertCards;
    }
}
