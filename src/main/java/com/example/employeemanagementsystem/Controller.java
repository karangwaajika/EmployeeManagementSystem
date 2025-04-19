package com.example.employeemanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField nameField;
    @FXML
    private TextField departmentField;
    @FXML
    private TextField salaryField;
    @FXML
    private TextField ratingField;
    @FXML
    private TextField experienceField;
    @FXML
    private CheckBox isActiveCheckBox;

    private final Database<Integer> db = new Database<>(); // initialize database

    @FXML
    private void handleSubmit() {
        try {
            String name = nameField.getText();
            String department = departmentField.getText();
            double salary = Double.parseDouble(salaryField.getText());
            double rating = Double.parseDouble(ratingField.getText());
            int experience = Integer.parseInt(experienceField.getText());
            boolean isActive = isActiveCheckBox.isSelected();
            int nbrOfEmployees = Employee.nbrOfEmployees;

            Employee<Integer> employee = new Employee<>(nbrOfEmployees, name,
                    department, salary, rating, experience, isActive);
            db.addEmployee(nbrOfEmployees, employee);

            System.out.println("✅ Employee added: " + employee.getName());
            clearForm();

        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
        }
    }

    private void clearForm() {
        nameField.clear();
        departmentField.clear();
        salaryField.clear();
        ratingField.clear();
        experienceField.clear();
        isActiveCheckBox.setSelected(false);
    }
}