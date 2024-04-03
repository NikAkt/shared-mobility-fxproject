package org.example.sharedmobilityfxproject.model.tranportMode;

import org.example.sharedmobilityfxproject.model.motion;

public class Taxi extends TransportationMode implements motion {
    private int speed;
    private double carbonFootprintAmount;
    private final double carbonFootprintPerKm = 170; // Assuming this is the rate for a petrol car

    public Taxi(int x, int y, int stamina, int GemCount, int speed) {
        super(x, y, stamina, 10, 0, GemCount); // Assuming default speed is 10 and CO2 is 0 for initialization
        this.speed = speed;
        this.carbonFootprintAmount = 75; // Initial carbon footprint amount for Taxi
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

    @Override
    public void moveUp() {

    }

    @Override
    public void moveDown() {

    }

    @Override
    public void moveLeft() {

    }

    @Override
    public void moveRight() {

    }
}
