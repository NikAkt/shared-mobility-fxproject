package org.example.sharedmobilityfxproject.model.tranportMode;


import org.example.sharedmobilityfxproject.model.motion;

public class Bicycle extends TransportationMode implements motion {
    private int speed;

    public Bicycle(int x, int y, int stamina, int GemCount, int speed) {
        super(x, y, stamina, 10, 0, GemCount); // Assuming default speed is 10 and CO2 is 0 for initialization
        this.speed = speed;
    }

    // Overrides the calculateCarbonFootprint method to always return 0 for a Bicycle
    public double calculateCarbonFootprint(double distance) {
        // Bicycles do not have a carbon footprint
        return 0;
    }

    // Getter and Setter for speed
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void increase_Speed() {
        double currentSpeed = getSpeed(); // bring currentSpeed
        setSpeed(currentSpeed + 20); // 현재 속도에 20을 더해 새 속도를 설정
    }

    @Override
    public void moveUp() {

    }

    @Override
    public void moveDown() {

    }

    @Override
    public void moveLeft() {

    }

    @Override
    public void moveRight() {

    }
}