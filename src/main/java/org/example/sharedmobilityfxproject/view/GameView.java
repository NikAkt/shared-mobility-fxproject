package org.example.sharedmobilityfxproject.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameView {
    private Stage stage;

    public GameView(Stage stage) {
        this.stage = stage;
        setupScene();
    }

    private void setupScene() {
        BorderPane root = new BorderPane();
        // Placeholder for map and game elements.
        // Add your game map and elements here.

        Button selectTransportButton = new Button("Select Transport");
        // Add event handler for the button to open transport selection dialog.
        root.setBottom(selectTransportButton);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
    }
}
