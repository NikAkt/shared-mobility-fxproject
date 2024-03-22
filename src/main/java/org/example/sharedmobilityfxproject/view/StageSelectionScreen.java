package org.example.sharedmobilityfxproject.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StageSelectionScreen {
    private Stage stage;

    public StageSelectionScreen(Stage stage) {
        this.stage = stage;
        setupScene();
    }

    private void setupScene() {
        VBox root = new VBox(10);
        Button dublinButton = new Button("Dublin");
        Button seoulButton = new Button("Seoul");
        // Add buttons for each city and their event handlers.

        root.getChildren().addAll(dublinButton, seoulButton);
        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
    }
}
