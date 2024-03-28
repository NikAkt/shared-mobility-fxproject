package org.example.sharedmobilityfxproject.model;

public class Player {
    private String name;
    private int score;
    private int currentPosition;
    private int gemsCollected;
    private int stamina;

    // Constructor, getters, and setters.
    public Player(){
        this.stamina = 100; // Default stamina value
    }

    // Additional player-related methods.
    public void decreaseStamina(int amount) {
        this.stamina -= amount;
        if (this.stamina < 0) {
            this.stamina = 0;
        }
    }
    public double getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }
    public void collectGem() {
        this.gemsCollected++;
    }
}


