package org.example;

import java.util.*;

public class DepartmentConverter {


    public static List<Department> convertToDepartments(List<String[]> csvData) {
        Map<String, Department> departmentMap = new HashMap<>();
        List<Department> rootDepartments = new ArrayList<>();

        for (String[] row : csvData) {
            try {

                if (row.length < 3) {

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


                if (parentDepartmentId.isEmpty()) {
                    rootDepartments.add(department);
                } else {

                    Department parentDepartment = departmentMap.get(parentDepartmentId);
                    if (parentDepartment != null) {
                        parentDepartment.addChild(department);
                    } else {

                        System.err.println("Warning: Parent department not found for department ID " + departmentId + ": " + parentDepartmentId);
                    }
                }
            } catch (Exception exp) {

                System.err.println("Error processing row: " + Arrays.toString(row));
                exp.printStackTrace();
            }
        }


        return rootDepartments;
    }

}