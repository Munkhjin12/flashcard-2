package flashcard;

public class FlashCard {
    private final String question;
    private final String answer;
    private int totalAttempts = 0;
    private int correctAttempts = 0;
    private int consecutiveCorrect = 0; // Дараалсан зөв хариулт
    private boolean isWrongInLastRound = false;
    private long lastFailedTime = 0;

    public FlashCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public void recordAttempt(boolean correct) {
        this.totalAttempts++;
        if (correct) {
            this.correctAttempts++;
            this.consecutiveCorrect++;
            this.isWrongInLastRound = false;
        } else {
            this.isWrongInLastRound = true;
            this.consecutiveCorrect = 0; // Буруу бол 0 болно
            this.lastFailedTime = System.nanoTime();
        }
    }

    // Тест болон Сортлогчид хэрэгтэй функцүүд
    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
    public int getTotalAttempts() { return totalAttempts; }
    public int getCorrectAttempts() { return correctAttempts; }
    public int getConsecutiveCorrect() { return consecutiveCorrect; }
    public boolean isWrongInLastRound() { return isWrongInLastRound; }
    public long getLastFailedTime() { return lastFailedTime; }
}