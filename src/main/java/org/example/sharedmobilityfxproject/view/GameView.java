package org.example.sharedmobilityfxproject.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.example.sharedmobilityfxproject.Main;
import org.example.sharedmobilityfxproject.model.MenuElement;

import java.io.InputStream;

public class GameView {
    private MenuElement menuElement;
    private VBox gameModeBox;
    private Main main;
    private HBox topRow;
    private HBox bottomRow;
    private void showPlayerModeSelection(ActionEvent actionEvent) {
//        buttonBox.setVisible(false);
        Button btnOnePlayer = menuElement.createButton("SinglePlay", event -> showStageSelectionScreen());
        Button btnTwoPlayer = menuElement.createButton("MultiPlay", event -> showStageSelectionScreen());

//        Button backToMenu = menuElement.createButton("Back", event -> showInitialScreen());
//        backToMenu.setMinWidth(menuElement.BUTTON_WIDTH);
//        backToMenu.setMaxWidth(menuElement.BUTTON_WIDTH);
//        backToMenu.setStyle("-fx-font-size: 24px;");

        // Create the game mode selection box if not already created
        if (gameModeBox == null) {
            gameModeBox = new VBox(20, btnOnePlayer, btnTwoPlayer);
            gameModeBox.setAlignment(Pos.CENTER);
        }
        // Add the game mode box to the root stack pane, making it visible
        if (!main.root.getChildren().contains(gameModeBox)) {
            main.root.getChildren().add(gameModeBox);
        }

        // Make the game mode selection box visible
        gameModeBox.setVisible(true);

    }
    private void showStageSelectionScreen() {
        if (topRow == null && bottomRow == null) {
            topRow = new HBox(10);
            bottomRow = new HBox(10);
            topRow.setAlignment(Pos.CENTER);
            bottomRow.setAlignment(Pos.CENTER);

            String[] topStages = {"Seoul", "Athens", "Dublin", "Istanbul"};
            String[] bottomStages = {"Vilnius", "Back"};

            for (String stage : topStages) {
                ImageView stageImage = createStageImage(stage); // 예시로 무작위 이미지 생성
                Button stageButton = createStageButton(stage, stageImage);
                topRow.getChildren().add(stageButton);
            }

            for (String stage : bottomStages) {
                ImageView stageImage = createStageImage(stage); // 예시로 무작위 이미지 생성
                Button stageButton = createStageButton(stage, stageImage);
                bottomRow.getChildren().add(stageButton);
            }
        }

        stageSelectionBox = new VBox(20, topRow, bottomRow);
        stageSelectionBox.setAlignment(Pos.CENTER);

        gameModeBox.setVisible(false);
        root.getChildren().removeAll(buttonBox, gameModeBox);
        root.getChildren().add(stageSelectionBox);
    }
    private ImageView createStageImage(String stageName) {
        String imagePath;
        switch (stageName) {
            case "Seoul":
                imagePath = "/images/seoul.jpg"; // 서울 이미지 경로
                break;
            case "Athens":
                imagePath = "/images/athens.png"; // 아테네 이미지 경로
                break;
            case "Dublin":
                imagePath = "/images/dublin.png"; // 더블린 이미지 경로
                break;
            case "Vilnius":
                imagePath = "/images/vilnius.png"; // 더블린 이미지 경로
                break;
            case "Istanbul":
                imagePath = "/images/istanbul.png"; // 더블린 이미지 경로
                break;
            case "Home":
                imagePath = "/images/home.png"; // 더블린 이미지 경로
                break;
            default:
                // 기본 이미지 또는 에러 처리
                imagePath = "/images/Way_Back_Home.png.png";
                break;
        }
        InputStream is = getClass().getResourceAsStream(imagePath);
        if (is == null) {
            throw new IllegalStateException("Cannot find image for stage: " + stageName);
        }
        Image image = new Image(is);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100); // 이미지 높이를 설정
        imageView.setFitWidth(150);  // 이미지 너비를 설정
        return imageView;
    }
    private void loadGameScreen(String stageName) {

        // Create the gameplay pane and layout
        // Overall layout container
        BorderPane borderPane = new BorderPane();

        // CO2 Parameter Bar (Vertical)
        ProgressBar co2Bar = new ProgressBar(2.0); // Example value, adjust as needed
        co2Bar.setPrefWidth(60);
        co2Bar.setPrefHeight(400); // Adjust the height as needed
        co2Bar.setStyle("-fx-accent: red;"); // Set the fill color to red

        // Wrap CO2 bar in VBox to align it vertically
        VBox co2Container = new VBox(co2Bar);
        co2Container.setAlignment(Pos.CENTER);


        // Stamina Parameter
        ProgressBar staminaParameter = new ProgressBar(1); // Set to full stamina
        staminaParameter.setPrefHeight(40);
        staminaParameter.setPrefWidth(600);
        staminaParameter.setStyle("-fx-accent: yellow;"); // Set the fill color to red

        // Wrap CO2 bar in VBox to align it vertically
        VBox staminaContainer = new VBox(staminaParameter);
        staminaContainer.setAlignment(Pos.CENTER);

        // Time countdown
        Label timeLabel = new Label("Time left: 360s");
        timeLabel.setAlignment(Pos.TOP_CENTER);

        // Countdown logic
        IntegerProperty timeSeconds = new SimpleIntegerProperty(5);
        new Timeline(
                new KeyFrame(
                        Duration.seconds(timeSeconds.get()),
//                        event -> gameOver(primaryStage),
                        new KeyValue(timeSeconds, 0)
                )
        ).play();

        timeSeconds.addListener((obs, oldVal, newVal) -> {
            timeLabel.setText("Time left: " + newVal + "s");
        });
        timeLabel.setAlignment(Pos.CENTER);

        // Placeholder for the map
        Label mapPlaceholder = new Label("Make a map ,Eamonn you Lazy Ass");
        mapPlaceholder.setPrefSize(1200, 800);
        mapPlaceholder.setAlignment(Pos.CENTER);
        mapPlaceholder.setStyle("-fx-background-color: lightgrey; -fx-border-color: black;");

        // Add all to the layout

        // Add Stage name and Time above and below the map
        VBox mapBox = new VBox( timeLabel,mapPlaceholder,staminaParameter);
        mapBox.setAlignment(Pos.CENTER);

        // Add all to the layout
        borderPane.setCenter(mapBox);
        borderPane.setLeft(co2Container); // CO2 bar on the left side of the map


        // Set this layout in the scene
        Scene scene = new Scene(borderPane, 1496, 1117);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Welcome To "+ stageName);
//        primaryStage.show();

    }

//    public EventHandler<ActionEvent> showPlayerModeSelection() { if (topRow == null && bottomRow == null) {
//        topRow = new HBox(10);
//        bottomRow = new HBox(10);
//        topRow.setAlignment(Pos.CENTER);
//        bottomRow.setAlignment(Pos.CENTER);
//
//        String[] topStages = {"Seoul", "Athens", "Dublin", "Istanbul"};
//        String[] bottomStages = {"Vilnius", "Back"};
//
//        for (String stage : topStages) {
//            ImageView stageImage = createStageImage(stage); // 예시로 무작위 이미지 생성
//            Button stageButton = createStageButton(stage, stageImage);
//            topRow.getChildren().add(stageButton);
//        }
//
//        for (String stage : bottomStages) {
//            ImageView stageImage = createStageImage(stage); // 예시로 무작위 이미지 생성
//            Button stageButton = createStageButton(stage, stageImage);
//            bottomRow.getChildren().add(stageButton);
//        }
//    }
//
//        stageSelectionBox = new VBox(20, topRow, bottomRow);
//        stageSelectionBox.setAlignment(Pos.CENTER);
//
//        gameModeBox.setVisible(false);
//        root.getChildren().removeAll(buttonBox, gameModeBox);
//        root.getChildren().add(stageSelectionBox);
//
//
//    }
}
