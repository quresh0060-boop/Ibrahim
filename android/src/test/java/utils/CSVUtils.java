package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {

    public static List<String[]> readCSV(String filePath) throws Exception {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    records.add(line.split(","));
                }
            }
        }
        if (records.isEmpty()) {
            throw new RuntimeException("CSV is empty or not found: " + filePath);
        }
        return records;
    }
}
