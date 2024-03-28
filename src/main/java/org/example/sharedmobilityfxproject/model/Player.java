package org.example.sharedmobilityfxproject.model;

public class Player {
    private String name;
    private int score;
    private int currentPosition;
    private int gemsCollected;
    private int stamina;

    // Assuming the default transportation mode is Walk and each walk action reduces 20 stamina
    private static final int STAMINA_DECREASE_ON_WALK = 20;


    // Constructor, getters, and setters.
    public Player(){
        this.stamina = 100; // Default stamina value
        this.gemsCollected = 0;
    }
    public void walk() {
        // Call this method whenever the player walks
        decreaseStamina(STAMINA_DECREASE_ON_WALK);
        // Implement walking logic here (e.g., update currentPosition)
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


