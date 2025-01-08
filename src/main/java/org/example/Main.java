package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String csvFile = "department.csv";
        try {

            List<String[]> data = CSVReader.readCSV(csvFile);


            List<Department> departments = DepartmentConverter.convertToDepartments(data);


            String xmlString = XMLConverter.convertToXogXML(departments);


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
