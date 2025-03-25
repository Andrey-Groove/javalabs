package org.example;

public class Department {
    private long id;
    private String name;
    private static long departmentIdCounter = 1;

    public Department(String name) {
        this.id = departmentIdCounter++;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Department{id=" + id + ", name='" + name + "'}";
    }


}
