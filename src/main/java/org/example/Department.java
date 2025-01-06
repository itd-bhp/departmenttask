package org.example;

import java.util.*;

public class Department {
    String id;
    String name;
    String parentDeptId;
    List<Department> children = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Department(String id, String name, String parentDeptId) {
        this.id = id;
        this.name = name;
        this.parentDeptId = parentDeptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void addChild(Department child) {
        children.add(child);
    }


    public List<Department> getChildren() {
        return children;
    }

    public String getParentDeptId() {
        return parentDeptId;
    }
}
