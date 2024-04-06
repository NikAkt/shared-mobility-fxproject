package org.example.sharedmobilityfxproject.model.tranportMode;

import org.example.sharedmobilityfxproject.model.motion;
import org.example.sharedmobilityfxproject.model.Cell;

public class Taxi extends Cell {
    private int speed;
    private double carbonFootprintAmount;
    private final double carbonFootprintPerKm = 170; // Assuming this is the rate for a petrol car
    private int x;
    private int y;
    public int flagMove = 0;
    public boolean hailed = false;
    public Taxi(int x, int y) {
        super(x, y); // Assuming default speed is 10 and CO2 is 0 for initialization
        // Initial carbon footprint amount for Taxi
        this.getStyleClass().add("taxi");
        this.x = x;
        this.y = y;
    }
    public void setX(int i ){
        this.x = i;
    }
    public void setY(int j ){
        this.y = j;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    // Overriding a method to calculate carbon footprint based on distance traveled.
    public double calculateCarbonFootprint(double distance) {
        double journeyCarbonFootprint = carbonFootprintPerKm * distance;
        // Update the total carbon footprint
        this.carbonFootprintAmount += journeyCarbonFootprint;
        return journeyCarbonFootprint;
    }

    // Getter and Setter for speed
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    // Getter and Setter for carbonFootprintAmount
    public double getCarbonFootprintAmount() {
        return carbonFootprintAmount;
    }

    public void setCarbonFootprintAmount(double carbonFootprintAmount) {
        this.carbonFootprintAmount = carbonFootprintAmount;
    }

}