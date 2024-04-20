package org.example.sharedmobilityfxproject.model;

import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Objects;

public class busStop extends Cell {
    private int x;
    private int y;
    private boolean flagIsPlayerHere; // Change this to boolean
    private boolean isBusPresent;

    private boolean atStop;
    public busStop(int i, int j) {
        super(i, j);
        this.x = i;
        this.y = j;
        this.flagIsPlayerHere = false; // Initialize to false

        setPrefHeight(10);  // Set the preferred height to 10 pixels
        setPrefWidth(3);

        String imagePath = "src/main/resources/images/house.png";
        File file = new File(imagePath);
        String absolutePath = file.toURI().toString();

        this.setStyle("-fx-background-image: url('" + absolutePath + "');" +
                "-fx-background-size: contain; -fx-background-position: center center;");
        getStyleClass().add("busStop");
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean isPlayerHere() {
        return this.flagIsPlayerHere;
    }
    public void setBusPresent(boolean isBusPresent) {
        this.isBusPresent = isBusPresent;
    }

    public boolean isBusPresent() {
        return this.isBusPresent;
    }
    public void setPlayerHere(boolean isPlayerHere) {
        this.flagIsPlayerHere = isPlayerHere;
    }



}