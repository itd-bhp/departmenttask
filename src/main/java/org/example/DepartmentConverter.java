package org.example;

import java.util.*;

public class DepartmentConverter {

    // Convert CSV data to Department objects
    public static List<Department> convertToDepartments(List<String[]> csvData) {
        Map<String, Department> departmentMap = new HashMap<>();
        List<Department> rootDepartments = new ArrayList<>();

        for (String[] row : csvData) {
            try {
                // Check if the row has the expected number of columns
                if (row.length < 3) {
                    // Log a warning for incomplete rows (skip them)
                    System.err.println("Warning: Skipping incomplete row (not enough columns): " + Arrays.toString(row));
                    continue;
                }
                String departmentId = row[1].trim();
                String departmentName = row[2].trim();
                String parentDepartmentId = (row.length > 3) ? row[3].trim() : "";

                if (departmentId.isEmpty() || departmentName.isEmpty()) {
                    System.err.println("Error: Invalid department data (missing ID or name) in row: " + Arrays.toString(row));
                    continue;
                }

                Department department = new Department(departmentId, departmentName, parentDepartmentId);
                departmentMap.put(departmentId, department);

                // If there is no parent, add to root departments
                if (parentDepartmentId.isEmpty()) {
                    rootDepartments.add(department);
                } else {
                    // If parent department is found, link as a child of the parent
                    Department parentDepartment = departmentMap.get(parentDepartmentId);
                    if (parentDepartment != null) {
                        parentDepartment.addChild(department);
                    } else {
                        // Log a warning if the parent department doesn't exist
                        System.err.println("Warning: Parent department not found for department ID " + departmentId + ": " + parentDepartmentId);
                    }
                }
            } catch (Exception e) {
                // Catch any unexpected exceptions that might arise while processing a row
                System.err.println("Error processing row: " + Arrays.toString(row));
                e.printStackTrace();
            }
        }

        // Return the list of root departments (no need for second loop to rebind children)
        return rootDepartments;
    }

}