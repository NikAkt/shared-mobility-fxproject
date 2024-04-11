package org.example.sharedmobilityfxproject.model;

import javafx.scene.control.Alert;

public class EducationalPopup {
    public static void show(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Environmental Tip");
        alert.setHeaderText(null);
        alert.setContentText(message);


        alert.showAndWait();
    }

}
