package org.example.sharedmobilityfxproject.model.tranportMode;
import org.example.sharedmobilityfxproject.model.TransportationMode;
public class Car extends TransportationMode {
    public Car(double speed) {
        super("petrolCar", speed);
        this.carbon_footprint_amount=75;
    }

    @Override
    public double calculateCarbonFootprint(double distance) {
        double carbonFootprintPerKm = 170; // Assuming this is the rate for a petrol car
        double journeyCarbonFootprint = carbonFootprintPerKm * distance;
        // Update the total carbon footprint
        this.carbon_footprint_amount += journeyCarbonFootprint;
        return journeyCarbonFootprint;
    }
}
