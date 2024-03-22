package org.example.sharedmobilityfxproject.view;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EndStageSummary {
    private Stage stage;

    public EndStageSummary(Stage stage) {
        this.stage = stage;
        setupScene();
    }

    private void setupScene() {
        VBox root = new VBox(10);
        Label summaryLabel = new Label("Stage Summary");
        // Populate with actual game data.

        root.getChildren().add(summaryLabel);
        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
    }
}
