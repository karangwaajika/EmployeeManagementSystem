package com.example.employeemanagementsystem;

import java.util.HashMap;

public class Database<T> {
    HashMap<T, Employee<T>> employees = new HashMap<>();

    public String addEmployee(T employeeId, Employee<T> employee) {
        employees.put(employeeId, employee);
        return "Successfully added";
    }

    public HashMap<T, Employee<T>> getAllEmployees() {
        return this.employees;
    }

}
