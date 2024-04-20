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
    private int gemCounter;

    // Constructor, getters, and setters...

    public SaveGame(int playerX, int playerY, int busX, int busY,  int gemCounter) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.busX = busX;
        this.busY = busY;
        this.gemCounter = gemCounter;
    }

    // Getters and Setters for each field

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

    public int getBusX() {
        return busX;
    }

    public void setBusX(int busX) {
        this.busX = busX;
    }

    public int getBusY() {
        return busY;
    }

    public void setBusY(int busY) {
        this.busY = busY;
    }

    public int getGemCounter() {
        return gemCounter;
    }

    public void setGemCounter(int gemCounter) {
        this.gemCounter = gemCounter;
    }

}
