package controller;

import DB.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Gender;
import model.Trainee;
import model.TraineeLevel;
import util.ErrorHandler;
import util.FormController;
import util.RandomKeyGenerator;
import view.tm.TraineeTM;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TraineeManagementFormController {
    public AnchorPane TraineeContext;
    public TextField txtid;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtEmail;
    public TextField txtMobile;
    public TextField txtNic;
    public TextField txtAddress;
    public ComboBox<TraineeLevel> cmbExperience;
    public DatePicker txtDob;
    public ComboBox<Gender> cmbGender;
    public TableView<TraineeTM> tblTrainee;
    public GridPane gridPane;
    public TextField txtSearch;
    public TableColumn colFName;
    public TableColumn colLName;
    public TableColumn colEmail;
    public TableColumn colMobile;
    public TableColumn colNic;
    public TableColumn colDob;
    public TableColumn colAddress;
    public TableColumn colGender;
    public TableColumn colActions;
    public TableColumn colId;
    public TableColumn colLevel;
    public Button btnCreate;
    public Button btnAddNewTrainee;
    public ComboBox<TraineeLevel> cmbSearchExperience;
    public ComboBox<Gender> cmbSearchGender;

    StringBuilder searchText = new StringBuilder();
    StringBuilder searchGender = new StringBuilder();
    StringBuilder searchLevel = new StringBuilder();

    String queryParam = "searchText=" + searchText + "&gender=" + searchGender + "&level=" + searchLevel;

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
        colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        colActions.setCellValueFactory(new PropertyValueFactory<>("actions"));


        txtid.setText(RandomKeyGenerator.generateTraineeId());
        loadGenders();
        loadExperienceLevel();
        addListeners();
        loadTraineeTableData(queryParam);

    }

    public void createOnAction(ActionEvent actionEvent) {
        if (
                txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty() || txtEmail.getText().isEmpty() ||
                        txtMobile.getText().isEmpty() || txtNic.getText().isEmpty() || txtAddress.getText().isEmpty() ||
                        txtDob.getValue() == null || cmbExperience.getSelectionModel().isEmpty() || cmbGender.getSelectionModel().isEmpty()
        ) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the required fields!", ButtonType.CANCEL, ButtonType.OK).show();
        } else {

            Trainee newTraineeDetailObj = new Trainee(
                    txtid.getText(),
                    txtFirstName.getText(),
                    txtLastName.getText(),
                    txtEmail.getText(),
                    txtNic.getText(),
                    txtMobile.getText(),
                    txtAddress.getText(),
                    txtDob.getValue(),
                    cmbExperience.getSelectionModel().getSelectedItem(),
                    cmbGender.getSelectionModel().getSelectedItem()
            );

            if (btnCreate.getText().equals("Update")) {
                update(newTraineeDetailObj);
                loadTraineeTableData(queryParam);
            } else {

                save(newTraineeDetailObj);
                txtid.setText(RandomKeyGenerator.generateTraineeId());
                loadTraineeTableData(queryParam);

            }
        }
    }


    public void addNewTraineeOnAction(ActionEvent actionEvent) {
        btnCreate.setText("Create");
        btnCreate.setStyle("-fx-background-color:#FF6A00; -fx-text-fill: white;");
        btnAddNewTrainee.setVisible(false);
        FormController.clearForm(gridPane);
        txtid.setText(RandomKeyGenerator.generateTraineeId());

    }


    public void resetOnAction(ActionEvent actionEvent) {
        String id = txtid.getText();
        FormController.clearForm(gridPane);
        txtid.setText(id);
    }

    public void resetFilterOnAction(ActionEvent actionEvent) {
        txtSearch.clear();
        cmbSearchExperience.getSelectionModel().clearSelection();
        cmbSearchGender.getSelectionModel().clearSelection();
        searchText.setLength(0);
        searchGender.setLength(0);
        searchLevel.setLength(0);

    }


    ObservableList<TraineeTM> obList = FXCollections.observableArrayList();

    private void loadTraineeTableData(String queryParam) {
        obList.clear();
        List<Trainee> filteredTrainees = Database.trainees;


        String[] pairs = queryParam.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");

            if (keyValue[0].equals("searchText") && keyValue.length == 2) {
                filteredTrainees = filteredTrainees.stream().filter(t -> t.getId().contains(keyValue[1]) || t.getFirstName().contains(keyValue[1]) || t.getLastName().contains(keyValue[1]) || t.getEmail().contains(keyValue[1]) || t.getMobile().contains(keyValue[1]) || t.getNic().contains(keyValue[1])).collect(Collectors.toList());
            }
            if (keyValue[0].equals("gender") && keyValue.length == 2) {
                filteredTrainees = filteredTrainees.stream().filter(t -> t.getGender().getName().equals(keyValue[1])).collect(Collectors.toList());
            }
            if (keyValue[0].equals("level") && keyValue.length == 2) {
                filteredTrainees = filteredTrainees.stream().filter(t -> t.getLevel().getName().equals(keyValue[1])).collect(Collectors.toList());
            }

        }

        for (Trainee trainee : filteredTrainees) {

            obList.add(new TraineeTM(
                    trainee.getId(),
                    trainee.getFirstName(),
                    trainee.getLastName(),
                    trainee.getEmail(),
                    trainee.getNic(),
                    trainee.getMobile(),
                    trainee.getAddress(),
                    trainee.getDob(),
                    trainee.getLevel().getName(),
                    trainee.getGender().getName(),
                    createActionButtons(trainee)
            ));

            tblTrainee.setItems(obList);
        }
    }

    private ButtonBar createActionButtons(Trainee trainee) {
        Button btnUpdate = new Button("Update");
        Button btnDelete = new Button("Delete");

        btnUpdate.setStyle("-fx-background-color:#1E386F; -fx-text-fill: white;");
        btnDelete.setStyle("-fx-background-color:#CB0D0D; -fx-text-fill: white;");

        btnUpdate.setOnAction(event -> loadTraineeDataToForm(trainee));
        btnDelete.setOnAction(event -> handleDeleteTrainee(trainee));

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(btnUpdate, btnDelete);
        return buttonBar;
    }

    private void handleDeleteTrainee(Trainee trainee) {
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete trainee " + trainee.getFirstName() + " " + trainee.getLastName() + "?", ButtonType.YES).showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            Database.trainees.remove(trainee);
            loadTraineeTableData(queryParam);

        }
    }

    private void loadTraineeDataToForm(Trainee trainee) {

        txtid.setText(trainee.getId());
        txtFirstName.setText(trainee.getFirstName());
        txtLastName.setText(trainee.getLastName());
        txtEmail.setText(trainee.getEmail());
        txtMobile.setText(trainee.getMobile());
        txtNic.setText(trainee.getNic());
        txtAddress.setText(trainee.getAddress());
        cmbGender.getSelectionModel().select(IntStream.range(0, cmbGender.getItems().size()).filter(i -> cmbGender.getItems().get(i).getName().equals(trainee.getGender().getName())).findFirst().orElseThrow(() -> new RuntimeException("Gender Not Found")));
        cmbExperience.getSelectionModel().select(IntStream.range(0, cmbExperience.getItems().size()).filter(i -> cmbExperience.getItems().get(i).getName().equals(trainee.getLevel().getName())).findFirst().orElseThrow(() -> new RuntimeException("Level Not Found")));
        txtDob.setValue(trainee.getDob());

        btnCreate.setText("Update");
        btnCreate.setStyle("-fx-background-color:#0612ec; -fx-text-fill: white;");
        btnAddNewTrainee.setVisible(true);
    }

    private ErrorHandler checkErrors(Trainee trainee) {
        StringBuilder error = new StringBuilder();
        boolean isUpdate = false;

        for (Trainee tr : Database.trainees) {
            if (tr.getId().equals(trainee.getId())) {
                isUpdate = true;
                for (Trainee temp : Database.trainees) {
                    if (!(trainee.getId().equals(temp.getId())) && trainee.getNic().equals(temp.getNic()))
                        error.append("NIC already exits\n");
                    if (!(trainee.getId().equals(temp.getId())) && trainee.getEmail().equals(temp.getEmail()))
                        error.append("E-mail already exits\n");
                    if (!(trainee.getId().equals(temp.getId())) && trainee.getMobile().equals(temp.getMobile()))
                        error.append("Mobile already exits");
                }
                break;
            }
        }

        if (!isUpdate) {
            for (Trainee temp : Database.trainees) {
                if (trainee.getNic().equals(temp.getNic()))
                    error.append("NIC already exits\n");
                if (trainee.getEmail().equals(temp.getEmail()))
                    error.append("E-mail already exits\n");
                if (trainee.getMobile().equals(temp.getMobile()))
                    error.append("Mobile already exits");
            }
        }

        return new ErrorHandler(error, error.length() > 0);
    }


    private void save(Trainee trainee) {
        ErrorHandler errorHandler = checkErrors(trainee);

        if (errorHandler.getStatus()) {
            new Alert(Alert.AlertType.ERROR, errorHandler.getErrors().toString(), ButtonType.OK).show();
        } else {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to add  trainee " + trainee.getFirstName() + " " + trainee.getLastName() + " ?", ButtonType.OK, ButtonType.CANCEL).showAndWait();

            if (buttonType.get() == ButtonType.OK) {
                Database.trainees.add(trainee);
                new Alert(Alert.AlertType.INFORMATION, "Trainee successfully created!", ButtonType.OK).show();
                FormController.clearForm(gridPane);
            }
        }
    }

    private void update(Trainee trainee) {
        ErrorHandler errorHandler = checkErrors(trainee);

        if (errorHandler.getStatus()) {
            new Alert(Alert.AlertType.ERROR, errorHandler.getErrors().toString(), ButtonType.OK).show();
        } else {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to update data of trainee " + trainee.getFirstName() + " ?", ButtonType.OK, ButtonType.CANCEL).showAndWait();

            if (buttonType.get() == ButtonType.OK) {
                Trainee selectedTrainee = Database.trainees.get(IntStream.range(0, Database.trainees.size()).filter(i -> Database.trainees.get(i).getId().equals(trainee.getId())).findFirst().orElseThrow(() -> new RuntimeException("Trainee Not Found")));
                selectedTrainee.setFirstName(trainee.getFirstName());
                selectedTrainee.setLastName(trainee.getLastName());
                selectedTrainee.setEmail(trainee.getEmail());
                selectedTrainee.setMobile(trainee.getMobile());
                selectedTrainee.setNic(trainee.getNic());
                selectedTrainee.setAddress(trainee.getAddress());
                selectedTrainee.setDob(trainee.getDob());
                selectedTrainee.setLevel(trainee.getLevel());
                selectedTrainee.setGender(trainee.getGender());

                new Alert(Alert.AlertType.INFORMATION, "Update Successfully Completed", ButtonType.OK).showAndWait();
                addNewTraineeOnAction(new ActionEvent());
            }

        }
    }


    private void loadExperienceLevel() {
        FormController.loadComboBoxData(Database.traineeLevels, cmbExperience);
        FormController.loadComboBoxData(Database.traineeLevels, cmbSearchExperience);
    }

    private void loadGenders() {
        FormController.loadComboBoxData(Database.genders, cmbGender);
        FormController.loadComboBoxData(Database.genders, cmbSearchGender);
    }

    public void addListeners() {
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText.setLength(0); // Clear previous value
            searchText.append(newValue);
            queryParam = "searchText=" + searchText + "&gender=" + searchGender + "&level=" + searchLevel;
            loadTraineeTableData(queryParam);
        });

        cmbSearchGender.valueProperty().addListener((observable, oldValue, newValue) -> {
            searchGender.setLength(0);
            if (newValue != null) {
                searchGender.append(newValue.getName());
            }
            queryParam = "searchText=" + searchText + "&gender=" + searchGender + "&level=" + searchLevel;
            loadTraineeTableData(queryParam);
        });

        cmbSearchExperience.valueProperty().addListener((observable, oldValue, newValue) -> {
            searchLevel.setLength(0);
            if (newValue != null) {
                searchLevel.append(newValue.getName());
            }
            queryParam = "searchText=" + searchText + "&gender=" + searchGender + "&level=" + searchLevel;
            loadTraineeTableData(queryParam);
        });

    }

}
