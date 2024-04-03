package org.example.sharedmobilityfxproject.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.sharedmobilityfxproject.Main;
import org.example.sharedmobilityfxproject.model.MenuFrontElement;

import java.io.File;
import java.io.InputStream;

public class GameView {
    public MenuFrontElement menuFrontElement;
    public VBox gameModeBox;
    public Main main;
    public VBox buttonBox;
    public StackPane root;
    public MediaPlayer mediaPlayer;
    public HBox topRow;
    public HBox bottomRow;

    public Stage primaryStage;
    public VBox stageSelectionBox;
    public GameView(Main main) {
        this.main = main;
    }
    public void showStageSelectionScreen() {
        menuFrontElement = new MenuFrontElement();
        if (topRow == null && bottomRow == null) {
            topRow = new HBox(10);
            bottomRow = new HBox(10);
            topRow.setAlignment(Pos.CENTER);
            bottomRow.setAlignment(Pos.CENTER);

            String[] topStages = {"Seoul", "Athens", "Dublin", "Istanbul"};
            String[] bottomStages = {"Vilnius", "Back"};

            for (String stage : topStages) {
                ImageView stageImage = createStageImage(stage); // 예시로 무작위 이미지 생성
                Button stageButton = menuFrontElement.createStageButton(stage, stageImage);
                topRow.getChildren().add(stageButton);
            }

            for (String stage : bottomStages) {
                ImageView stageImage = createStageImage(stage); // 예시로 무작위 이미지 생성
                Button stageButton = menuFrontElement.createStageButton(stage, stageImage);
                bottomRow.getChildren().add(stageButton);
            }
        }
        stageSelectionBox = new VBox(100, topRow, bottomRow);
        stageSelectionBox.setAlignment(Pos.CENTER);
        main.gameModeBox.setVisible(false);
        main.root.getChildren().removeAll(buttonBox, gameModeBox);
        main.root.getChildren().add(stageSelectionBox);


    }
    public ImageView createStageImage(String stageName) {
        String imagePath = switch (stageName) {
            case "Seoul" -> "/images/seoul.jpg"; // 서울 이미지 경로
            case "Athens" -> "/images/athens.png"; // 아테네 이미지 경로
            case "Dublin" -> "/images/dublin.png"; // 더블린 이미지 경로
            case "Vilnius" -> "/images/vilnius.png"; // 더블린 이미지 경로
            case "Istanbul" -> "/images/istanbul.png"; // 더블린 이미지 경로
            case "Home" -> "/images/home.png"; // 더블린 이미지 경로
            case "Back" -> "/images/home.png";
            default ->
                // 기본 이미지 또는 에러 처리
                    "/images/Way_Back_Home.png.png";
        };
        InputStream is = getClass().getResourceAsStream(imagePath);

        if (is == null) {
            throw new IllegalStateException("Cannot find image for stage: " + stageName);
        }
        Image image = new Image(is);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200); // 이미지 높이를 설정
        imageView.setFitWidth(230);  // 이미지 너비를 설정
        return imageView;
    }
    public void loadGameScreen(String stageName) {

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
        primaryStage.setScene(scene);
        primaryStage.setTitle("Welcome To "+ stageName);
        primaryStage.show();

    }

    public void selectStage(String stageName) {
        // Here, you would implement what happens when a stage is selected
        // For example, you might load the game scene for the selected stage
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        System.out.println("Stage selected: " + stageName);
        String musicFilePath;
        switch (stageName) {
            case "Seoul":
                musicFilePath = "path/to/seoul/song.mp3";
                break;
            case "Athens":
                // Load Athens stage
                break;
            case "Dublin":
                // Load Dublin stage
                break;
            case "Istanbul":
                // Load Istanbul stage
                break;
            case "Vilnius":
                // Load Vilnius stage
                break;
            case "Back":
                // Go back to the previous screen
                main.showPlayerModeSelection(null);
                break;
            default:
                // Handle default case if necessary
                break;
        }
        Media gameMusic = new Media(new File("src/main/resources/music/Merry_go.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(gameMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Set the music to loop continuously
        mediaPlayer.play(); // Start playing the new background music
        // This is where you would transition to the actual game play scene
        // For now, just printing out the selection
        System.out.println("You have selected the stage: " + stageName);

        // You might want to hide the stage selection screen and display the game screen, like so:
        stageSelectionBox.setVisible(false);
        loadGameScreen(stageName);
    }
    public void showInitialScreen() {
        // 다른 UI 요소들을 숨깁니다.
        if (gameModeBox != null) {
            gameModeBox.setVisible(false);
        }
        if (stageSelectionBox != null) {
            stageSelectionBox.setVisible(false);
        }
        // 초기 버튼들을 다시 보여줍니다.
        buttonBox.setVisible(true);
        // root에서 필요 없는 요소들을 제거합니다. 선택적으로 사용
        root.getChildren().removeAll(gameModeBox, stageSelectionBox);
        // root에 buttonBox가 이미 포함되어 있지 않다면 다시 추가합니다.
        if (!root.getChildren().contains(buttonBox)) {
            root.getChildren().add(buttonBox);
        }
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
