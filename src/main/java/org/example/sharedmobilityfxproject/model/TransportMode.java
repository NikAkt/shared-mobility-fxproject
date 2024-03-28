package org.example.sharedmobilityfxproject.model;
// Model/TransportMode.java
public abstract class TransportMode {
    private String modeName;
    private double carbonFootprint;
    private int speed; // Speed cBan be an abstract concept depending on the game design.

    public TransportMode(String modeName, double carbonFootprint, int speed) {
        this.modeName = modeName;
        this.carbonFootprint = carbonFootprint;
        this.speed = speed;
    }

    // Getters
    public String getModeName() {
        return modeName;
    }

    public double getCarbonFootprint() {
        return carbonFootprint;
    }

    public int getSpeed() {
        return speed;
    }

    //This is the abstract method, and Each child of TransportMode has to implement this method
    public abstract double calculateCarbonFootprint(double distance);

    // Getters and Setters for modeName, carbonFootprintFactor, and speed

}