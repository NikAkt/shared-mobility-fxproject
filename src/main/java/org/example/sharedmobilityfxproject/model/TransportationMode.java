package org.example.sharedmobilityfxproject.model;

// TransportationMode class with three instance variables.
public class TransportationMode {
    public String modeName;
    public double carbon_footprint_amount;
    public double speed;

    // Constructor of the TransportationMode class. Three parameters, assigned to the corresponding instance variables.
    public TransportationMode(String modeName, double carbon_footprint_amount, double speed) {
        this.modeName = modeName;
        this.carbon_footprint_amount = 0;
        this.speed = speed;
    }

    public TransportationMode(String petrolCar, double speed) {
    }

    // Getter method for the name of the mode of transport.
    public String getModeName() {
        return modeName;
    }

    // Getter method for the total carbon footprint.
    public double getCarbon_footprint_amount() {
        return carbon_footprint_amount;
    }

    // Getter method for the speed of the mode of transport.
    public double getSpeed() {
        return speed;
    }

    // Setter method for defining the name of the mode of transport.
    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    // Setter method for the total carbon footprint.
    public void setCarbon_footprint_amount(double carbon_footprint_amount) {
        this.carbon_footprint_amount = carbon_footprint_amount;
    }

    // Setter method for the speed of the mode of transport.
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    // Method to calculate the carbon footprint for a journey based on the transportation mode and distance.
    public double calculateCarbonFootprint(double distance) {
        // Default carbon footprint per kilometre (in case of errors, can be replaced with average carbon footprint per kilometre).
        double carbonFootprintPerKm = 0; // Default/Average value
        double carbon_footprint_amount = 0;
        // Determine the carbon footprint per kilometre based on the modeName.
        // THE VALUES USED BELOW ARE CO2 EMISSIONS PER MODE OF TRANSPORT PER PASSENGER KILOMETRE.
        if (modeName.equals("petrolCar")) {
            carbonFootprintPerKm = 170; // grammes per kilometre for a petrol car
        } else if (modeName.equals("bus")) {
            carbonFootprintPerKm = 97; // grammes per kilometre for a bus
        } else if (modeName.equals("bicycle")) {
            carbonFootprintPerKm = 0; // grammes per kilometre for a bicycle
        }

        // Calculate the carbon footprint for the entire journey.
        double journeyCarbonFootprint = carbonFootprintPerKm * distance;
        // Update the total carbon footprint.
        carbon_footprint_amount = carbon_footprint_amount + journeyCarbonFootprint;
        return journeyCarbonFootprint;
    }

}