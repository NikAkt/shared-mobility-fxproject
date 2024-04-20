package org.example.sharedmobilityfxproject.controller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SaveGame implements Serializable {
    private static final long serialVersionUID = 1L;

    private int playerX;
    private int playerY;
    private int busX;
    private int busY;
    private List<int[]> gemLocations;
    private int gemCounter;

    // Constructor, getters, and setters...

    public SaveGame(int playerX, int playerY, int busX, int busY, List<int[]> gemLocations, int gemCounter) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.busX = busX;
        this.busY = busY;
        this.gemLocations = new ArrayList<>(gemLocations); // Ensure a deep copy if necessary
        this.gemCounter = gemCounter;
    }

    // Getters and Setters for each field

    // Example getter
    public int getPlayerX() {
        return playerX;
    }

    // ... Other getters and setters
}
