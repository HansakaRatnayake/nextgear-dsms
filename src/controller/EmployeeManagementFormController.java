package controller;

import DB.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Employee;
import model.Gender;
import model.Role;
import model.Trainee;
import util.ErrorHandler;
import util.FormController;
import util.RandomKeyGenerator;
import view.tm.EmployeeTM;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EmployeeManagementFormController {


    public AnchorPane employeeContext;
    public GridPane gridPane;
    public TextField txtid;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtEmail;
    public TextField txtMobile;
    public TextField txtNic;
    public TextField txtAddress;
    public ComboBox<Role> cmbRole;
    public DatePicker txtDob;
    public ComboBox<Gender> cmbGender;
    public Button btnCreate;
    public Button btnAddNewEmployee;
    public TextField txtSearch;
    public TableView<EmployeeTM> tblEmployee;
    public TableColumn colId;
    public TableColumn colFName;
    public TableColumn colLName;
    public TableColumn colEmail;
    public TableColumn colMobile;
    public TableColumn colNic;
    public TableColumn colDob;
    public TableColumn colAddress;
    public TableColumn colGender;
    public TableColumn colLevel;
    public TableColumn colActions;
    public ComboBox<Role> cmbSearchRole;
    public ComboBox<Gender> cmbSearchGender;

    StringBuilder searchText = new StringBuilder();
    StringBuilder searchGender = new StringBuilder();
    StringBuilder searchRole = new StringBuilder();

    String queryParam = "searchText=" + searchText + "&gender=" + searchGender + "&role=" + searchRole;

    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("role"));
        colActions.setCellValueFactory(new PropertyValueFactory<>("actions"));

        txtid.setText(RandomKeyGenerator.generateEmployeeId());
        loadGenders();
        loadRoles();
        addListeners();
        loadEmployeeTable(queryParam);
    }

    public void createOnAction(ActionEvent actionEvent) {
        if (
                txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty() || txtEmail.getText().isEmpty() ||
                        txtMobile.getText().isEmpty() || txtNic.getText().isEmpty() || txtAddress.getText().isEmpty() ||
                        txtDob.getValue() == null || cmbRole.getSelectionModel().isEmpty() || cmbGender.getSelectionModel().isEmpty()
        ) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the required fields!", ButtonType.CANCEL, ButtonType.OK).show();
        } else {

            Employee newEmployeeDetailObj = new Employee(
                    txtid.getText(),
                    txtFirstName.getText(),
                    txtLastName.getText(),
                    txtEmail.getText(),
                    txtNic.getText(),
                    txtMobile.getText(),
                    txtAddress.getText(),
                    txtDob.getValue(),
                    cmbRole.getSelectionModel().getSelectedItem(),
                    cmbGender.getSelectionModel().getSelectedItem()
            );

            if (btnCreate.getText().equals("Update")) {
                update(newEmployeeDetailObj);
                loadEmployeeTable(queryParam);
            } else {

                save(newEmployeeDetailObj);
                txtid.setText(RandomKeyGenerator.generateEmployeeId());
                loadEmployeeTable(queryParam);

            }
        }
    }

    public void resetOnAction(ActionEvent actionEvent) {
        String id = txtid.getText();
        FormController.clearForm(gridPane);
        txtid.setText(id);
    }

    public void addNewEmployeeOnAction(ActionEvent actionEvent) {
        btnCreate.setText("Create");
        btnCreate.setStyle("-fx-background-color:#FF6A00; -fx-text-fill: white;");
        btnAddNewEmployee.setVisible(false);
        FormController.clearForm(gridPane);
        txtid.setText(RandomKeyGenerator.generateEmployeeId());
    }

    public void resetFilterOnAction(ActionEvent actionEvent) {
        txtSearch.clear();
        cmbSearchRole.getSelectionModel().clearSelection();
        cmbSearchGender.getSelectionModel().clearSelection();
        searchText.setLength(0);
        searchGender.setLength(0);
        searchRole.setLength(0);
    }

    ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();


    public void loadEmployeeTable(String queryParam) {
        obList.clear();
        List<Employee> filteredEmployees = Database.employees;


        String[] pairs = queryParam.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");

            if (keyValue[0].equals("searchText") && keyValue.length == 2) {
                filteredEmployees = filteredEmployees.stream().filter(e -> e.getId().contains(keyValue[1]) || e.getFirstName().contains(keyValue[1]) || e.getLastName().contains(keyValue[1]) || e.getEmail().contains(keyValue[1]) || e.getMobile().contains(keyValue[1]) || e.getNic().contains(keyValue[1])).collect(Collectors.toList());
            }
            if (keyValue[0].equals("gender") && keyValue.length == 2) {
                filteredEmployees = filteredEmployees.stream().filter(e -> e.getGender().getName().equals(keyValue[1])).collect(Collectors.toList());
            }
            if (keyValue[0].equals("role") && keyValue.length == 2) {
                filteredEmployees = filteredEmployees.stream().filter(e -> e.getRole().getName().equals(keyValue[1])).collect(Collectors.toList());
            }

        }

        for (Employee employee : filteredEmployees) {

            obList.add(new EmployeeTM(
                    employee.getId(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getEmail(),
                    employee.getNic(),
                    employee.getMobile(),
                    employee.getAddress(),
                    employee.getDob(),
                    employee.getRole().getName(),
                    employee.getGender().getName(),
                    createActionButtons(employee)
            ));

            tblEmployee.setItems(obList);
        }
    }


    private ButtonBar createActionButtons(Employee employee) {
        Button btnUpdate = new Button("Update");
        Button btnDelete = new Button("Delete");

        btnUpdate.setStyle("-fx-background-color:#1E386F; -fx-text-fill: white;");
        btnDelete.setStyle("-fx-background-color:#CB0D0D; -fx-text-fill: white;");

        btnUpdate.setOnAction(event -> loadEmployeeDataToForm(employee));
        btnDelete.setOnAction(event -> handleDeleteEmployee(employee));

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(btnUpdate, btnDelete);
        return buttonBar;
    }

    private void handleDeleteEmployee(Employee employee) {
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete employee " + employee.getFirstName() + " " + employee.getLastName() + "?", ButtonType.YES).showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            Database.employees.remove(employee);
            loadEmployeeTable(queryParam);

        }
    }

    private void loadEmployeeDataToForm(Employee employee) {

        txtid.setText(employee.getId());
        txtFirstName.setText(employee.getFirstName());
        txtLastName.setText(employee.getLastName());
        txtEmail.setText(employee.getEmail());
        txtMobile.setText(employee.getMobile());
        txtNic.setText(employee.getNic());
        txtAddress.setText(employee.getAddress());
        cmbGender.getSelectionModel().select(IntStream.range(0, cmbGender.getItems().size()).filter(i -> cmbGender.getItems().get(i).getName().equals(employee.getGender().getName())).findFirst().orElseThrow(() -> new RuntimeException("Gender Not Found")));
        cmbRole.getSelectionModel().select(IntStream.range(0, cmbRole.getItems().size()).filter(i -> cmbRole.getItems().get(i).getName().equals(employee.getRole().getName())).findFirst().orElseThrow(() -> new RuntimeException("Role Not Found")));
        txtDob.setValue(employee.getDob());

        btnCreate.setText("Update");
        btnCreate.setStyle("-fx-background-color:#0612ec; -fx-text-fill: white;");
        btnAddNewEmployee.setVisible(true);
    }

    private ErrorHandler checkErrors(Employee employee) {
        StringBuilder error = new StringBuilder();
        boolean isUpdate = false;

        for (Employee tr : Database.employees) {
            if (tr.getId().equals(employee.getId())) {
                isUpdate = true;
                for (Trainee temp : Database.trainees) {
                    if (!(employee.getId().equals(temp.getId())) && employee.getNic().equals(temp.getNic()))
                        error.append("NIC already exits\n");
                    if (!(employee.getId().equals(temp.getId())) && employee.getEmail().equals(temp.getEmail()))
                        error.append("E-mail already exits\n");
                    if (!(employee.getId().equals(temp.getId())) && employee.getMobile().equals(temp.getMobile()))
                        error.append("Mobile already exits");
                }
                break;
            }
        }

        if (!isUpdate) {
            for (Employee temp : Database.employees) {
                if (employee.getNic().equals(temp.getNic()))
                    error.append("NIC already exits\n");
                if (employee.getEmail().equals(temp.getEmail()))
                    error.append("E-mail already exits\n");
                if (employee.getMobile().equals(temp.getMobile()))
                    error.append("Mobile already exits");
            }
        }

        return new ErrorHandler(error, error.length() > 0);
    }


    private void save(Employee employee) {
        ErrorHandler errorHandler = checkErrors(employee);

        if (errorHandler.getStatus()) {
            new Alert(Alert.AlertType.ERROR, errorHandler.getErrors().toString(), ButtonType.OK).show();
        } else {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to add  employee " + employee.getFirstName() + " " + employee.getLastName() + " ?", ButtonType.OK, ButtonType.CANCEL).showAndWait();

            if (buttonType.get() == ButtonType.OK) {
                Database.employees.add(employee);
                new Alert(Alert.AlertType.INFORMATION, "Employee successfully created!", ButtonType.OK).show();
                FormController.clearForm(gridPane);
            }
        }
    }

    private void update(Employee employee) {
        ErrorHandler errorHandler = checkErrors(employee);

        if (errorHandler.getStatus()) {
            new Alert(Alert.AlertType.ERROR, errorHandler.getErrors().toString(), ButtonType.OK).show();
        } else {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to update data of trainee " + employee.getFirstName() + " ?", ButtonType.OK, ButtonType.CANCEL).showAndWait();

            if (buttonType.get() == ButtonType.OK) {
                Employee selectedTrainee = Database.employees.get(IntStream.range(0, Database.employees.size()).filter(i -> Database.employees.get(i).getId().equals(employee.getId())).findFirst().orElseThrow(() -> new RuntimeException("Employee Not Found")));
                selectedTrainee.setFirstName(employee.getFirstName());
                selectedTrainee.setLastName(employee.getLastName());
                selectedTrainee.setEmail(employee.getEmail());
                selectedTrainee.setMobile(employee.getMobile());
                selectedTrainee.setNic(employee.getNic());
                selectedTrainee.setAddress(employee.getAddress());
                selectedTrainee.setDob(employee.getDob());
                selectedTrainee.setRole(employee.getRole());
                selectedTrainee.setGender(employee.getGender());

                new Alert(Alert.AlertType.INFORMATION, "Update Successfully Completed", ButtonType.OK).showAndWait();
                addNewEmployeeOnAction(new ActionEvent());
            }

        }
    }

    private void loadRoles() {
        FormController.loadComboBoxData(Database.roles, cmbRole);
        FormController.loadComboBoxData(Database.roles, cmbSearchRole);
    }

    private void loadGenders() {
        FormController.loadComboBoxData(Database.genders, cmbGender);
        FormController.loadComboBoxData(Database.genders, cmbSearchGender);
    }

    public void addListeners() {
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText.setLength(0); // Clear previous value
            searchText.append(newValue);
            queryParam = "searchText=" + searchText + "&gender=" + searchGender + "&role=" + searchRole;
            loadEmployeeTable(queryParam);
        });

        cmbSearchGender.valueProperty().addListener((observable, oldValue, newValue) -> {
            searchGender.setLength(0);
            if (newValue != null) {
                searchGender.append(newValue.getName());
            }
            queryParam = "searchText=" + searchText + "&gender=" + searchGender + "&role=" + searchRole;
            loadEmployeeTable(queryParam);
        });

        cmbSearchRole.valueProperty().addListener((observable, oldValue, newValue) -> {
            searchRole.setLength(0);
            if (newValue != null) {
                searchRole.append(newValue.getName());
            }
            queryParam = "searchText=" + searchText + "&gender=" + searchGender + "&role=" + searchRole;
            loadEmployeeTable(queryParam);
        });

    }
}
