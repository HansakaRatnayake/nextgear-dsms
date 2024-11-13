package controller;

import DB.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Gender;
import model.Supportive;
import model.Trainee;
import model.TraineeLevel;
import util.FormController;
import util.RandomKeyGenerator;
import view.tm.TraineeTM;

import java.util.Date;
import java.util.List;
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

            Database.trainees.add(
                    new Trainee(
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

                    )
            );

            new Alert(Alert.AlertType.INFORMATION, "Trainee created successfully!", ButtonType.OK).showAndWait();
            FormController.clearForm(gridPane);
            loadTraineeTableData();
        }

    }

    public void resetOnAction(ActionEvent actionEvent) {
        FormController.clearForm(gridPane);
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

    }
}
