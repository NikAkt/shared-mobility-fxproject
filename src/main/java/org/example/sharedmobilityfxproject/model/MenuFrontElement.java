package org.example.sharedmobilityfxproject.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import org.example.sharedmobilityfxproject.view.GameView;

public class MenuFrontElement {

    public GameView gameView;
    public static final double BUTTON_WIDTH = 200;

    public Button createButton(String text, EventHandler<ActionEvent> action) {
        Button button = new Button(text);
        button.setMinWidth(BUTTON_WIDTH);
        button.setMaxWidth(BUTTON_WIDTH);
        button.setStyle(normalButtonStyle());
        button.setOnAction(action);
        button.setFocusTraversable(true);

        //hover colour change
        button.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                button.setStyle(focusedButtonStyle());
            } else {
                button.setStyle(normalButtonStyle());
            }
        });

        return button;
    }

    public Button createStageButton(String stage, ImageView stageImage) {
        Button stageButton = new Button(stage);
        stageButton.setGraphic(stageImage);
        stageButton.setContentDisplay(ContentDisplay.TOP);
        if (this.gameView != null) {
            stageButton.setOnAction(event -> gameView.selectStage(stage));
        } else {
            System.out.println("gameView is not initialized");
        }
        return stageButton;
    }


    public String normalButtonStyle() {
        return "-fx-font-size: 24px; -fx-background-color: rgba(255, 255, 240, 0.7); -fx-text-fill: black;";
    }

    public String focusedButtonStyle() {
        return "-fx-font-size: 24px; -fx-background-color: dodgerblue; -fx-text-fill: white;";
    }


}
