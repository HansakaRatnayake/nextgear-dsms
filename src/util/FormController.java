package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import model.Supportive;

import java.util.List;

public class FormController {

    public static <T extends Supportive> void loadComboBoxData(List<T> data, ComboBox<T> comboBox) {
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
                    setText(customObj.getName()); // Customize based on `Supportive` properties
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

    public static void clearForm(Parent container){
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
