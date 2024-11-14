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


import java.util.Optional;
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
        loadTraineeTableData();

    }


    private void loadTraineeTableData() {
        ObservableList<TraineeTM> obList = FXCollections.observableArrayList();

//        Database.trainees.stream().filter(t->t.getId().equals(searchText) || t.getFirstName().equals(searchText) || t.getLastName().equals(searchText) || t.getEmail().equals(searchText) || t.getMobile().equals(searchText) || t.getNic().equals(searchText));

        for (Trainee trainee : Database.trainees) {
            Button btnUpdate = new Button("Update");
            Button btnDelete = new Button("Delete");

            btnUpdate.setStyle("-fx-background-color:#1E386F; -fx-text-fill: white;");
            btnDelete.setStyle("-fx-background-color:#CB0D0D; -fx-text-fill: white;");

            ButtonBar buttonBar = new ButtonBar();
            buttonBar.getButtons().addAll(btnUpdate, btnDelete);


            btnUpdate.setOnAction(event -> {
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
            });

            btnDelete.setOnAction(event -> {
                Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete trainee " + trainee.getFirstName() + " " + trainee.getLastName() + "?", ButtonType.YES).showAndWait();
                if (buttonType.get() == ButtonType.YES) {
                    Database.trainees.remove(trainee);
                    loadTraineeTableData();

                }
            });

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
                    buttonBar
            ));


            tblTrainee.setItems(obList);
        }
    }


    public void createOnAction(ActionEvent actionEvent) {
        if (
                txtFirstName.getText().isEmpty() ||
                        txtLastName.getText().isEmpty() ||
                        txtEmail.getText().isEmpty() ||
                        txtMobile.getText().isEmpty() ||
                        txtNic.getText().isEmpty() ||
                        txtAddress.getText().isEmpty() ||
                        txtDob.getValue() == null ||
                        cmbExperience.getSelectionModel().isEmpty() ||
                        cmbGender.getSelectionModel().isEmpty()
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
                loadTraineeTableData();
            } else {

                save(newTraineeDetailObj);
                loadTraineeTableData();

            }
        }
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

    public void resetOnAction(ActionEvent actionEvent) {
        String id = txtid.getText();
        FormController.clearForm(gridPane);
        txtid.setText(id);
    }

    private void loadExperienceLevel() {
        FormController.loadComboBoxData(Database.traineeLevels, cmbExperience);
    }

    private void loadGenders() {
        FormController.loadComboBoxData(Database.genders, cmbGender);
    }


    public void addNewTraineeOnAction(ActionEvent actionEvent) {
        btnCreate.setText("Create");
        btnCreate.setStyle("-fx-background-color:#FF6A00; -fx-text-fill: white;");
        btnAddNewTrainee.setVisible(false);
        FormController.clearForm(gridPane);
        txtid.setText(RandomKeyGenerator.generateTraineeId());

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
}
