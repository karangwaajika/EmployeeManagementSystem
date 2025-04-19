package com.example.employeemanagementsystem;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.stream.Collector;

public class Database<T> {
    private final HashMap<T, Employee<T>> employees = new HashMap<>();
    private Employee<T> employee;

    public String addEmployee(T employeeId, Employee<T> employee) {
        employees.put(employeeId, employee);
        return "Employee's added successfully !!";
    }

    public ArrayList<Employee<T>> getAllEmployees() {
        return new ArrayList<Employee<T>>(employees.values());
    }

    public String removeEmployee(T employeeId) {
        if (employees.containsKey(employeeId)) {
            employees.remove(employeeId);
            return "Employee's deleted successfully !!";
        }
        return "Employee Id doesn't exist";

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

    public Stream<Employee<T>> filterByDepartment(String field) {
        return employees.values().stream()
                .filter(n -> n.getDepartment().equals(field));
    }

    public Stream<Employee<T>> filterByName(String name) {
        return employees.values().stream()
                .filter(n -> n.getName().contains(name));
    }

    public Stream<Employee<T>> searchMinimumRating(double rating) {
        return employees.values().stream()
                .filter(n -> n.getPerformanceRating() >= rating);
    }

    public Stream<Employee<T>> searchRangeSalary(double salary1, double salary2) {
        return employees.values().stream()
                .filter(n -> salary1 <= n.getSalary() && n.getSalary() <= salary2);
    }

    public ArrayList<Employee<T>> sortByYearsOfExperience() {
        ArrayList<Employee<T>> result = new ArrayList<>(employees.values().stream().toList());

        Comparator<Employee<T>> comparator = new Comparator<Employee<T>>() {
            @Override
            public int compare(Employee<T> o1, Employee<T> o2) {
                if (o1.getYearsOfExperience() < o2.getYearsOfExperience()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };
        Collections.sort(result, comparator);

        return result;

    }

    public ArrayList<Employee<T>> giveSalaryRaise(double minPerformanceRating) {
        double raiseAmount = 500.0;
        ArrayList<Employee<T>> ls = new ArrayList<>(employees.values());
        Iterator<Employee<T>> iter = ls.iterator();
        while (iter.hasNext()) {
            Employee<T> employee = iter.next();
            double salary = employee.getSalary();
            double ratingPerformance = employee.getPerformanceRating();

            if (ratingPerformance >= minPerformanceRating) {
                employee.setSalary(salary + raiseAmount);
            }
        }
        return ls;

    }

    public List<Employee<T>> retrieveTopFiveHighestPaid() {
        ArrayList<Employee<T>> employeeList = new ArrayList<>(employees.values());
        Comparator<Employee<T>> comparator = new Comparator<Employee<T>>() {
            @Override
            public int compare(Employee<T> o1, Employee<T> o2) {
                if (o1.getSalary() < o2.getSalary()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };
        Collections.sort(employeeList, comparator);
        if (employeeList.size() >= 5) {
            return employeeList.subList(0, 1);
        }
        return employeeList;

    }

    public HashMap<String, ArrayList<Double>> getAverageDepartmentSalary() {
        HashMap<String, ArrayList<Double>> departmentToSalaries = new HashMap<>();
        List<Employee<T>> employeeList = new ArrayList<>(employees.values()).stream().toList();
        Iterator<Employee<T>> iterator = employeeList.iterator();
        while (iterator.hasNext()) {
            Employee<T> employee = iterator.next();
            String department = employee.getDepartment();
            if (departmentToSalaries.containsKey(department)) {
                ArrayList<Double> salaryList = departmentToSalaries.get(department);
                double salary = salaryList.get(0) + employee.getSalary();
                double nbrOfEmployees = salaryList.get(1) + 1;
                salaryList.add(0, salary);
                salaryList.add(1, nbrOfEmployees);
                departmentToSalaries.put(department, salaryList);
            } else {
                ArrayList<Double> salaryList = new ArrayList<>();
                salaryList.add(employee.getSalary());
                double nbrOfEmployees = 1;
                salaryList.add(nbrOfEmployees);
                departmentToSalaries.put(department, salaryList);
            }

        }
        return departmentToSalaries;

    }
}
