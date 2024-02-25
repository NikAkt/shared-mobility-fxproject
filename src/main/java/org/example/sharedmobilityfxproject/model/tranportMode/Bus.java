package org.example.sharedmobilityfxproject.model.tranportMode;


public class Bus extends TransportationMode {
    public Bus(double speed) {
        super("bus", speed);
    }

    @Override
    public double calculateCarbonFootprint(double distance) {
        double carbonFootprintPerKm = 97; // bus의 경우
        double journeyCarbonFootprint = carbonFootprintPerKm * distance;
        this.carbon_footprint_amount += journeyCarbonFootprint;
        return journeyCarbonFootprint;
    }
}