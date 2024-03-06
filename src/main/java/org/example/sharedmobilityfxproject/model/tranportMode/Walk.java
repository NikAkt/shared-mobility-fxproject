package org.example.sharedmobilityfxproject.model.tranportMode;

import org.example.sharedmobilityfxproject.model.Player;
public class Walk extends Player {
    // Example properties unique to the Walk class
    private int walkSpeed;
    public Walk(int x, int y, int stamina, int speed, int co2, int GemCount, int walkSpeed) {
        super(x, y, stamina, speed, co2, GemCount);
        this.walkSpeed = walkSpeed; // Additional properties of the Walk class
    }

    // Walk class's getter and setter
    public void setWalkSpeed(int walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

    public int getWalkSpeed() {
        return walkSpeed;
    }

    // default normSetting
    public void normSpeed() {
        super.setSpeed(10); //Setting speed using methods in the Player class
    }

    public void normCO2() {
        super.setCo2(0); // Setting CO2 using methods in the Player class
    }

    public void normStamina() {
        super.setStamina(100); // Setting stamina using methods in the Player class
    }
}
