package com.example.employeemanagementsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Database<Integer> db = new Database<>(); // initialize database

        Employee<Integer> employee1 = new Employee<>(1,
                "ajika", "HR", 2000, 2.5,
                1, true);
        Employee<Integer> employee2 = new Employee<>(2,
                "joel", "Finance", 1000, 3,
                3, true);
        Employee<Integer> employee3 = new Employee<>(3,
                "joella", "HR", 400, 4.5,
                3, true);

        // insert employee to a list
        ArrayList<Employee<Integer>> ls = new ArrayList<>();
        ls.add(employee1);
        ls.add(employee2);

        // sort by years of experience descending order
        Comparator<Employee<Integer>> comparator = new Comparator<Employee<Integer>>() {
            @Override
            public int compare(Employee<Integer> o1, Employee<Integer> o2) {
                if (o1.getYearsOfExperience() < o2.getYearsOfExperience()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };

        Collections.sort(ls, comparator);

        // display employees from list
        ls.forEach((employee) -> {
            System.out.printf("%d| name:%s, dep: %s, years: %d \n", employee.employeeId, employee.getName(),
                    employee.getDepartment(), employee.getYearsOfExperience());
        });
        System.out.println("############ END TASK 1 #####################");

// ###################################  END TASK 1 ###############################################

        // insert employee to the database
        db.addEmployee(employee1.employeeId, employee1);
        db.addEmployee(employee2.employeeId, employee2);
        db.addEmployee(employee3.employeeId, employee3);

        // get all the employees from the database
        System.out.println(db.getAllEmployees());

        db.updateEmployeeDetails(1, "yearsOfExperience", 4);
        System.out.println(db.removeEmployee(4));

        System.out.println(db.getAllEmployees());
        ls.forEach((employee) -> {
            System.out.printf("%d| name:%s, dep: %s, years: %d \n", employee.employeeId, employee.getName(),
                    employee.getDepartment(), employee.getYearsOfExperience());
        });
        System.out.println("############ END TASK 2 #####################");

// ###################################  END TASK 2 ###############################################

        //filter by department
        System.out.println("###### search by department #####");
        if (db.filterByDepartment("Finance").toArray().length == 0) {
            System.out.println("nothing");
        } else {
            db.filterByDepartment("Finance")
                    .forEach(n -> System.out.println(n.getName()));
        }

        System.out.println("##### search by name #####");
        //filter by name as auto complete
        if (db.filterByName("j").toArray().length == 0) {
            System.out.println("nothing found");
        } else {
            db.filterByName("j")
                    .forEach(n -> System.out.println(n.getName()));
        }

        System.out.println("##### minimum rating ######");
        //retrieve minimum rating
        if (db.searchMinimumRating(3).toArray().length == 0) {
            System.out.println("nothing found");
        } else {
            db.searchMinimumRating(3)
                    .forEach(n -> System.out.println(n.getName()));
        }
    }
}
