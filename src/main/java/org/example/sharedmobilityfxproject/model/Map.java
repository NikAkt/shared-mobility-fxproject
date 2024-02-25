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
     * Initializes the map with a specified background image.
     */
    public Map() {
        // map constructor
        Image backgroundImage = new Image("https://www.narniaweb.com/wp-content/uploads/2009/08/NarniaMap-768x579.jpg");
        this.backgroundImageView = new ImageView(backgroundImage);
    }

//    @Override
//    public void start(Stage stage) {
//        stage.setTitle("Drag the mouse to pan and scroll to zoom the map");
//
//        // Set the position of the circle on the map
//        double xCoordinate = 300; // Example X coordinate
//        double yCoordinate = 200; // Example Y coordinate
//
//        Circle object = new Circle(10); // Circle with radius 10
//        object.setFill(Color.RED); // Set the color of the circle
//        object.setCenterX(xCoordinate);
//        object.setCenterY(yCoordinate);
//
//        // construct the scene contents over a stacked background.
//        StackPane layout = new StackPane();
//        layout.getChildren().setAll(
//                backgroundImageView,
//                object,
//                createKillButton()
//        );
//
//        // wrap the scene contents in a pannable scroll pane.
//        ScrollPane scroll = createScrollPane(layout);
//
//        // Add zoom capability
//        layout.setOnScroll(new EventHandler<ScrollEvent>() {
//            @Override
//            public void handle(ScrollEvent event) {
//                double zoomFactor = 1.05;
//                double deltaY = event.getDeltaY();
//                if (deltaY < 0){
//                    zoomFactor = 2.0 - zoomFactor;
//                }
//                backgroundImageView.setScaleX(backgroundImageView.getScaleX() * zoomFactor);
//                backgroundImageView.setScaleY(backgroundImageView.getScaleY() * zoomFactor);
//                event.consume();
//            }
//        });
//
//        // show the scene.
//        Scene scene = new Scene(scroll);
//        stage.setScene(scene);
//        stage.show();
//
//        // bind the preferred size of the scroll area to the size of the scene.
//        scroll.prefWidthProperty().bind(scene.widthProperty());
//        scroll.prefHeightProperty().bind(scene.widthProperty());
//
//        // center the scroll contents.
//        scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
//        scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);
//    }

    /**
     * Creates a button with functionality to interact within the game map.
     * @return A configured Button.
     */
    private Button createKillButton() {
        final Button killButton = new Button("Kill the evil witch");
        killButton.setStyle("-fx-base: firebrick;");
        killButton.setTranslateX(65);
        killButton.setTranslateY(-130);
        killButton.setOnAction(event ->  {
            killButton.setStyle("-fx-base: forestgreen;");
            killButton.setText("Ding-Dong! The Witch is Dead");
        });
        return killButton;
    }

//    /** @return a ScrollPane which scrolls the layout. */
//    private ScrollPane createScrollPane(Pane layout) {
//        ScrollPane scroll = new ScrollPane();
//        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//        scroll.setPannable(true);
//        scroll.setPrefSize(800, 600);
//        scroll.setContent(layout);
//        return scroll;
//    }

//    public static void main(String[] args) {
//        launch(args);
//    }

    // Method to initialize or render the map
    public void render(Pane pane) {
        System.out.println("Rendering Map with width: "  + " and height: ");
        // Add your JavaFX code for rendering the map here
        Circle object = new Circle(10, Color.RED);
        object.setCenterX(300); // Example X coordinate
        object.setCenterY(200); // Example Y coordinate

        Button killButton = createKillButton();
        killButton.setTranslateX(65);
        killButton.setTranslateY(-130);

        StackPane layout = new StackPane();
        layout.getChildren().addAll(backgroundImageView, object, killButton);

        pane.getChildren().add(layout);
    }
}
