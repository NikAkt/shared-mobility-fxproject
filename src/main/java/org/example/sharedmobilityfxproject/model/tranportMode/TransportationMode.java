package org.example.sharedmobilityfxproject.model.tranportMode;
public abstract class TransportationMode {
    protected String modeName;
    protected double carbon_footprint_amount;
    protected double speed;

    public TransportationMode(String modeName, double speed) {
        this.modeName = modeName;
        this.carbon_footprint_amount = 0; // Default value for general transportation modes
        this.speed = speed;
    }

    public TransportationMode(int x, int y, int stamina, int i, int i1, int gemCount) {
    }


    public String getModeName() {
        return modeName;
    }

    public double getCarbon_footprint_amount() {
        return carbon_footprint_amount;
    }

    public double getSpeed() {
        return speed;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public void setCarbon_footprint_amount(double carbon_footprint_amount) {
        this.carbon_footprint_amount = carbon_footprint_amount;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    // 추상 메서드
    public abstract double calculateCarbonFootprint(double distance);
}
