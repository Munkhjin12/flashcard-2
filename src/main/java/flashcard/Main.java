package flashcard;

import java.io.IOException;
import java.util.List;

/**
 * Entry point for the Flashcard CLI application.
 *
 * <p>Usage: flashcard &lt;cards-file&gt; [options]
 *
 * <p>Options:
 * <ul>
 *   <li>--help             Show help information</li>
 *   <li>--order &lt;order&gt;    Card ordering: random, worst-first, recent-mistakes-first</li>
 *   <li>--repetitions &lt;n&gt;  Number of correct answers required per card</li>
 *   <li>--invertCards      Swap question and answer</li>
 * </ul>
 */
public class Main {

    /**
     * Private constructor — utility class, not instantiated.
     */
    private Main() {}

    private static final String DEFAULT_ORDER = "random";
    private static final int DEFAULT_REPETITIONS = 1;

    /**
     * Main method - entry point of the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // Check for --help first (can appear anywhere)
        for (String arg : args) {
            if (arg.equals("--help")) {
                printHelp();
                return;
            }
        }

        if (args.length == 0) {
            System.err.println("Алдаа: cards-file тодорхойлогдаагүй байна.");
            System.err.println("Тусламж авахын тулд --help ашиглана уу.");
            System.exit(1);
        }

        String cardsFile = args[0];
        String order = DEFAULT_ORDER;
        int repetitions = DEFAULT_REPETITIONS;
        boolean invertCards = false;

        // Parse options
        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "--order":
                    if (i + 1 >= args.length) {
                        System.err.println("Алдаа: --order тохируулгад утга шаардлагатай.");
                        System.exit(1);
                    }
                    order = args[++i];
                    if (!order.equals("random")
                        && !order.equals("worst-first")
                        && !order.equals("recent-mistakes-first")) {
                        System.err.println("Алдаа: --order утга буруу: " + order);
                        System.err.println("Сонголтууд: random, worst-first, "
                            + "recent-mistakes-first");
                        System.exit(1);
                    }
                    break;

                case "--repetitions":
                    if (i + 1 >= args.length) {
                        System.err.println("Алдаа: --repetitions тохируулгад утга шаардлагатай.");
                        System.exit(1);
                    }
                    try {
                        repetitions = Integer.parseInt(args[++i]);
                        if (repetitions < 1) {
                            System.err.println("Алдаа: --repetitions утга 1-ээс их байх ёстой.");
                            System.exit(1);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Алдаа: --repetitions тоон утга шаардлагатай: "
                            + args[i]);
                        System.exit(1);
                    }
                    break;

                case "--invertCards":
                    invertCards = true;
                    break;

                default:
                    System.err.println("Алдаа: Тодорхойгүй тохируулга: " + args[i]);
                    System.err.println("Тусламж авахын тулд --help ашиглана уу.");
                    System.exit(1);
            }
        }

        // Build config
        SessionConfig config = new SessionConfig(cardsFile, order, repetitions, invertCards);

        // Select organizer
        CardOrganizer organizer;
        switch (order) {
            case "worst-first":
                organizer = new WorstFirstSorter();
                break;
            case "recent-mistakes-first":
                organizer = new RecentMistakesFirstSorter();
                break;
            default:
                organizer = new RandomSorter();
        }

        // Load cards
        CardFileParser parser = new CardFileParser();
        List<FlashCard> cards;
        try {
            cards = parser.parse(cardsFile);
        } catch (IOException e) {
            System.err.println("Алдаа: Файлыг уншиж чадсангүй: " + cardsFile);
            System.err.println("Дэлгэрэнгүй: " + e.getMessage());
            System.exit(1);
            return;
        } catch (IllegalArgumentException e) {
            System.err.println("Алдаа: Файлын формат буруу: " + e.getMessage());
            System.exit(1);
            return;
        }

        // Run session
        FlashCardSession session = new FlashCardSession(config, organizer);
        session.run(cards);
    }

    /**
     * Prints the help message.
     */
    private static void printHelp() {
        System.out.println("Хэрэглээ: flashcard <cards-file> [options]");
        System.out.println();
        System.out.println("Тохируулгууд:");
        System.out.println("  --help                  Тусламжийн мэдээлэл харуулах");
        System.out.println("  --order <order>         Зохион байгуулалтын төрөл "
            + "(default: random)");
        System.out.println("                          Сонголт: random, worst-first, "
            + "recent-mistakes-first");
        System.out.println("  --repetitions <num>     Нэг картыг хэдэн удаа зөв "
            + "хариулахыг шаардах (default: 1)");
        System.out.println("  --invertCards           Картын асуулт, хариултыг сольж "
            + "харуулах (default: false)");
        System.out.println();
        System.out.println("Cards-file формат:");
        System.out.println("  # Тайлбар мөр (# тэмдэгтээр эхэлнэ)");
        System.out.println("  Q: Асуулт");
        System.out.println("  A: Хариулт");
        System.out.println();
        System.out.println("Жишээ:");
        System.out.println("  flashcard cards.txt");
        System.out.println("  flashcard cards.txt --order worst-first --repetitions 3");
        System.out.println("  flashcard cards.txt --invertCards --repetitions 2");
    }
}
