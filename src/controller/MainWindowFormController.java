package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainWindowFormController {

    public AnchorPane contextOutlet;

    public void initialize() {
        dashboardOnAction(new ActionEvent());
    }

    public void dashboardOnAction(ActionEvent actionEvent) {
        loadUI("Dashboard");
    }

    public void traineeOnAction(ActionEvent actionEvent) {
        loadUI("TraineeManagement");
    }

    public void loadUI(String context){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/"+context+"Form.fxml"));
            Parent dashboardView = loader.load();

            contextOutlet.getChildren().clear();
            contextOutlet.getChildren().add(dashboardView);

            contextOutlet.setTopAnchor(dashboardView, 0.0);
            contextOutlet.setLeftAnchor(dashboardView, 0.0);
            contextOutlet.setRightAnchor(dashboardView, 0.0);
            contextOutlet.setBottomAnchor(dashboardView, 0.0);



        }catch (IOException e){
            e.printStackTrace();
        }

    }
}