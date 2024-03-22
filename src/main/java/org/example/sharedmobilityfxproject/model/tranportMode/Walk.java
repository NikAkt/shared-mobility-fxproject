package org.example.sharedmobilityfxproject.model.tranportMode;

import org.example.sharedmobilityfxproject.model.Player;
import org.example.sharedmobilityfxproject.model.TransportMode;

// Model/Walk.java
public class Walk extends TransportMode {
    public Walk() {
        super("Walk", 0, 5); // Walking has no carbon footprint and is slower.
    }

    // Walk-specific methods.
}
