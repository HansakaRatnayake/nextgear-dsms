package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class RegistrationMainFormController {
    public AnchorPane registrationMainContext;
    public void loadUI(String context){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/"+context+"Form.fxml"));
            Parent dashboardView=loader.load();



            registrationMainContext.getChildren().clear();
            registrationMainContext.getChildren().add(dashboardView);

            AnchorPane.setTopAnchor(dashboardView, 0.0);
            AnchorPane.setLeftAnchor(dashboardView, 0.0);
            AnchorPane.setRightAnchor(dashboardView, 0.0);
            AnchorPane.setBottomAnchor(dashboardView, 0.0);



        }catch (IOException e){
            e.printStackTrace();
        }

    }



    public void examRegistrationOnAction(ActionEvent actionEvent) {loadUI("ExamRegistration");
    }

    public void programManagementOnAction(ActionEvent actionEvent) {loadUI("ProgramRegistration");
    }
}
