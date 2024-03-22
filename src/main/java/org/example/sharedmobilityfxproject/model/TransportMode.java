package org.example.sharedmobilityfxproject.model;
// Model/TransportMode.java
public class TransportMode {
    private String name;
    private double carbonFootprint;
    private int speed; // Speed can be an abstract concept depending on the game design.

    public TransportMode(String name, double carbonFootprint, int speed) {
        this.name = name;
        this.carbonFootprint = carbonFootprint;
        this.speed = speed;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getCarbonFootprint() {
        return carbonFootprint;
    }

    public int getSpeed() {
        return speed;
    }

    // You might want to add setter methods or other functionality here.
}
