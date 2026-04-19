package flashcard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses flash card files in the following format:
 *
 * <pre>
 * # Lines starting with # are comments and are ignored
 * # Each card is defined by a Q: and A: pair
 * Q: What is Java?
 * A: A programming language
 *
 * Q: What is OOP?
 * A: Object-Oriented Programming
 * </pre>
 */
public class CardFileParser {
    /**
     * Constructs a new CardFileParser.
     */
    public CardFileParser() {}


    /**
     * Parses a card file and returns a list of FlashCards.
     *
     * @param filePath path to the card file
     * @return list of parsed FlashCards
     * @throws IOException if the file cannot be read
     * @throws IllegalArgumentException if the file format is invalid
     */
    public List<FlashCard> parse(String filePath) throws IOException {
        List<FlashCard> cards = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String currentQuestion = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Skip empty lines and comments
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                if (line.startsWith("Q:")) {
                    currentQuestion = line.substring(2).trim();
                } else if (line.startsWith("A:")) {
                    if (currentQuestion == null) {
                        throw new IllegalArgumentException(
                            "Answer found without a preceding question in file: " + filePath);
                    }
                    String answer = line.substring(2).trim();
                    cards.add(new FlashCard(currentQuestion, answer));
                    currentQuestion = null;
                } else {
                    throw new IllegalArgumentException(
                        "Invalid line format (expected 'Q:' or 'A:'): " + line);
                }
            }

            if (currentQuestion != null) {
                throw new IllegalArgumentException(
                    "Question without answer at end of file: " + currentQuestion);
            }
        }

        if (cards.isEmpty()) {
            throw new IllegalArgumentException("No cards found in file: " + filePath);
        }

        return cards;
    }
}
