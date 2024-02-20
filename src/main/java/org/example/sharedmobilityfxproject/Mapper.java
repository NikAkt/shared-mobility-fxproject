package main.java.org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
import static javafx.application.Application.launch;
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
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Mapper extends Application {
    private Image backgroundImage;
    private ImageView backgroundImageView; // Reference to the ImageView
    Circle object = new Circle(10); // Circle with radius 10
        object.setFill(Color.RED); // Set the color of the circle

    // Set the position of the circle on the map
    double xCoordinate = 300; // Example X coordinate
    double yCoordinate = 200; // Example Y coordinate
        object.setCenterX(xCoordinate);
        object.setCenterY(yCoordinate);
    @Override public void init() {
        backgroundImage = new Image("https://www.narniaweb.com/wp-content/uploads/2009/08/NarniaMap-768x579.jpg");
        backgroundImageView = new ImageView(backgroundImage); // Initialize ImageView
    }

    @Override public void start(Stage stage) {
        stage.setTitle("Drag the mouse to pan and scroll to zoom the map");

        // construct the scene contents over a stacked background.
        StackPane layout = new StackPane();
        layout.getChildren().setAll(
                backgroundImageView,
                createKillButton()
        );

        // wrap the scene contents in a pannable scroll pane.
        ScrollPane scroll = createScrollPane(layout);

        // Add zoom capability
        layout.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                double zoomFactor = 1.05;
                double deltaY = event.getDeltaY();
                if (deltaY < 0){
                    zoomFactor = 2.0 - zoomFactor;
                }
                backgroundImageView.setScaleX(backgroundImageView.getScaleX() * zoomFactor);
                backgroundImageView.setScaleY(backgroundImageView.getScaleY() * zoomFactor);
                event.consume();
            }
        });

        // show the scene.
        Scene scene = new Scene(scroll);
        stage.setScene(scene);
        stage.show();

        // bind the preferred size of the scroll area to the size of the scene.
        scroll.prefWidthProperty().bind(scene.widthProperty());
        scroll.prefHeightProperty().bind(scene.widthProperty());

        // center the scroll contents.
        scroll.setHvalue(scroll.getHmin() + (scroll.getHmax() - scroll.getHmin()) / 2);
        scroll.setVvalue(scroll.getVmin() + (scroll.getVmax() - scroll.getVmin()) / 2);
    }

    /** @return a control to place on the scene. */
    private Button createKillButton() {
        final Button killButton = new Button("Kill the evil witch");
        killButton.setStyle("-fx-base: firebrick;");
        killButton.setTranslateX(65);
        killButton.setTranslateY(-130);
        killButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent t) {
                killButton.setStyle("-fx-base: forestgreen;");
                killButton.setText("Ding-Dong! The Witch is Dead");
            }
        });
        return killButton;
    }

    /** @return a ScrollPane which scrolls the layout. */
    private ScrollPane createScrollPane(Pane layout) {
        ScrollPane scroll = new ScrollPane();
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPannable(true);
        scroll.setPrefSize(800, 600);
        scroll.setContent(layout);
        return scroll;
    }


    public static void main(String[] args) { launch(args); }
}
