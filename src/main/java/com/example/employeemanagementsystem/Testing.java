package com.example.employeemanagementsystem;

import java.util.*;

public class Testing {
    public static void main(String[] args) {
        Database<Integer> db = new Database<>(); // initialize database

        Employee<Integer> employee1 = new Employee<>(1,
                "ajika", "HR", 2000, 2.5,
                1, true);
        Employee<Integer> employee2 = new Employee<>(2,
                "joel", "Finance", 1000, 3,
                2, true);
        Employee<Integer> employee3 = new Employee<>(3,
                "joella", "HR", 400, 4.5,
                3, true);

        // insert employee to a list
        ArrayList<Employee<Integer>> ls = new ArrayList<>();
        ls.add(employee1);
        ls.add(employee2);
        ls.add(employee3);

        // sort by years of experience descending order using comparable
        Collections.sort(ls);

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

        System.out.println("####### range salary #########");
        if (db.searchRangeSalary(400, 1000).toArray().length == 0) {
            System.out.println("nothing found");
        } else {
            // using an iterator to traverse element
            List<Employee<Integer>> l = db.searchRangeSalary(400, 1000).toList();
            Iterator<Employee<Integer>> itr = l.iterator();
            while (itr.hasNext()) {
                Employee<Integer> employee = itr.next();
                System.out.println(employee.getName());
            }
        }

        System.out.println("############ END TASK 3 #####################");
        // ###################################  END TASK 3 ###############################################
        System.out.println("sort by years of experience");
        ArrayList<Employee<Integer>> a = db.sortByYearsOfExperience();
        Iterator<Employee<Integer>> itr = a.iterator();
        while (itr.hasNext()) {
            Employee<Integer> employee = itr.next();
            System.out.printf("%d| name:%s, dep: %s, years: %d \n", employee.employeeId, employee.getName(),
                    employee.getDepartment(), employee.getYearsOfExperience());
        }

        System.out.println("sort by salary");
        ArrayList<Employee<Integer>> employeesList = db.getAllEmployees();
        Collections.sort(employeesList, new EmployeeSalaryComparator<Integer>());
        Iterator<Employee<Integer>> iter = employeesList.iterator();
        while (iter.hasNext()) {
            Employee<Integer> employee = iter.next();
            System.out.printf("%d| name:%s, dep: %s, years: %d, salary: %f\n", employee.employeeId, employee.getName(),
                    employee.getDepartment(), employee.getYearsOfExperience(), employee.getSalary());
        }

        System.out.println("sort by rating performance");
        ArrayList<Employee<Integer>> dbList = db.getAllEmployees();
        Collections.sort(dbList, new EmployeePerformanceComparator<Integer>());
        Iterator<Employee<Integer>> t = dbList.iterator();
        while (t.hasNext()) {
            Employee<Integer> employee = t.next();
            System.out.printf("%d| name:%s, dep: %s, years: %d, salary: %f, rating: %f\n", employee.employeeId, employee.getName(),
                    employee.getDepartment(), employee.getYearsOfExperience()
                    , employee.getSalary(), employee.getPerformanceRating());
        }

        System.out.println("############ END TASK 4 #####################");
        // ###################################  END TASK 4 ###############################################

        System.out.println("give a salary raise");
        ArrayList<Employee<Integer>> empls = db.giveSalaryRaise(3);
        Iterator<Employee<Integer>> te = empls.iterator();
        while (te.hasNext()) {
            Employee<Integer> employee = te.next();
            System.out.printf("%d| name:%s, dep: %s, years: %d, salary: %f, rating: %f\n", employee.employeeId, employee.getName(),
                    employee.getDepartment(), employee.getYearsOfExperience()
                    , employee.getSalary(), employee.getPerformanceRating());
        }

        System.out.println("give top five highest paid");
        List<Employee<Integer>> empList = db.retrieveTopFiveHighestPaid();
        Iterator<Employee<Integer>> iterate = empList.iterator();
        while (iterate.hasNext()) {
            Employee<Integer> employee = iterate.next();
            System.out.printf("%d| name:%s, dep: %s, years: %d, salary: %f, rating: %f\n", employee.employeeId, employee.getName(),
                    employee.getDepartment(), employee.getYearsOfExperience()
                    , employee.getSalary(), employee.getPerformanceRating());
        }

    }
}
