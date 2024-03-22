package org.example.sharedmobilityfxproject.model.tranportMode;


import org.example.sharedmobilityfxproject.model.Player;
import org.example.sharedmobilityfxproject.model.TransportMode;

// Model/Bus.java
public class Bus extends TransportMode {
    public Bus() {
        super("Bus", 50, 30); // Assuming 50 as carbon footprint and 30 as speed.
    }

    // Bus-specific methods can be added here.
}
