package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String csvFile = "departments.csv";
        try {
            // Read CSV data
            List<String[]> data = CSVReader.readCSV(csvFile);

            // Convert CSV to Department objects
            List<Department> departments = DepartmentConverter.convertToDepartments(data);

            // Convert to XML string
            String xmlString = XMLConverter.convertToXogXML(departments);

            // Print the XML and write to a file
            System.out.println(xmlString);
            XMLConverter.writeXMLToFile(xmlString, "output.xml");

        } catch (FileNotFoundException e) {
            System.err.println("Error: The CSV file '" + csvFile + "' was not found. Please check the file path.");
        } catch (IOException e) {
            System.err.println("Error: An I/O error occurred: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
