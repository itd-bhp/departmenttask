package org.example;

import java.util.*;

public class Department {
    String id;
    String name;
    String parentDeptId;
    List<Department> children = new ArrayList<>();

    public Department(String id, String name, String parentDeptId) {
        this.id = id;
        this.name = name;
        this.parentDeptId = parentDeptId;
    }

    // Add a child department
    public void addChild(Department child) {
        children.add(child);
    }

    // Get child departments
    public List<Department> getChildren() {
        return children;
    }

    public String getParentDeptId() {
        return parentDeptId;
    }
}
