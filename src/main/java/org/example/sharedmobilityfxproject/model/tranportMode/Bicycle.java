package org.example.sharedmobilityfxproject.model.tranportMode;

import org.example.sharedmobilityfxproject.model.Player;

public class Bicycle extends Player {
    public Bicycle() {
        super(4, 20, 100, 5, 10); // Assuming default stamina is 100, speed is 15 and CO2 is 0 for initialization
        setStaminaDrain(0.5); // Reduced stamina drain
    }
}