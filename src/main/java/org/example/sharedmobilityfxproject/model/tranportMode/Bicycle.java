package org.example.sharedmobilityfxproject.model.tranportMode;


public class Bicycle extends TransportationMode {
    public Bicycle(double speed) {
        super("bicycle", speed);
    }

    @Override
    public double calculateCarbonFootprint(double distance) {
        // 자전거는 탄소 배출량이 0
        return 0;
    }
}