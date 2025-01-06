package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class XMLConverter {


    public static String convertToXogXML(List<Department> departments) {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<NikuDataBus xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:noNamespaceSchemaLocation='../xsd/nikuxog_department.xsd'>\n")
                .append("<Header action='write' externalSource='NIKU' objectType='department' version='15.9' />\n")
                .append("<Departments>\n");

        for (Department dept : departments) {
            try {
                appendDepartmentXML(dept, xmlBuilder); // Handle each department conversion
            } catch (Exception e) {
                System.err.println("Error processing department ID " + dept.id + ": " + e.getMessage());
            }
        }

        xmlBuilder.append("</Departments>\n</NikuDataBus>");
        return xmlBuilder.toString();
    }


    private static void appendDepartmentXML(Department dept, StringBuilder xmlBuilder) {
        if (dept == null) {
            System.err.println("Error: Department is null, skipping...");
            return;
        }

        try {
            xmlBuilder.append("\t<Department department_code='").append(dept.id)
                    .append("' dept_manager_code=\"admin\" entity=\"Corporate\" short_description='").append(escapeXml(dept.name)).append("'>\n")
                    .append("\t\t<Description>").append(escapeXml(dept.name)).append("</Description>\n");

            for (Department child : dept.getChildren()) {
                appendDepartmentXML(child, xmlBuilder); // Recursively handle children
            }

            xmlBuilder.append("\t</Department>\n");
        } catch (Exception e) {
            System.err.println("Error building XML for department ID " + dept.id + ": " + e.getMessage());
        }
    }


    private static String escapeXml(String input) {
        if (input == null) {
            return ""; // Return empty string if input is null
        }

        try {
            return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&apos;");
        } catch (Exception e) {
            System.err.println("Error escaping XML for input: " + input);
            return ""; // Return empty string if there's an error in escaping
        }
    }

    public static void writeXMLToFile(String xmlContent, String fileName) throws IOException {
        if (xmlContent == null || xmlContent.isEmpty()) {
            System.err.println("Error: XML content is empty, cannot write to file.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(xmlContent);
        } catch (IOException e) {
            System.err.println("Error writing XML to file " + fileName + ": " + e.getMessage());
        }
    }
}