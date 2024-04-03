package org.example.sharedmobilityfxproject.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class MenuElement {
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


    private String normalButtonStyle() {
        return "-fx-font-size: 24px; -fx-background-color: rgba(255, 255, 240, 0.7); -fx-text-fill: black;";
    }

    private String focusedButtonStyle() {
        return "-fx-font-size: 24px; -fx-background-color: dodgerblue; -fx-text-fill: white;";
    }


}
