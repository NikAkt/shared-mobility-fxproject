package org.example.sharedmobilityfxproject.model.tranportMode;

import org.example.sharedmobilityfxproject.model.Cell;

public class Bicycle extends Cell {
    private int x ;
    private int y ;
    public int bikeTime = 0;
    public Bicycle(int i,int j ) {
        super(i, j); // Assuming default stamina is 100, speed is 15 and CO2 is 0 for initialization
        //setStaminaDrain(0.5); // Reduced stamina drain
        this.x = i;
        this.y = j;
        this.getStyleClass().add("bicycle");
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
}