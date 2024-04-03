package org.example.sharedmobilityfxproject.model.tranportMode;


import org.example.sharedmobilityfxproject.model.Player;

public class Bus extends TransportationMode {
    private double speed;
    private double carbonFootprintAmount;
    private final double carbonFootprintPerKm = 97; // Specific rate for a bus

    public Bus(String name, double speed) {
        super(name,speed); // Assuming default speed is 10 and CO2 is 0 for initialization
        this.speed = speed;
        this.carbonFootprintAmount = 75; // Initial carbon footprint amount for Bus
    }

    // Method to calculate carbon footprint based on distance traveled, specific to the Bus.
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
