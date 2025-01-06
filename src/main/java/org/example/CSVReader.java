package org.example;

import java.io.*;
import java.util.*;

public class CSVReader {


    public static List<String[]> readCSV(String fileName) throws IOException {
        InputStream inputStream = CSVReader.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) throw new FileNotFoundException("File not found: " + fileName);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            List<String[]> data = new ArrayList<>();
            String line;


            while ((line = reader.readLine()) != null) {

                if (line.trim().startsWith("#") || line.trim().isEmpty()) {
                    continue;
                }


                try {
                    String[] values = line.split(",");
                    if (values.length < 3) {
                        System.err.println("Skipping incomplete row: " + Arrays.toString(values));
                        continue;  // Skip incomplete rows
                    }
                    data.add(values);
                } catch (Exception e) {
                    System.err.println("Error processing row: " + line);
                    e.printStackTrace();
                }
            }
            return data;
        } catch (IOException e) {
            throw new IOException("Error reading file: " + fileName, e);
        }
    }
}