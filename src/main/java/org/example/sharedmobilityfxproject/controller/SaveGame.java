package org.example.sharedmobilityfxproject.controller;
import java.io.Serializable;

public class SaveGame implements Serializable {
    private static final long serialVersionUID = 1L;

    // Example game state variables
    private int playerX;
    private int playerY;
    // ... other variables as needed

    // Constructor, getters and setters
    public SaveGame(int playerX, int playerY) {
        this.playerX = playerX;
        this.playerY = playerY;
    }

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    // ... other getters and setters as needed
}
