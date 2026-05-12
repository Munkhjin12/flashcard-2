package flashcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FlashCardSession {
    private final SessionConfig config;
    private final CardOrganizer organizer;
    private final AchievementManager achievements;
    private final Scanner scanner;

    public FlashCardSession(SessionConfig config, CardOrganizer organizer) {
        this.config = config;
        this.organizer = organizer;
        this.achievements = new AchievementManager();
        this.scanner = new Scanner(System.in);
    }

    public void run(List<FlashCard> allCards) {
        Map<FlashCard, Integer> correctCounts = new HashMap<>();
        for (FlashCard card : allCards) {
            correctCounts.put(card, 0);
        }

        List<FlashCard> remaining = new ArrayList<>(allCards);
        int roundNumber = 0;

        System.out.println("\n=== Flashcard сургах систем ===");
        System.out.println("Картын тоо: " + allCards.size());
        System.out.println("Давталт шаардлага: " + config.getRepetitions());
        System.out.println("Дараалал: " + config.getOrder());
        System.out.println("Урвуу горим: " + (config.isInvertCards() ? "тийм" : "үгүй"));
        System.out.println("Гарах: 'quit' эсвэл 'q' гэж бичнэ үү.\n");

        while (!remaining.isEmpty()) {
            roundNumber++;
            // Жагсаалтыг шинэчлэн сортлох
            remaining = organizer.organize(remaining);

            System.out.println("--- Тойрог " + roundNumber + " | Үлдсэн карт: " + remaining.size() + " ---\n");

            List<FlashCard> toRemove = new ArrayList<>();
            long roundStart = System.currentTimeMillis();
            boolean allCorrectThisRound = true;

            for (FlashCard card : remaining) {
                String question = config.isInvertCards() ? card.getAnswer() : card.getQuestion();
                String expectedAnswer = config.isInvertCards() ? card.getQuestion() : card.getAnswer();

                System.out.println("Асуулт: " + question);
                System.out.print("Хариулт: ");
                String userInput = scanner.nextLine().trim();

                if (userInput.equalsIgnoreCase("quit") || userInput.equalsIgnoreCase("q")) {
                    System.out.println("\nСесс дуусав. Баяртай!");
                    printSummary(allCards, roundNumber);
                    return;
                }

                boolean correct = userInput.equalsIgnoreCase(expectedAnswer);
                card.recordAttempt(correct);

                if (correct) {
                    int newCount = correctCounts.get(card) + 1;
                    correctCounts.put(card, newCount);
                    System.out.println("✓ Зөв! (" + newCount + "/" + config.getRepetitions() + ")");
                    if (newCount >= config.getRepetitions()) {
                        toRemove.add(card);
                    }
                } else {
                    System.out.println("✗ Буруу. Зөв хариулт: " + expectedAnswer);
                    allCorrectThisRound = false;
                }
                System.out.println();
            }

            remaining.removeAll(toRemove);
            long roundEnd = System.currentTimeMillis();
            double avgTime = (double) (roundEnd - roundStart) / 1000.0 / (remaining.size() + toRemove.size());
            achievements.checkAfterRound(allCards, allCorrectThisRound, avgTime);

            System.out.println("--- Тойрог " + roundNumber + " дууслаа | Дундаж хугацаа: " 
                + String.format("%.1f", avgTime) + " сек/карт ---\n");
        }

        System.out.println("\n🎉 Бүх карт дууслаа! Баяртай!");
        printSummary(allCards, roundNumber);
    }

    private void printSummary(List<FlashCard> cards, int roundNumber) {
        System.out.println("\n=== Сессийн тайлан ===");
        System.out.println("Нийт тойрог: " + roundNumber);
        
        int totalAttempts = 0;
        int totalCorrect = 0;

        for (FlashCard card : cards) {
            totalAttempts += card.getTotalAttempts();
            totalCorrect += card.getCorrectAttempts();
        }

        System.out.println("Нийт хариулт: " + totalAttempts);
        System.out.println("Зөв хариулт: " + totalCorrect);

        if (totalAttempts > 0) {
            double pct = (double) totalCorrect / totalAttempts * 100;
            System.out.printf("Үнэн зөвийн хувь: %.1f%%%n", pct);
        }

        List<String> earned = achievements.getEarned();
        if (!earned.isEmpty()) {
            System.out.println("Олсон амжилтууд: " + String.join(", ", earned));
        }
        System.out.println("======================");
    }
}