package org.example.sharedmobilityfxproject.model;

import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.Objects;

public class busStop extends Cell {
    private int x;
    private int y;
    private boolean flagIsPlayerHere; // Change this to boolean

    public busStop(int i, int j) {
        super(i, j);
        this.x = i;
        this.y = j;
        this.flagIsPlayerHere = false; // Initialize to false
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
}