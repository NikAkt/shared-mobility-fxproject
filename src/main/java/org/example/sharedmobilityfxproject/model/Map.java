package org.example.sharedmobilityfxproject.model;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Map {
    private Image backgroundImage;
    private ImageView backgroundImageView; // Reference to the ImageView

    /**
     * Initialises the map with a specified background image.
     */
    public Map() {
        // Map constructor.
        Image backgroundImage = new Image("https://www.narniaweb.com/wp-content/uploads/2009/08/NarniaMap-768x579.jpg");
        this.backgroundImageView = new ImageView(backgroundImage);
    }

    public void render(Pane pane) {
        StackPane layout = new StackPane();
        pane.getChildren().add(layout);
    }
}
