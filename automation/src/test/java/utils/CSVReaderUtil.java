package utils;

import java.io.BufferedReader;
import java.io.FileReader;

public class CSVReaderUtil {
    public static String[] getCredentials() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/credentials.csv"));
        reader.readLine(); // skip header
        String line = reader.readLine();
        reader.close();
        return line.split(",");
    }
}
