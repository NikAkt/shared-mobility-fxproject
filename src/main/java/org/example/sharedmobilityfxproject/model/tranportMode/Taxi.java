package org.example.sharedmobilityfxproject.model.tranportMode;

import org.example.sharedmobilityfxproject.model.Player;
import org.example.sharedmobilityfxproject.model.TransportMode;

// Model/Taxi.java
public class Taxi extends TransportMode {
    public Taxi() {
        super("Taxi", 100, 60); // Example values.
    }
    @Override
    public double calculateCarbonFootprint(double distance) {
        // Implement the calculation specific to Bicycle
        return getCarbonFootprint()  * distance;
    }
}

