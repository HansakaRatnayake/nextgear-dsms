package controller;

import DB.Database;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Employee;
import model.Trainer;
import model.VehicleCategory;
import model.VehicleType;
import util.FormController;

import javax.xml.crypto.Data;
import java.util.ArrayList;


public class TrainerManagementFormController {

    public AnchorPane TrainerContext;

    public HBox chipContainer;
    public GridPane gridPane;
    public TextField txtId;
    public TextField txtEmail;
    public ComboBox cmbTrainer;
    public ComboBox<VehicleType> cmbVehicleType;
    public ComboBox<VehicleCategory> cmbVehicleCategory;
    public TextField txtSearch;
    public TableView tblTrainer;
    public TableColumn colEmpId;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colType;
    public TableColumn colCategory;
    public TableColumn colActions;
    public ComboBox<VehicleCategory> cmbSearchCategory;
    public ComboBox<VehicleType> cmbSearchType;


    StringBuilder searchText = new StringBuilder();
    StringBuilder searchVehicleType = new StringBuilder();
    StringBuilder searchVehicleCategory = new StringBuilder();

    String queryParam = "searchText=" + searchText + "&type=" + searchVehicleType + "&category=" + searchVehicleCategory;

    ArrayList<VehicleCategory> selectedCategories = new ArrayList<>();

    public void initialize() {

        loadTrainers();
        loadVehicleTypes();
        loadVehicleCategories();
        addListeners();

    }



    public void loadTrainerTable(String queryParam) {
    }

    private void loadTrainers() {
        Database.employees.stream().map(e->{
            if (e.getRole().getName().equals("Trainer")){
                return new Trainer(
                        e.getId(),
                        e.getFirstName() + " " + e.getLastName(),
                        e.getEmail(),

                );
            }
        })
    }

    private void loadVehicleCategories() {
        FormController.loadComboBoxData(Database.vehicleCategories, cmbVehicleCategory);
        FormController.loadComboBoxData(Database.vehicleCategories, cmbVehicleCategory);
    }

    private void loadVehicleTypes() {
        FormController.loadComboBoxData(Database.vehicleTypes, cmbVehicleType);
        FormController.loadComboBoxData(Database.vehicleTypes, cmbSearchType);
    }

    public void addListeners() {

        cmbVehicleCategory.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                AnchorPane chip = createChip(newValue);
                boolean isExist = false;
                for (VehicleCategory vehicleCategory : selectedCategories) {
                    if (Integer.parseInt(chip.getId()) == vehicleCategory.getId()) {
                        new Alert(Alert.AlertType.WARNING, "Category " + vehicleCategory.getName() + " is already selected", ButtonType.OK).show();
                        isExist = true;
                        break;
                    }
                }

                if (!isExist) {
                    chipContainer.getChildren().add(chip);
                    selectedCategories.add(newValue);
                }

            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText.setLength(0); // Clear previous value
            searchText.append(newValue);
            String queryParam = "searchText=" + searchText + "&type=" + searchVehicleType + "&category=" + searchVehicleCategory;
            loadTrainerTable(queryParam);
        });

        cmbSearchCategory.valueProperty().addListener((observable, oldValue, newValue) -> {
            searchVehicleCategory.setLength(0);
            if (newValue != null) {
                searchVehicleCategory.append(newValue.getName());
            }
            String queryParam = "searchText=" + searchText + "&type=" + searchVehicleType + "&category=" + searchVehicleCategory;
            loadTrainerTable(queryParam);
        });

        cmbSearchType.valueProperty().addListener((observable, oldValue, newValue) -> {
            searchVehicleType.setLength(0);
            if (newValue != null) {
                searchVehicleType.append(newValue.getName());
            }
            String queryParam = "searchText=" + searchText + "&type=" + searchVehicleType + "&category=" + searchVehicleCategory;
            loadTrainerTable(queryParam);
        });

    }


    public AnchorPane createChip(VehicleCategory vehicleCategory) {


        AnchorPane mainAnchorPane = new AnchorPane();
        mainAnchorPane.getStylesheets().clear();
        mainAnchorPane.getStyleClass().add("chip");
        mainAnchorPane.setId(String.valueOf(vehicleCategory.getId()));


        HBox hBox = new HBox();
        hBox.setSpacing(3);
        AnchorPane.setBottomAnchor(hBox, 0.0);
        AnchorPane.setLeftAnchor(hBox, 0.0);
        AnchorPane.setTopAnchor(hBox, 0.0);
        AnchorPane.setRightAnchor(hBox, 0.0);

        Label label = new Label(vehicleCategory.getName());
        label.getStyleClass().add("lbl-text");

        AnchorPane cancelAnchorPane = new AnchorPane();
        cancelAnchorPane.setPrefHeight(18.0);
        cancelAnchorPane.setPrefWidth(11.0);
        cancelAnchorPane.getStyleClass().add("cancel-outer");

        cancelAnchorPane.setOnMouseClicked(event -> handleChipCancel(vehicleCategory));

        ImageView cancelImageView = new ImageView(getClass().getResource("../view/assets/cancel.png").toExternalForm());
        cancelImageView.setFitWidth(8.0);
        cancelImageView.setPickOnBounds(true);
        cancelImageView.setPreserveRatio(true);
        AnchorPane.setBottomAnchor(cancelImageView, 0.0);
        AnchorPane.setLeftAnchor(cancelImageView, 5.0);
        AnchorPane.setTopAnchor(cancelImageView, 4.0);

        cancelAnchorPane.getChildren().add(cancelImageView);

        hBox.getChildren().addAll(label, cancelAnchorPane);


        mainAnchorPane.getChildren().add(hBox);

        mainAnchorPane.getStylesheets().add(getClass().getResource("../view/styles/ChipCustom.css").toExternalForm());


        return mainAnchorPane;
    }

    private void handleChipCancel(VehicleCategory vehicleCategory) {
        int chipIndex = 0;
        selectedCategories.remove(vehicleCategory);
        for (Node chip : chipContainer.getChildren()) {
            if (chip.getId().equals(String.valueOf(vehicleCategory.getId()))) {
                chipContainer.getChildren().remove(chipIndex);
                break;
            }
            chipIndex++;
        }
    }

    public void setOnAction(ActionEvent actionEvent) {

    }

    public void resetOnAction(ActionEvent actionEvent) {

    }

    public void addNewTrainerVehicleOnAction(ActionEvent actionEvent) {

    }

    public void resetFilterOnAction(ActionEvent actionEvent) {

    }
}
