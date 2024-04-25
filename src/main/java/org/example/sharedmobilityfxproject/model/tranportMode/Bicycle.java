package org.example.sharedmobilityfxproject.model.tranportMode;

import org.example.sharedmobilityfxproject.model.Cell;

import java.io.File;

public class Bicycle extends Cell {
    private int x ;
    private int y ;
    public int bikeTime = 0;
    public Bicycle(int i,int j ) {
        super(i, j);
        this.x = i;
        this.y = j;
        String imagePath = "src/main/resources/images/bike.png";
        File file = new File(imagePath);
        String absolutePath = file.toURI().toString();

        setPrefHeight(10);
        setPrefWidth(8);


        this.setStyle("-fx-background-image: url('" + absolutePath + "');" +
                "-fx-background-size: contain; -fx-background-position: center center;");
        this.getStyleClass().add("bicycle");
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
}