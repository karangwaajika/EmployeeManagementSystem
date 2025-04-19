package com.example.employeemanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.Collections;

public class Controller {
    // input field
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<String> departmentComboBox;
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
    private TableColumn<Employee<Integer>, String> nameColumn;
    @FXML
    private TableColumn<Employee<Integer>, String> departmentColumn;
    @FXML
    private TableColumn<Employee<Integer>, Double> salaryColumn;
    @FXML
    private TableColumn<Employee<Integer>, String> ratingColumn;
    @FXML
    private TableColumn<Employee<Integer>, Integer> experienceColumn;
    @FXML
    private TableColumn<Employee<Integer>, Boolean> activeColumn;

    // action fields
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> departmentFilterComboBox;
    @FXML
    private ComboBox<String> sortComboBox;


    private final Database<Integer> db = new Database<>(); // initialize database
    private final ObservableList<Employee<Integer>> employeeList = FXCollections
            .observableArrayList(db.getAllEmployees());


    @FXML
    private void handleSubmit() {
        try {
            String name = nameField.getText();
            String department = departmentComboBox.getValue();
            double salary = Double.parseDouble(salaryField.getText());
            double rating = Double.parseDouble(ratingField.getText());
            int experience = Integer.parseInt(experienceField.getText());
            boolean isActive = isActiveCheckBox.isSelected();
            int nbrOfEmployees = Employee.nbrOfEmployees;

            if (department == null || department.isBlank()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Please select a department.");
                return;
            }

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

            employeeList.setAll(db.getAllEmployees()); // to make table auto-refresh

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
//        departmentComboBox.getSelectionModel().clearSelection();
        departmentComboBox.setValue("Select Department");
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

        employeeTable.setItems(employeeList); // refresh table for new record

        // search
        searchField.textProperty().addListener((obs,
                                                oldVal, newVal) -> applySearchByName());
        // filter
        departmentFilterComboBox.setValue("All"); // Default
        departmentFilterComboBox.setOnAction(e -> applyFilterByDepartment());

        // sorting
        sortComboBox.setValue("None");
        sortComboBox.setOnAction(e -> applySortBy());
    }

    public void applySearchByName() {
        String searchText = searchField.getText().toLowerCase().trim();
        ObservableList<Employee<Integer>> employeeList = FXCollections
                .observableArrayList(db.filterByName(searchText));

        employeeTable.setItems(employeeList);
    }

    public void applyFilterByDepartment() {
        String departmentFilter = departmentFilterComboBox.getValue();
        if (departmentFilter.equals("All")) {
            ObservableList<Employee<Integer>> employeeList = FXCollections
                    .observableArrayList(db.getAllEmployees());
            employeeTable.setItems(employeeList);
        } else {
            ObservableList<Employee<Integer>> employeeList = FXCollections
                    .observableArrayList(db.filterByDepartment(departmentFilter));
            employeeTable.setItems(employeeList);
        }
    }

    public void applySortBy() {
        String sortOption = sortComboBox.getValue();
        switch (sortOption) {
            case "None":
                ObservableList<Employee<Integer>> employeeList = FXCollections
                        .observableArrayList(db.getAllEmployees());
                employeeTable.setItems(employeeList);
                break;
            case "Salary":
                ArrayList<Employee<Integer>> employees = db.getAllEmployees();
                Collections.sort(employees, new EmployeeSalaryComparator<>());
                ObservableList<Employee<Integer>> employeesList = FXCollections
                        .observableArrayList(employees);
                employeeTable.setItems(employeesList);
                break;
            case "YearsOfExperience":
                ArrayList<Employee<Integer>> list = db.getAllEmployees();
                Collections.sort(list);
                ObservableList<Employee<Integer>> listEmployee = FXCollections
                        .observableArrayList(list);
                employeeTable.setItems(listEmployee);
                break;
            default:
                ArrayList<Employee<Integer>> emploList = db.getAllEmployees();
                Collections.sort(emploList, new EmployeePerformanceComparator<>());
                ObservableList<Employee<Integer>> allEmployees = FXCollections
                        .observableArrayList(emploList);
                employeeTable.setItems(allEmployees);

        }
    }


}