package org.example;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XMLConverterTest {

    @Test
    void testConvertToXogXML_ValidDepartments() {
        // Prepare: Create test departments for conversion
        Department dept1 = new Department("C", "Corporate", "");
        Department dept2 = new Department("CCA", "Corporate Compliance & Audit", "C");
        Department dept3 = new Department("F", "Finance", "");
        dept1.addChild(dept2);
        //dept1.addChild(dept3);

        List<Department> departments = List.of(dept1);

        // Act: Convert the departments to XML
        String xml = XMLConverter.convertToXogXML(departments);

        // Assert: Check if XML structure is correct
        assertNotNull(xml, "XML content should not be null");
        assertTrue(xml.contains("<NikuDataBus"), "XML should contain the root element <NikuDataBus>");
        assertTrue(xml.contains("<Header action='write'"), "XML should contain the <Header> element");
        assertTrue(xml.contains("<Departments>"), "XML should contain the <Departments> element");
        assertTrue(xml.contains("<Department department_code='C'"), "XML should contain the department with ID 'C'");
        assertTrue(xml.contains("<Description>Corporate</Description>"), "XML should contain the description 'Corporate'");
    }

    @Test
    void testConvertToXogXML_EmptyDepartments() {
        // Prepare: Empty list of departments
        List<Department> departments = List.of();

        // Act: Convert to XML
        String xml = XMLConverter.convertToXogXML(departments);

        // Assert: Check if XML structure is still correct even if no departments exist
        assertNotNull(xml, "XML content should not be null");
        assertTrue(xml.contains("<NikuDataBus"), "XML should contain the root element <NikuDataBus>");
        assertTrue(xml.contains("<Header action='write'"), "XML should contain the <Header> element");
        assertTrue(xml.contains("<Departments>"), "XML should contain the <Departments> element");
        assertTrue(xml.contains("</Departments>"), "XML should properly close the <Departments> tag");
    }

    @Test
    void testWriteXMLToFile() throws IOException {
        // Prepare: Create test departments for conversion
        Department dept1 = new Department("C", "Corporate", "");
        Department dept2 = new Department("CCA", "Corporate Compliance & Audit", "C");
        dept1.addChild(dept2);

        List<Department> departments = List.of(dept1);
        String xmlContent = XMLConverter.convertToXogXML(departments);

        // Prepare a temporary file to write the XML content
        File tempFile = new File("temp_test.xml");

        // Act: Write the XML to a file
        XMLConverter.writeXMLToFile(xmlContent, tempFile.getAbsolutePath());

        // Assert: Check if the file is created
        assertTrue(tempFile.exists(), "The file should be created");
        assertTrue(tempFile.length() > 0, "The file should not be empty");

        // Clean up: Delete the temporary file after the test
        tempFile.delete();
    }

}
