package org.example.sharedmobilityfxproject.model.tranportMode;


public class Bicycle extends TransportationMode {
    public Bicycle(double speed) {
        super("bicycle", speed);
    }

    @Override
    public double calculateCarbonFootprint(double distance) {
        // Bicycle's carbon_footPrint is 0
        return 0;
    }
}