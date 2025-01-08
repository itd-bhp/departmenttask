import org.example.CSVReader;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CSVReaderTest {

    @Test
    void testReadCSV_ValidData() throws IOException {

        List<String[]> data = CSVReader.readCSV("departments.csv");

        assertNotNull(data, "Data should not be null");
        assertFalse(data.isEmpty(), "Data should contain rows");

        assertArrayEquals(new String[]{ "", "C", "Corporate"}, data.get(0));
    }
    @Test
    void testReadCSV_FileNotFound() {

        assertThrows(FileNotFoundException.class, () -> {
            CSVReader.readCSV("nonexistent-file.csv");
        });
    }
}
