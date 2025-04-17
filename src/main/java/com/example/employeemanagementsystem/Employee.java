package com.example.employeemanagementsystem;

public class Employee<T> {
    T employeeId;
    protected String name;
    protected String department;
    protected double salary;
    protected double performanceRating;
    int yearsOfExperience;
    boolean isActive;

    Employee(T employeeId, String name, String department,
             double salary, double performanceRating, int yearsOfExperience,
             boolean isActive) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.performanceRating = performanceRating;
        this.yearsOfExperience = yearsOfExperience;
        this.isActive = isActive;
    }
}
