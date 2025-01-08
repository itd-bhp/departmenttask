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


        List<Department> departments = List.of(dept1);


        String xml = XMLConverter.convertToXogXML(departments);


        assertNotNull(xml, "XML content should not be null");
        assertTrue(xml.contains("<NikuDataBus"), "XML should contain the root element <NikuDataBus>");
        assertTrue(xml.contains("<Header action='write'"), "XML should contain the <Header> element");
        assertTrue(xml.contains("<Departments>"), "XML should contain the <Departments> element");
        assertTrue(xml.contains("<Department department_code='C'"), "XML should contain the department with ID 'C'");
        assertTrue(xml.contains("<Description>Corporate</Description>"), "XML should contain the description 'Corporate'");
    }

    @Test
    void testConvertToXogXML_EmptyDepartments() {

        List<Department> departments = List.of();


        String xml = XMLConverter.convertToXogXML(departments);


        assertNotNull(xml, "XML content should not be null");
        assertTrue(xml.contains("<NikuDataBus"), "XML should contain the root element <NikuDataBus>");
        assertTrue(xml.contains("<Header action='write'"), "XML should contain the <Header> element");
        assertTrue(xml.contains("<Departments>"), "XML should contain the <Departments> element");
        assertTrue(xml.contains("</Departments>"), "XML should properly close the <Departments> tag");
    }

    @Test
    void testWriteXMLToFile() throws IOException {

        Department dept1 = new Department("C", "Corporate", "");
        Department dept2 = new Department("CCA", "Corporate Compliance & Audit", "C");
        dept1.addChild(dept2);

        List<Department> departments = List.of(dept1);
        String xmlContent = XMLConverter.convertToXogXML(departments);


        File tempFile = new File("temp_test.xml");
        XMLConverter.writeXMLToFile(xmlContent, tempFile.getAbsolutePath());

        assertTrue(tempFile.exists(), "The file should be created");
        assertTrue(tempFile.length() > 0, "The file should not be empty");

        tempFile.delete();
    }

}
