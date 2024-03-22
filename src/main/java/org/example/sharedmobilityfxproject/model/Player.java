package org.example.sharedmobilityfxproject.model;

public class Player {
    private String name;
    private int score;
    private int currentPosition;
    private int gemsCollected;

    // Constructor, getters, and setters.

    public void collectGem() {
        this.gemsCollected++;
    }

    // Additional player-related methods.
}
