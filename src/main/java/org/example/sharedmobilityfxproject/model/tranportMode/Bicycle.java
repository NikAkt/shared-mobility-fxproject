package org.example.sharedmobilityfxproject.model.tranportMode;


import org.example.sharedmobilityfxproject.model.Player;
import org.example.sharedmobilityfxproject.model.TransportMode;

public class Bicycle extends TransportMode {
    public Bicycle() {
        super("Bicycle", 0.1, 15); // These values are examples, adjust them as necessary
    }

    @Override
    public double calculateCarbonFootprint(double distance) {
        // Implement the calculation specific to Bicycle
        return getCarbonFootprint()  * distance;
    }
}