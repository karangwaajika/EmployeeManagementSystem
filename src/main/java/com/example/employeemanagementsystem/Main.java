package com.example.employeemanagementsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Database<Integer> db = new Database<>(); // initialize database

        Employee<Integer> employee1 = new Employee<>(1,
                "ajika", "HR", 2000, 2,
                1, true);
        Employee<Integer> employee2 = new Employee<>(2,
                "joel", "Finance", 1000, 2,
                3, true);

        // insert employee to a list
        ArrayList<Employee<Integer>> ls = new ArrayList<>();
        ls.add(employee1);
        ls.add(employee2);

        // sort by years of experience descending order
        Comparator<Employee<Integer>> comparator = new Comparator<Employee<Integer>>() {
            @Override
            public int compare(Employee<Integer> o1, Employee<Integer> o2) {
                if (o1.yearsOfExperience < o2.yearsOfExperience) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };

        Collections.sort(ls, comparator);

        // display employees from list
        ls.forEach((employee) -> {
            System.out.printf("%d| name:%s, dep: %s, years: %d \n", employee.employeeId, employee.name,
                    employee.department, employee.yearsOfExperience);
        });

        // insert employee to the database
        db.addEmployee(employee1.employeeId, employee1);
        db.addEmployee(employee2.employeeId, employee2);

        // get all the employees from the database
        System.out.println(db.getAllEmployees());


    }
}
