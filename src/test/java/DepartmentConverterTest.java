package org.example;

import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentConverterTest {

    @Test
    void testConvertToDepartments_ValidData() {
        List<String[]> csvData = Arrays.asList(
                new String[]{ "", "C", "Corporate", "" },
                new String[]{ "", "CCA", "Corporate Compliance & Audit", "C" },
                new String[]{ "", "CCM", "Corporate Communications", "C" },
                new String[]{ "", "CHR", "Corporate HR", "C" },
                new String[]{ "#", "CHR-D", "Corporate HR Development", "CHR" },
                new String[]{ "", "F", "Finance", "" }
        );

        List<Department> departments = DepartmentConverter.convertToDepartments(csvData);

        assertNotNull(departments, "Departments list should not be null");
        assertEquals(2, departments.size(), "There should be two root departments");

        // Root departments: Corporate and Finance
        Department corporateDepartment = departments.get(0);
        assertEquals("Corporate", corporateDepartment.getName(), "Corporate department name should be 'Corporate'");
        assertEquals(3, corporateDepartment.getChildren().size(), "Corporate department should have three children");

        Department financeDepartment = departments.get(1);
        assertEquals("Finance", financeDepartment.getName(), "Finance department name should be 'Finance'");
        assertTrue(financeDepartment.getChildren().isEmpty(), "Finance department should have no children");

        // Child departments of Corporate
        Department complianceDepartment = corporateDepartment.getChildren().get(0);
        assertEquals("Corporate Compliance & Audit", complianceDepartment.getName(), "First child of Corporate should be 'Corporate Compliance & Audit'");

        Department communicationsDepartment = corporateDepartment.getChildren().get(1);
        assertEquals("Corporate Communications", communicationsDepartment.getName(), "Second child of Corporate should be 'Corporate Communications'");

        Department hrDepartment = corporateDepartment.getChildren().get(2);
        assertEquals("Corporate HR", hrDepartment.getName(), "Third child of Corporate should be 'Corporate HR'");

    }
    @Test
    void testConvertToDepartments_Failed_InvalidParentDepartment() {
        List<String[]> csvData = Arrays.asList(
                new String[]{ "", "C", "Corporate", "" },
                new String[]{ "", "XYZ", "Test Department", "999" }
        );

        List<Department> departments = DepartmentConverter.convertToDepartments(csvData);

        // Check that only the root department (Corporate) is added
        assertNotNull(departments, "Departments list should not be null");
        assertEquals(1, departments.size(), "There should be one root department (Corporate)");

        Department corporateDepartment = departments.get(0);
        assertEquals("Corporate", corporateDepartment.getName(), "Corporate department name should be 'Corporate'");
        assertTrue(corporateDepartment.getChildren().isEmpty(), "Corporate department should have no children");

        boolean foundInvalidDepartment = departments.stream()
                .anyMatch(department -> "XYZ".equals(department.getId()));

        assertFalse(foundInvalidDepartment, "Department with invalid parent ID should not be added.");
    }


}
