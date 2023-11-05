package game.type.service;

public class PlayerAnalyticsGen {
    private int wpm; // Words per minute
    private int accuracy; // Accuracy percentage

    public PlayerAnalyticsGen(int timeElapsedInSeconds, int numCorrect, int numIncorrect) {
        int totalWords = numCorrect + numIncorrect;
        this.wpm = calculateWPM(totalWords, timeElapsedInSeconds);
        this.accuracy = calculateAccuracy(numCorrect, totalWords);
    }

    private int calculateWPM(int totalWords, int timeElapsedInSeconds) {
        // Ensure no division by zero
        if (timeElapsedInSeconds == 0) {
            return 0;
        }

        // Calculate WPM (words per minute)
        double wordsPerSecond = (double) totalWords / timeElapsedInSeconds;
        return (int) (wordsPerSecond * 60);
    }

    private int calculateAccuracy(int numCorrect, int totalWords) {
        // Ensure no division by zero
        if (totalWords == 0) {
            return 100; // Maximum accuracy
        }

        // Calculate accuracy as a percentage
        double accuracyPercentage = ((double) numCorrect / totalWords) * 100;
        return (int) accuracyPercentage;
    }

    public int getWPM() {
        return wpm;
    }

    public int getAccuracy() {
        return accuracy;
    }
}

