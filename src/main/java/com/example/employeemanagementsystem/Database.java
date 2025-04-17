package com.example.employeemanagementsystem;

import java.util.HashMap;

public class Database<T> {
    private final HashMap<T, Employee<T>> employees = new HashMap<>();
    private Employee<T> employee;

    public String addEmployee(T employeeId, Employee<T> employee) {
        employees.put(employeeId, employee);
        return "Employee's added successfully !!";
    }

    public HashMap<T, Employee<T>> getAllEmployees() {
        return employees;
    }

    public String removeEmployee(T employeeId) {
        employees.remove(employeeId);
        return "Employee's deleted successfully !!";
    }

    public void updateEmployeeDetails(T employeeId, String field, Object newValue) {

        Employee<T> employee = employees.get(employeeId);
        if (newValue instanceof Integer && field.equals("yearsOfExperience")) {
            employee.setYearsOfExperience((Integer) newValue);
        } else if (newValue instanceof Double) {
            if (field.equals("salary")) {
                employee.setSalary((Double) newValue);
            } else {
                employee.setPerformanceRating((Double) newValue);
            }
        } else if (newValue instanceof String) {
            if (field.equals("department")) {
                employee.setDepartment((String) newValue);
            } else {
                employee.setName((String) newValue);
            }
        } else {
            if (newValue instanceof Boolean) {
                employee.setIsActive((Boolean) newValue);
            }
        }
        System.out.println("Updated successfully ");

    }

}
