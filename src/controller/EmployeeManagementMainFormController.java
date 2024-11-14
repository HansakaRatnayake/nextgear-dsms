package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EmployeeManagementMainFormController {
    public AnchorPane employeeMainContext;


    public void maintainerManageOnAction(MouseEvent mouseEvent) {

    }

    public void trainerManageOnAction(MouseEvent mouseEvent) {

    }

    public void employeeManageOnAction(MouseEvent mouseEvent) {
        loadUI("EmployeeManagement");
    }


    public void loadUI(String context){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/"+context+"Form.fxml"));
            Parent dashboardView = loader.load();

            employeeMainContext.getChildren().clear();
            employeeMainContext.getChildren().add(dashboardView);

            employeeMainContext.setTopAnchor(dashboardView, 0.0);
            employeeMainContext.setLeftAnchor(dashboardView, 0.0);
            employeeMainContext.setRightAnchor(dashboardView, 0.0);
            employeeMainContext.setBottomAnchor(dashboardView, 0.0);



        }catch (IOException e){
            e.printStackTrace();
        }

    }


}
