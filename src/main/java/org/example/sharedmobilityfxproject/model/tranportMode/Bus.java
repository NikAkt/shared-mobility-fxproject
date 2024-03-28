package org.example.sharedmobilityfxproject.model.tranportMode;


import org.example.sharedmobilityfxproject.model.Player;
import org.example.sharedmobilityfxproject.model.TransportMode;

// Model/Bus.java
public class Bus extends TransportMode {
    public Bus() {
        super("Bus", 50, 30); // Assuming 50 as carbon footprint and 30 as speed.
    }
    @Override
    public double calculateCarbonFootprint(double distance) {
        // Implement the calculation specific to Bicycle
        return getCarbonFootprint()  * distance;
    }
}
