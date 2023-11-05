package game.type.service;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class TextGen {
    private static final String CSV_URL = "https://raw.githubusercontent.com/darshan-golchha/DynoType/main/words.csv";
    private static final int WORDS_IN_PARAGRAPH = 50;
    private static List<String> wordList = new ArrayList<>();

    public TextGen() {
        loadWordsFromCSV(CSV_URL); // Load words from the online CSV once
    }

    public static void loadWordsFromCSV(String csvUrl) {
        if (!wordList.isEmpty()) {
            return; // Words are already loaded, so no need to load them again
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(csvUrl).openStream()))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
            for (CSVRecord record : records) {
                wordList.add(record.get(0)); // Assuming the words are in the first column
            }
        } catch (Exception e) {
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