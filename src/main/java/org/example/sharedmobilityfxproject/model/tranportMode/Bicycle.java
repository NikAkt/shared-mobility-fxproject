package org.example.sharedmobilityfxproject.model.tranportMode;


import org.example.sharedmobilityfxproject.model.Player;
import org.example.sharedmobilityfxproject.model.TransportMode;

public class Bicycle extends TransportMode {
    public Bicycle() {
        super("Bicycle", 0, 15); // Assuming 0 carbon footprint and 15 as speed.
    }

    // Bicycle-specific methods can be added here.
}