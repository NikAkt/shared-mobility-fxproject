package org.example.sharedmobilityfxproject.model.tranportMode;

import org.example.sharedmobilityfxproject.model.Player;
import org.example.sharedmobilityfxproject.model.TransportMode;

// Model/Walk.java
public class Walk extends TransportMode {
    public Walk() {
        super("Walk", 0, 5); // Walking has no carbon footprint and is slower.
    }

    @Override
    public double calculateCarbonFootprint(double distance) {
        // Implement the calculation specific to Bicycle
        return 0;
    }
    public void walk(Player player, double distance) {
        player.decreaseStamina(5); // Decrease the player's stamina by 20 when walking
        // Additional logic for walking the distance can be added here
    }
}
