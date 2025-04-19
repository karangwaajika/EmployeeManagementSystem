package com.example.employeemanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class Controller {
    // input field
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

    // table fields
    @FXML
    private TableView<Employee<Integer>> employeeTable;
    @FXML
    private TableColumn<Employee, String> nameColumn;
    @FXML
    private TableColumn<Employee, String> departmentColumn;
    @FXML
    private TableColumn<Employee, Double> salaryColumn;
    @FXML
    private TableColumn<Employee, String> ratingColumn;
    @FXML
    private TableColumn<Employee, Integer> experienceColumn;
    @FXML
    private TableColumn<Employee, Boolean> activeColumn;

    private final Database<Integer> db = new Database<>(); // initialize database
    private final ObservableList<Employee<Integer>> employeeList = FXCollections.observableArrayList();

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

            if (!name.matches("[a-zA-Z\\s]+")) {
                showAlert(Alert.AlertType.ERROR, "Invalid Name",
                        "Name must only contain letters and spaces.");
                return;
            }

            if (!department.matches("[a-zA-Z\\s]+")) {
                showAlert(Alert.AlertType.ERROR, "Invalid Department",
                        "Department must only contain letters and spaces.");
                return;
            }

            if (rating > 5) {
                showAlert(Alert.AlertType.ERROR, "Validation Error",
                        "Rating cannot be greater than 5.");
                return;
            }

            Employee<Integer> employee = new Employee<>(nbrOfEmployees, name,
                    department, salary, rating, experience, isActive);
            db.addEmployee(nbrOfEmployees, employee);
            employeeList.add(employee);

            showAlert(Alert.AlertType.CONFIRMATION, "Success",
                    "✅ Employee added Successfully: " + employee.getName());
            System.out.println("✅ Employee added: " + employee.getName());
            clearForm();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error",
                    "Please enter valid numbers for salary, rating, and experience.");
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("performanceRating"));
        experienceColumn.setCellValueFactory(new PropertyValueFactory<>("yearsOfExperience"));
        activeColumn.setCellValueFactory(new PropertyValueFactory<>("isActive"));

        employeeTable.setItems(employeeList);
    }

}