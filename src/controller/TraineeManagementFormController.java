package controller;

import DB.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Gender;
import model.Supportive;
import model.Trainee;
import model.TraineeLevel;
import util.RandomKeyGenerator;

import java.util.Date;
import java.util.List;

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
    public GridPane gridPane;


    public void initialize() {

        txtid.setText(RandomKeyGenerator.generateTraineeId());
        loadGenders();
        loadExperienceLevel();
        loadTraineeTableData();
    }


    private void loadTraineeTableData() {

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
            clearForm(gridPane);
        }

    }

    public void resetOnAction(ActionEvent actionEvent) {

    }

    private void loadExperienceLevel() {
        loadComboBoxData(Database.traineeLevels, cmbExperience);
    }

    private void loadGenders() {
        loadComboBoxData(Database.genders, cmbGender);
    }

    public <T extends Supportive> void loadComboBoxData(List<T> data, ComboBox<T> comboBox) {
        // Create an observable list from the data
        ObservableList<T> dataList = FXCollections.observableArrayList(data);

        // Set the items for the ComboBox
        comboBox.setItems(dataList);

        // Set a custom cell factory to display the `getName()` property
        comboBox.setCellFactory(param -> new ListCell<T>() {
            @Override
            protected void updateItem(T customObj, boolean empty) {
                super.updateItem(customObj, empty);
                if (empty || customObj == null) {
                    setText(null);
                } else {
                    setText(customObj.getName()); // Customize based on `Gender` properties
                }
            }
        });

        // Set the button cell to show the selected item's `getName()`
        comboBox.setButtonCell(new ListCell<T>() {
            @Override
            protected void updateItem(T customObj, boolean empty) {
                super.updateItem(customObj, empty);
                if (empty || customObj == null) {
                    setText(null);
                } else {
                    setText(customObj.getName());
                }
            }
        });
    }

    public void clearForm(Parent container){
        for (Node node : container.getChildrenUnmodifiable()){
            if (node instanceof TextField) ((TextField) node).clear();
            else if (node instanceof ComboBox) {
                ((ComboBox<?>) node).getSelectionModel().clearSelection();
                ((ComboBox<?>) node).setValue(null);
            } else if (node instanceof DatePicker) {
                ((DatePicker) node).setValue(null);
            }
        }
    }


}
