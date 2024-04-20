package org.example.sharedmobilityfxproject.model.tranportMode;

import org.example.sharedmobilityfxproject.model.Cell;

import java.io.File;

public class Bicycle extends Cell {
    private int x ;
    private int y ;
    public int bikeTime = 0;
    public Bicycle(int i,int j ) {
        super(i, j); // Assuming default stamina is 100, speed is 15 and CO2 is 0 for initialization
        //setStaminaDrain(0.5); // Reduced stamina drain
        this.x = i;
        this.y = j;
        String imagePath = "src/main/resources/images/bike.png";
        File file = new File(imagePath);
        String absolutePath = file.toURI().toString();

        // Set the fixed size of the Gem node to match the cell size
        setPrefHeight(10);  // Set the preferred height to 10 pixels
        setPrefWidth(8);    // Set the preferred width to 8 pixels


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