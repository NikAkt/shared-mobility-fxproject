package org.example.sharedmobilityfxproject.model.tranportMode;


public class Bus extends TransportationMode {
    public Bus(double speed) {
        super("bus", speed);
        this.carbon_footprint_amount=75;
    }

    @Override
    public double calculateCarbonFootprint(double distance) {
        double carbonFootprintPerKm = 97; // in the case of bus
        double journeyCarbonFootprint = carbonFootprintPerKm * distance;
        this.carbon_footprint_amount += journeyCarbonFootprint;
        return journeyCarbonFootprint;
    }
}