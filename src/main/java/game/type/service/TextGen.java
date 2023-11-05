package game.type.service;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TextGen {
    private static final String CSV_FILE = "/Users/darshan/Documents/DynoType/words.csv"; // Replace with your CSV file path
    private static final int WORDS_IN_PARAGRAPH = 50;
    private static List<String> wordList = new ArrayList<>();

    public TextGen() {
        loadWordsFromCSV(CSV_FILE); // Load words from the CSV once
    }

    public static void loadWordsFromCSV(String csvFilePath) {
        if (!wordList.isEmpty()) {
            return; // Words are already loaded, so no need to load them again
        }

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                wordList.add(nextLine[0]); // Assuming the words are in the first column
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String generateRandomParagraph() {
        if (wordList.isEmpty()) {
            return "Word list is empty.";
        }

        Random random = new Random();
        StringBuilder paragraph = new StringBuilder();
        for (int i = 0; i < WORDS_IN_PARAGRAPH; i++) {
            int randomIndex = random.nextInt(wordList.size());
            paragraph.append(wordList.get(randomIndex)).append(" ");
        }
        return paragraph.toString().trim(); // Remove the trailing space
    }
}
